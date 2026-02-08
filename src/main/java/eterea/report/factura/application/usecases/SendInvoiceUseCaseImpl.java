package eterea.report.factura.application.usecases;

import eterea.report.factura.domain.ports.in.GenerateInvoicePdfUseCase;
import eterea.report.factura.domain.ports.in.SendInvoiceUseCase;
import eterea.report.factura.domain.ports.out.InvoiceDataRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class SendInvoiceUseCaseImpl implements SendInvoiceUseCase {

    private final InvoiceDataRepository invoiceDataRepository;
    private final GenerateInvoicePdfUseCase generateInvoicePdfUseCase;
    private final JavaMailSender javaMailSender;

    @Value("${app.mailcopy.account}")
    private String mailCopyAccount;

    @Value("${app.testing}")
    private Boolean testing;

    @Value("${app.mail.reply-to:no-reply@eterea.com}")
    private String replyToAddress;

    @Override
    public String sendInvoice(Long clienteMovimientoId, String email) throws MessagingException {

        if (email.equals("daniel.quinterospinto@gmail.com")) {
            email = "";
        }

        var invoiceData = invoiceDataRepository.findByClienteMovimientoId(clienteMovimientoId);

        // Genera PDF
        var pdfResource = generateInvoicePdfUseCase.generatePdf(clienteMovimientoId);
        String filenameFactura = extractFilename(pdfResource);
        log.info("filenameFactura -> {}", filenameFactura);
        if (filenameFactura.isEmpty()) {
            return "ERROR: Sin Factura para ENVIAR";
        }

        var clienteMovimiento = invoiceData.getClienteMovimiento();
        String data = getTextForEmail();

        // Envia correo
        var message = javaMailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);
        var addresses = new ArrayList<String>();

        if (!testing) {
            // Validar y agregar correo del cliente
            String clienteEmail = Objects.requireNonNull(clienteMovimiento.getCliente()).getEmail();
            if (!clienteEmail.trim().isEmpty()) {
                addresses.add(clienteEmail.trim());
                log.debug("Agregando correo del cliente: {}", clienteEmail);
            }

            // Validar y agregar correo adicional
            if (!email.trim().isEmpty()) {
                addresses.add(email.trim());
                log.debug("Agregando correo adicional: {}", email);
            }

            if (addresses.isEmpty()) {
                log.error("No hay direcciones de correo válidas para enviar");
                return "ERROR: No hay direcciones de correo válidas";
            }
        }

        if (testing) {
            addresses.add("daniel.quinterospinto@gmail.com");
            log.debug("Agregando correo de prueba: {}", "daniel.quinterospinto@gmail.com");
        }

        // Manejar copias ocultas
        try {
            if (!testing) {
                if (mailCopyAccount != null && !mailCopyAccount.equals("null") && !mailCopyAccount.trim().isEmpty()) {
                    helper.addBcc(mailCopyAccount.trim());
                    log.debug("Agregando BCC de cuenta configurada: {}", mailCopyAccount);
                }
            }
            
            helper.setTo(addresses.toArray(new String[0]));
            helper.setText(data);
            helper.setReplyTo(replyToAddress);
            helper.setSubject("Envío Automático de Comprobante -> " + filenameFactura);

            FileSystemResource fileRecibo = new FileSystemResource(Objects.requireNonNull(pdfResource.getFilename()));
            helper.addAttachment(filenameFactura, fileRecibo);

            log.info("Enviando correo a: {} con BCC configurado", addresses);
            javaMailSender.send(message);
            return "Envío de correo Ok";
        } catch (MessagingException e) {
            log.error("Error al enviar correo: {}", e.getMessage(), e);
            return "Error envío: " + e.getMessage();
        }
    }

    private String extractFilename(org.springframework.core.io.Resource resource) {
        try {
            return resource.getFilename();
        } catch (Exception e) {
            log.error("Error extracting filename", e);
            return "";
        }
    }

    private static String getTextForEmail() {
        String data = "Estimad@ cliente:" + (char) 10;
        data = data + (char) 10;
        data = data + "Le enviamos como archivo adjunto su comprobante." + (char) 10;
        data = data + (char) 10;
        data = data + "Le agradecemos habernos elegido." + (char) 10;
        data = data + (char) 10;
        data = data + "Atentamente." + (char) 10;
        data = data + (char) 10;
        data = data + (char) 10
                + "Por favor no responda este mail, fue generado automáticamente. Su respuesta no será leída."
                + (char) 10;
        return data;
    }

}