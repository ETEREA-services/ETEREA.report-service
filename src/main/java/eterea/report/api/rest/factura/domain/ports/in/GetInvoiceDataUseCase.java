package eterea.report.api.rest.factura.domain.ports.in;

import eterea.report.api.rest.factura.domain.model.InvoiceData;

public interface GetInvoiceDataUseCase {

    InvoiceData getInvoiceDataByClienteMovimientoId(Long clienteMovimientoId);

}
