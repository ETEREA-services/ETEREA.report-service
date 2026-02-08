package eterea.report.factura.domain.ports.in;

import jakarta.mail.MessagingException;

public interface SendInvoiceUseCase {

    String sendInvoice(Long clienteMovimientoId, String email) throws MessagingException;

}