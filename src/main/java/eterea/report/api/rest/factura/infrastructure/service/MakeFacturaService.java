package eterea.report.api.rest.factura.infrastructure.service;

import eterea.report.api.rest.factura.infrastructure.client.InvoiceDataClient;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MakeFacturaService {

    private final FacturaPdfService facturaPdfService;
    private final JavaMailSender javaMailSender;
    private final InvoiceDataClient invoiceDataClient;

    @Value("${app.mailcopy.account}")
    private String mailCopyAccount;

    @Value("${app.testing}")
    private Boolean testing;

    @Value("${app.mail.reply-to:no-reply@eterea.com}")
    private String replyToAddress;

    public String send(Long clienteMovimientoId, String email) throws MessagingException {

        if (email.equals("daniel.quinterospinto@gmail.com")) {
            email = "";
        }

        var invoiceData = invoiceDataClient.getInvoiceDataByClienteMovimientoId(clienteMovimientoId);

        // Genera PDF
        String filenameFactura = facturaPdfService.generatePdf(clienteMovimientoId, invoiceData);
        log.info("filenameFactura -> {}", filenameFactura);
        if (filenameFactura.isEmpty()) {
            return "ERROR: Sin Factura para ENVIAR";
        }

        var clienteMovimiento = invoiceData.getClienteMovimiento();
        String data = getTextForEmail();

        // Envia correo
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
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

            FileSystemResource fileRecibo = new FileSystemResource(filenameFactura);
            helper.addAttachment(filenameFactura, fileRecibo);

            log.info("Enviando correo a: {} con BCC configurado", addresses);
            javaMailSender.send(message);
            return "Envío de correo Ok";
        } catch (MessagingException e) {
            log.error("Error al enviar correo: {}", e.getMessage(), e);
            return "Error envío: " + e.getMessage();
        }
    }

    @NotNull
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
