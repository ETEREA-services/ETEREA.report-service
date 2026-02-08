package eterea.report.factura.domain.ports.in;

import org.springframework.core.io.Resource;

public interface GenerateInvoicePdfUseCase {

    Resource generatePdf(Long clienteMovimientoId);

}