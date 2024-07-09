package eterea.report.api.rest.client;

import eterea.report.api.rest.kotlin.model.dto.MonedaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "core-service/api/core/moneda")
public interface MonedaClient {

    @GetMapping("/{monedaId}")
    MonedaDto findByMonedaId(@PathVariable Integer monedaId);

}
