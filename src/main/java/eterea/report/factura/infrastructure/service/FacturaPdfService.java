package eterea.report.factura.infrastructure.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import eterea.report.factura.application.service.InvoiceDataService;
import eterea.report.factura.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.openpdf.text.*;
import org.openpdf.text.Font;
import org.openpdf.text.Image;
import org.openpdf.text.Rectangle;
import org.openpdf.text.pdf.*;
import eterea.report.tool.ToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static com.google.zxing.client.j2se.MatrixToImageConfig.BLACK;
import static com.google.zxing.client.j2se.MatrixToImageConfig.WHITE;


@Service
@Slf4j
@RequiredArgsConstructor
public class FacturaPdfService {

    private final Environment environment;
    private final InvoiceDataService invoiceDataService;

    private static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig(BLACK, WHITE); // BLACK , WHITE

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
        return pngOutputStream.toByteArray();
    }

    public String generatePdf(Long clienteMovimientoId, InvoiceData invoiceData) {

        Image imageQr = null;
        if (invoiceData == null) {
            invoiceData = invoiceDataService.getInvoiceDataByClienteMovimientoId(clienteMovimientoId);
        }
        var clienteMovimiento = invoiceData.getClienteMovimiento();
        var empresa = clienteMovimiento.getEmpresa();
        var cliente = clienteMovimiento.getCliente();
        var moneda = clienteMovimiento.getMoneda();

        // Cambia los null del tipo de documento y numero de documento
        var registroCae = invoiceData.getRegistroCae();
        if (registroCae.getTipoDocumento() == null) {
            registroCae.setTipoDocumento(99);
        }

        var clienteMovimientoAsociado = invoiceData.getClienteMovimientoAsociado();
        ComprobanteAfip comprobanteAfipAsociado = null;
        if (clienteMovimientoAsociado != null) {
            comprobanteAfipAsociado = clienteMovimientoAsociado.getComprobante().comprobanteAfip();
        }

        try {
            String url = "https://www.afip.gob.ar/fe/qr/?p=";
            CodigoQR codigoQR = new CodigoQR();
            codigoQR.setVer(1);
            codigoQR.setFecha(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ToolService.stringDDMMYYYY2OffsetDateTime(Objects.requireNonNull(registroCae.getFecha()))));
            codigoQR.setCuit(Objects.requireNonNull(empresa.getCuit()).replaceAll("-", ""));
            codigoQR.setPtoVta(registroCae.getPuntoVenta());
            codigoQR.setTipoCmp(registroCae.getComprobanteId());
            codigoQR.setNroCmp(registroCae.getNumeroComprobante());
            codigoQR.setImporte(registroCae.getTotal());
            codigoQR.setMoneda("PES");
            codigoQR.setCtz(1);
            codigoQR.setTipoDocRec(registroCae.getTipoDocumento());
            codigoQR.setNroDocRec(registroCae.getNumeroDocumento());
            codigoQR.setTipoCodAut("E");
            codigoQR.setCodAut(Objects.requireNonNull(registroCae.getCae()));
            ObjectMapper objectMapper = new ObjectMapper();
            String datos = new String(Base64.getEncoder().encode(objectMapper.writeValueAsString(codigoQR).getBytes()));
            imageQr = Image.getInstance(getQRCodeImage(url + datos, 25, 25));

        } catch (BadElementException e) {
            log.debug("Sin Imagen");
        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }

        var comprobante = registroCae.getComprobante();
        Boolean discrimina = true;
        int copias = 2;
        List<String> discriminados = Arrays.asList("A", "M");
        if (discriminados.contains(comprobante.letraComprobante())) {
            discrimina = true;
            copias = 3;
        }
        var comprobanteAfip = comprobante.comprobanteAfip();

        String[] titulo_copias = {"ORIGINAL", "DUPLICADO", "TRIPLICADO"};

        String path = environment.getProperty("path.facturas");

        String filename = "";
        List<String> filenames = new ArrayList<>();
        for (int copia = 0; copia < copias; copia++) {
            filenames.add(filename = path + clienteMovimientoId + "." + titulo_copias[copia].toLowerCase() + ".pdf");

            makePage(filename, titulo_copias[copia], empresa, comprobante, comprobanteAfip, registroCae, cliente,
                    clienteMovimiento, moneda, discrimina, imageQr, clienteMovimientoAsociado, comprobanteAfipAsociado);
        }

        try {
            mergePdf(filename = path + clienteMovimientoId + ".pdf", filenames);
        } catch (DocumentException | IOException e) {
            log.debug(e.getMessage());
        }

        return filename;
    }

    private void mergePdf(String filename, List<String> filenames) throws DocumentException, IOException {
        OutputStream outputStream = new FileOutputStream(filename);
        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
        for (String name : filenames) {
            PdfReader pdfReader = new PdfReader(new FileInputStream(name));
            for (int pagina = 0; pagina < pdfReader.getNumberOfPages(); ) {
                document.newPage();
                PdfImportedPage page = pdfWriter.getImportedPage(pdfReader, ++pagina);
                pdfContentByte.addTemplate(page, 0, 0);
            }
        }
        outputStream.flush();
        document.close();
        outputStream.close();
    }

    private void makePage(String filename, String titulo, Empresa empresa, Comprobante comprobante,
                          ComprobanteAfip comprobanteAfip, RegistroCae registroCae, Cliente cliente,
                          ClienteMovimiento clienteMovimiento, Moneda moneda, Boolean discriminar, Image imageQr,
                          ClienteMovimiento clienteMovimientoAsociado, ComprobanteAfip comprobanteAfipAsociado) {
        PdfPTable table;
        PdfPCell cell;

        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.setMargins(20, 20, 20, 20);
            document.open();

            table = new PdfPTable(1);
            table.setWidthPercentage(100);
            Paragraph paragraph = new Paragraph(titulo, new Font(Font.COURIER, 16, Font.BOLD, Color.BLACK));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell = new PdfPCell();
            cell.addElement(paragraph);
            table.addCell(cell);
            document.add(table);

            table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{48, 4, 48});
            cell = new PdfPCell();
            paragraph = new Paragraph(empresa.getNombreFantasia(), new Font(Font.HELVETICA, 14, Font.BOLD));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(10);
            cell.addElement(paragraph);
            cell.addElement(new Paragraph(" ", new Font(Font.HELVETICA, 6, Font.NORMAL)));
            paragraph = new Paragraph(new Phrase("Razón Social: ", new Font(Font.HELVETICA, 9, Font.NORMAL)));
            paragraph.add(new Phrase(empresa.getRazonSocial(), new Font(Font.HELVETICA, 10, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(10);
            cell.addElement(paragraph);
            paragraph = new Paragraph(
                    new Phrase("Domicilio Comercial: ", new Font(Font.HELVETICA, 9, Font.NORMAL)));
            paragraph.add(new Phrase(empresa.getDomicilio() + " " + empresa.getTelefono(),
                    new Font(Font.HELVETICA, 10, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(10);
            cell.addElement(paragraph);
            paragraph = new Paragraph(
                    new Phrase("Condición frente al IVA: ", new Font(Font.HELVETICA, 9, Font.NORMAL)));
            paragraph.add(new Phrase(empresa.getCondicionIva(), new Font(Font.HELVETICA, 10, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(10);
            cell.addElement(paragraph);
            table.addCell(cell);
            cell = new PdfPCell();
            paragraph = new Paragraph(comprobante.letraComprobante(), new Font(Font.HELVETICA, 24, Font.BOLD));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);
            paragraph = new Paragraph(new Phrase("Cod: ", new Font(Font.HELVETICA, 6, Font.NORMAL)));
            paragraph.add(new Phrase(Objects.requireNonNull(comprobante.comprobanteAfip().comprobanteAfipId()).toString(),
                    new Font(Font.HELVETICA, 6, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);
            table.addCell(cell);
            cell = new PdfPCell();
            paragraph = new Paragraph(comprobanteAfip.label(), new Font(Font.HELVETICA, 14, Font.BOLD));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(20);
            cell.addElement(paragraph);
            paragraph = new Paragraph(new Phrase("Punto de Venta: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
            paragraph.add(new Phrase(new DecimalFormat("0000").format(registroCae.getPuntoVenta()),
                    new Font(Font.HELVETICA, 8, Font.BOLD)));
            paragraph.add(new Phrase("          Comprobante Nro: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
            paragraph.add(new Phrase(new DecimalFormat("00000000").format(registroCae.getNumeroComprobante()),
                    new Font(Font.HELVETICA, 8, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(20);
            cell.addElement(paragraph);
            paragraph = new Paragraph(new Phrase("Fecha de Emisión: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
            paragraph.add(new Phrase(ToolService.stringDDMMYYYY2OffsetDateTime(Objects.requireNonNull(registroCae.getFecha()))
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), new Font(Font.HELVETICA, 8, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(20);
            cell.addElement(paragraph);
            paragraph = new Paragraph(new Phrase("CUIT: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
            paragraph.add(new Phrase(empresa.getCuit(), new Font(Font.HELVETICA, 8, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(20);
            cell.addElement(paragraph);
            paragraph = new Paragraph(new Phrase("Ingresos Brutos: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
            paragraph.add(new Phrase(empresa.getIngresosBrutos(), new Font(Font.HELVETICA, 8, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(20);
            cell.addElement(paragraph);
            paragraph = new Paragraph(
                    new Phrase("Inicio Actividades: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
            paragraph.add(new Phrase(empresa.getInicioActividades(), new Font(Font.HELVETICA, 8, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(20);
            cell.addElement(paragraph);
            table.addCell(cell);
            document.add(table);
            table = new PdfPTable(1);
            table.setWidthPercentage(100);
            cell = new PdfPCell();
            paragraph = new Paragraph(new Phrase("Cliente: ", new Font(Font.HELVETICA, 10, Font.NORMAL)));
            paragraph.add(new Phrase(cliente.getRazonSocial(), new Font(Font.HELVETICA, 10, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(20);
            cell.addElement(paragraph);
            paragraph = new Paragraph(new Phrase("Domicilio: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
            paragraph.add(new Phrase(cliente.getDomicilio(), new Font(Font.HELVETICA, 8, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(20);
            cell.addElement(paragraph);
            paragraph = new Paragraph(new Phrase("CUIT: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
            paragraph.add(new Phrase(cliente.getCuit(), new Font(Font.HELVETICA, 8, Font.BOLD)));
            paragraph
                    .add(new Phrase("                          IVA: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
            String[] condiciones = {"Sin Posición", "Responsable Inscripto", "Consumidor Final", "Monotributista",
                    "Responsable No Inscripto", "Exento", "Exportación"};
            paragraph.add(
                    new Phrase(condiciones[cliente.getPosicionIva()], new Font(Font.HELVETICA, 8, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(20);
            cell.addElement(paragraph);
            paragraph = new Paragraph(
                    new Phrase("Condición de venta: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
            paragraph.add(new Phrase(comprobante.contado() == 0 ? "Cuenta Corriente" : "Contado",
                    new Font(Font.HELVETICA, 8, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(20);
            cell.addElement(paragraph);
            cell.addElement(new Paragraph(" ", new Font(Font.HELVETICA, 6, Font.BOLD)));
            table.addCell(cell);
            document.add(table);

            table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{45, 15, 15, 15});
            // Títulos
            boolean isMonedaEmpty = clienteMovimiento.getMoneda() == null;
            String monedaSimbolo = isMonedaEmpty ? "" : (char) 10 + clienteMovimiento.getMoneda().simbolo();
            cell = new PdfPCell();
            paragraph = new Paragraph("Artículo", new Font(Font.HELVETICA, 8, Font.BOLD));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(paragraph);
            table.addCell(cell);
            cell = new PdfPCell();
            paragraph = new Paragraph("Cantidad", new Font(Font.HELVETICA, 8, Font.BOLD));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(paragraph);
            table.addCell(cell);
            cell = new PdfPCell();
            paragraph = new Paragraph("Precio Unitario" + monedaSimbolo, new Font(Font.HELVETICA, 8, Font.BOLD));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(paragraph);
            table.addCell(cell);
            cell = new PdfPCell();
            paragraph = new Paragraph("Subtotal" + monedaSimbolo, new Font(Font.HELVETICA, 8, Font.BOLD));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(paragraph);
            table.addCell(cell);
            //
            document.add(table);

            int lineas = discriminar ? 20 : 24;

            for (var articuloMovimiento : clienteMovimiento.getArticulos()) {
                lineas--;
                table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setWidths(new int[]{45, 15, 15, 15});
                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                var articulo = articuloMovimiento.getArticulo();
                String asociado = "";
                try {
                    asociado = (char) 10 + articuloMovimiento.getConceptoFacturado().getConcepto();
                    lineas--;
                } catch (Exception e) {
                    asociado = "";
                }
                paragraph = new Paragraph(articulo.getDescripcion() + asociado, new Font(Font.HELVETICA, 8, Font.NORMAL));
                paragraph.setAlignment(Element.ALIGN_LEFT);
                cell.addElement(paragraph);
                table.addCell(cell);
                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                paragraph = new Paragraph(new DecimalFormat("#,##0.00").format(Math.abs(Objects.requireNonNull(articuloMovimiento.getCantidad()).doubleValue())),
                        new Font(Font.HELVETICA, 8, Font.NORMAL));
                paragraph.setAlignment(Element.ALIGN_RIGHT);
                cell.addElement(paragraph);
                table.addCell(cell);
                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                paragraph = new Paragraph(
                        new DecimalFormat("#,##0.00").format(discriminar ? articuloMovimiento.getPrecioUnitarioSinIva()
                                : articuloMovimiento.getPrecioUnitarioConIva()),
                        new Font(Font.HELVETICA, 8, Font.NORMAL));
                paragraph.setAlignment(Element.ALIGN_RIGHT);
                cell.addElement(paragraph);
                table.addCell(cell);
                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                paragraph = new Paragraph(
                        new DecimalFormat("#,##0.00").format(Math.abs(articuloMovimiento.getCantidad()
                                .multiply((discriminar ? articuloMovimiento.getPrecioUnitarioSinIva()
                                        : articuloMovimiento.getPrecioUnitarioConIva()))
                                .doubleValue())),
                        new Font(Font.HELVETICA, 8, Font.NORMAL));
                paragraph.setAlignment(Element.ALIGN_RIGHT);
                cell.addElement(paragraph);
                table.addCell(cell);
                document.add(table);
            }

            for (int i = 0; i < lineas; i++) {
                table = new PdfPTable(1);
                table.setWidthPercentage(100);
                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                cell.addElement(new Paragraph("  ", new Font(Font.COURIER, 8, Font.NORMAL)));
                table.addCell(cell);
                document.add(table);
            }

            table = new PdfPTable(1);
            table.setWidthPercentage(100);
            // Agregando Observaciones
            paragraph = new Paragraph(new Phrase("Observaciones: ", new Font(Font.COURIER, 10, Font.BOLD)));
            String observaciones = "";

            if (clienteMovimiento.getObservaciones() != null) {
                observaciones += clienteMovimiento.getObservaciones();
            }
            assert clienteMovimiento.getReservaId() != null;
            if (clienteMovimiento.getReservaId() > 0) {
                observaciones += " Reserva: ";
                observaciones += clienteMovimiento.getReservaId() + " ";
            }
            if (clienteMovimientoAsociado != null) {
                observaciones += " Asoc: ";
                if (comprobanteAfipAsociado != null) {
                    observaciones += comprobanteAfipAsociado.label();
                }
                observaciones += clienteMovimientoAsociado.getLetraComprobante()
                        + String.format("%04d", clienteMovimientoAsociado.getPuntoVenta()) + "-"
                        + String.format("%08d", clienteMovimientoAsociado.getNumeroComprobante());
            }
            paragraph.add(new Phrase(observaciones, new Font(Font.HELVETICA, 10, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            cell = new PdfPCell();
            cell.addElement(paragraph);
            table.addCell(cell);
            // Agregando Son Pesos
            paragraph = new Paragraph(new Phrase("Son pesos: ", new Font(Font.COURIER, 8, Font.BOLD)));
            paragraph.add(new Phrase(clienteMovimiento.getLetras(), new Font(Font.HELVETICA, 8, Font.NORMAL)));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            cell = new PdfPCell();
            cell.addElement(paragraph);
            table.addCell(cell);
            //
            document.add(table);

            table = new PdfPTable(1);
            table.setWidthPercentage(100);
            cell = new PdfPCell();
            if (discriminar) {
                paragraph = new Paragraph(new Phrase("Importe Neto: " + moneda.simbolo() + " ",
                        new Font(Font.COURIER, 9, Font.NORMAL)));
                paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(clienteMovimiento.getNeto().abs()),
                        new Font(Font.HELVETICA, 9, Font.BOLD)));
                paragraph.setAlignment(Element.ALIGN_RIGHT);
                cell.addElement(paragraph);

                paragraph = new Paragraph(new Phrase("Importe Exento: " + moneda.simbolo() + " ",
                        new Font(Font.COURIER, 9, Font.NORMAL)));
                paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(clienteMovimiento.getMontoExento().abs()),
                        new Font(Font.HELVETICA, 9, Font.BOLD)));
                paragraph.setAlignment(Element.ALIGN_RIGHT);
                cell.addElement(paragraph);

                paragraph = new Paragraph(new Phrase("Importe IVA 21%: " + moneda.simbolo() + " ",
                        new Font(Font.COURIER, 9, Font.NORMAL)));
                paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(clienteMovimiento.getMontoIva().abs()),
                        new Font(Font.HELVETICA, 9, Font.BOLD)));
                paragraph.setAlignment(Element.ALIGN_RIGHT);
                cell.addElement(paragraph);

                paragraph = new Paragraph(new Phrase("Importe IVA 10.5%: " + moneda.simbolo() + " ",
                        new Font(Font.COURIER, 9, Font.NORMAL)));
                paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(clienteMovimiento.getMontoIvaRni().abs()),
                        new Font(Font.HELVETICA, 9, Font.BOLD)));
                paragraph.setAlignment(Element.ALIGN_RIGHT);
                cell.addElement(paragraph);

            }
            paragraph = new Paragraph(new Phrase("Importe Total: $ ", new Font(Font.COURIER, 10, Font.BOLD)));
            paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(clienteMovimiento.getImporte().abs()),
                    new Font(Font.HELVETICA, 10, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(paragraph);
            table.addCell(cell);
            document.add(table);

            table = new PdfPTable(1);
            table.setWidthPercentage(100);
            paragraph = new Paragraph(new Phrase("CAE Nro: ", new Font(Font.COURIER, 10, Font.NORMAL)));
            paragraph.add(new Phrase(registroCae.getCae(), new Font(Font.HELVETICA, 10, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(paragraph);
            table.addCell(cell);
            document.add(table);

            table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{15, 50, 35});
            // Agrega código QR
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(imageQr);
            table.addCell(cell);
            //
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            // Agrega Vencimiento
            paragraph = new Paragraph(new Phrase("Vencimiento CAE: ", new Font(Font.COURIER, 10, Font.NORMAL)));
            paragraph.add(new Phrase(registroCae.getCaeVencimiento(), new Font(Font.HELVETICA, 10, Font.BOLD)));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.addElement(paragraph);
            table.addCell(cell);
            document.add(table);

            document.close();
        } catch (Exception ex) {
            log.debug(ex.getMessage());
        }
    }

}
