package eterea.report.factura.domain.ports.in;

import eterea.report.factura.domain.model.InvoiceData;

public interface GetInvoiceDataUseCase {

    InvoiceData getInvoiceDataByClienteMovimientoId(Long clienteMovimientoId);

}
