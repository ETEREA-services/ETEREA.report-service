package eterea.report.api.rest.factura.application.service;

import eterea.report.api.rest.factura.domain.model.InvoiceData;
import eterea.report.api.rest.factura.domain.ports.in.GetInvoiceDataUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceDataService {

    private final GetInvoiceDataUseCase getInvoiceDataUseCase;

    public InvoiceData getInvoiceDataByClienteMovimientoId(Long clienteMovimientoId) {
        return getInvoiceDataUseCase.getInvoiceDataByClienteMovimientoId(clienteMovimientoId);
    }

}
