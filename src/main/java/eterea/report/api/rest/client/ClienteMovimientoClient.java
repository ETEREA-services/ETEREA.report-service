package eterea.report.api.rest.client;

import eterea.report.api.rest.kotlin.model.dto.ClienteMovimientoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "core-service/api/core/clienteMovimiento")
public interface ClienteMovimientoClient {

    @GetMapping("/asociable/{clienteId}")
    List<ClienteMovimientoDto> findTop200Asociables(@PathVariable Long clienteId);

    @GetMapping("/last/{puntoVenta}/{letraComprobante}")
    Long nextNumeroFactura(@PathVariable Integer puntoVenta, @PathVariable String letraComprobante);

    @GetMapping("/{clienteMovimientoId}")
    ClienteMovimientoDto findByClienteMovimientoId(@PathVariable Long clienteMovimientoId);

}
