package eterea.report.api.rest.client;

import eterea.report.api.rest.kotlin.model.dto.ComprobanteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "core-service/api/core/comprobante")
public interface ComprobanteClient {

    @GetMapping("/modulo/{modulo}/{debita}/{comprobanteId}")
    List<ComprobanteDto> findAllByModulo(@PathVariable Integer modulo, @PathVariable Byte debita,
                                         @PathVariable Integer comprobanteId);

    @GetMapping("/{comprobanteId}")
    ComprobanteDto findByComprobanteId(@PathVariable Integer comprobanteId);

}
