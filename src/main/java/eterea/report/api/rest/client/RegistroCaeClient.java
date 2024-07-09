package eterea.report.api.rest.client;

import eterea.report.api.rest.kotlin.model.dto.RegistroCaeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "core-service/api/core/registroCae")
public interface RegistroCaeClient {

    @GetMapping("/unique/{comprobanteId}/{puntoVenta}/{numeroComprobante}")
    RegistroCaeDto findByUnique(@PathVariable Integer comprobanteId, @PathVariable Integer puntoVenta, @PathVariable Long numeroComprobante);

}
