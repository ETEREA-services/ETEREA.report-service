package eterea.report.api.rest.service.facade;

import eterea.report.api.rest.client.ClienteMovimientoClient;
import eterea.report.api.rest.kotlin.model.dto.ClienteMovimientoDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class MakeFacturaService {

    private final FacturaPdfService facturaPdfService;
    private final ClienteMovimientoClient clienteMovimientoClient;
    private final JavaMailSender javaMailSender;

    @Value("${app.mailcopy.account}")
    private String mailCopyAccount;

    public MakeFacturaService(FacturaPdfService facturaPdfService, ClienteMovimientoClient clienteMovimientoClient, JavaMailSender javaMailSender) {
        this.facturaPdfService = facturaPdfService;
        this.clienteMovimientoClient = clienteMovimientoClient;
        this.javaMailSender = javaMailSender;
    }

    public String send(Long clienteMovimientoId, String email) throws MessagingException {
        // Genera PDF
        String filenameFactura = facturaPdfService.generatePdf(clienteMovimientoId);
        log.info("filenameFactura -> " + filenameFactura);
        if (filenameFactura.isEmpty()) {
            return "ERROR: Sin Factura para ENVIAR";
        }

        String data = "";

        ClienteMovimientoDto clienteMovimiento = clienteMovimientoClient.findByClienteMovimientoId(clienteMovimientoId);

        data = "Estimad@ cliente:" + (char) 10;
        data = data + (char) 10;
        data = data + "Le enviamos como archivo adjunto su factura." + (char) 10;
        data = data + (char) 10;
        data = data + "Le agradecemos habernos elegido." + (char) 10;
        data = data + (char) 10;
        data = data + "Atentamente." + (char) 10;
        data = data + (char) 10;
        data = data + (char) 10
                + "Por favor no responda este mail, fue generado automáticamente. Su respuesta no será leída."
                + (char) 10;

        // Envia correo
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        var addresses = new ArrayList<String>();
        var addresses_bcc = new ArrayList<String>();

        if (!clienteMovimiento.getCliente().getEmail().isEmpty()) {
            addresses.add(clienteMovimiento.getCliente().getEmail());
        }
        if (!email.isEmpty()) {
            addresses.add(email);
        }

        if (!mailCopyAccount.equals("null")) {
            addresses_bcc.add(mailCopyAccount);
        }
        addresses_bcc.add("daniel.quinterospinto@gmail.com");

        try {
            helper.setTo(addresses.toArray(new String[0]));
            helper.setBcc(addresses_bcc.toArray(new String[0]));
            helper.setText(data);
            helper.setReplyTo("no-reply@gmail.com");
            helper.setSubject("Envío Automático de Factura -> " + filenameFactura);

            FileSystemResource fileRecibo = new FileSystemResource(filenameFactura);
            helper.addAttachment(filenameFactura, fileRecibo);

        } catch (MessagingException e) {
            return "Error envío";
        }
        javaMailSender.send(message);
        return "Envío de correo Ok";
    }

}
