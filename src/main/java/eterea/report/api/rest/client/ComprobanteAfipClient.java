package eterea.report.api.rest.client;

import eterea.report.api.rest.kotlin.model.dto.ComprobanteAfipDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "core-service/api/core/comprobanteAfip")
public interface ComprobanteAfipClient {

    @GetMapping("/{comprobanteAfipId}")
    ComprobanteAfipDto findByComprobanteAfipId(@PathVariable Integer comprobanteAfipId);

}
