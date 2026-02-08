package eterea.report.factura.infrastructure.repository;

import eterea.report.factura.domain.model.InvoiceData;
import eterea.report.factura.domain.ports.out.InvoiceDataRepository;
import eterea.report.factura.infrastructure.client.InvoiceDataClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceDataRepositoryImpl implements InvoiceDataRepository {

    private final InvoiceDataClient invoiceDataClient;

    @Override
    public InvoiceData findByClienteMovimientoId(Long clienteMovimientoId) {
        return invoiceDataClient.getInvoiceDataByClienteMovimientoId(clienteMovimientoId);
    }

}