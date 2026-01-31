package eterea.report.api.rest.factura.application.usecases;

import eterea.report.api.rest.factura.domain.model.InvoiceData;
import eterea.report.api.rest.factura.domain.ports.in.GetInvoiceDataUseCase;
import eterea.report.api.rest.factura.infrastructure.client.InvoiceDataClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetInvoiceDataUseCaseImpl implements GetInvoiceDataUseCase {

    private final InvoiceDataClient invoiceDataClient;

    @Override
    public InvoiceData getInvoiceDataByClienteMovimientoId(Long clienteMovimientoId) {
        return invoiceDataClient.getInvoiceDataByClienteMovimientoId(clienteMovimientoId);
    }

}
