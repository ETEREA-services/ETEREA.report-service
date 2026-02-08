package eterea.report.factura.application.usecases;

import eterea.report.factura.domain.model.InvoiceData;
import eterea.report.factura.domain.ports.in.GetInvoiceDataUseCase;
import eterea.report.factura.domain.ports.out.InvoiceDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetInvoiceDataUseCaseImpl implements GetInvoiceDataUseCase {

    private final InvoiceDataRepository invoiceDataRepository;

    @Override
    public InvoiceData getInvoiceDataByClienteMovimientoId(Long clienteMovimientoId) {
        return invoiceDataRepository.findByClienteMovimientoId(clienteMovimientoId);
    }

}
