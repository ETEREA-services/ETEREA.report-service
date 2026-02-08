package eterea.report.factura.domain.ports.out;

import eterea.report.factura.domain.model.InvoiceData;

public interface InvoiceDataRepository {
    
    InvoiceData findByClienteMovimientoId(Long clienteMovimientoId);
    
}
