package eterea.report.api.rest.factura.infrastructure.client;

import eterea.report.api.rest.factura.domain.model.InvoiceData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "core-service", contextId = "invoiceDataClient", path = "/api/core/invoiceData")
public interface InvoiceDataClient {

    @GetMapping("/{clienteMovimientoId}")
    InvoiceData getInvoiceDataByClienteMovimientoId(@PathVariable Long clienteMovimientoId);

}
