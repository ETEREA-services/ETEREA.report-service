package eterea.report.api.rest.client;

import eterea.report.api.rest.kotlin.model.dto.ConceptoFacturadoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "core-service/api/core/conceptoFacturado")
public interface ConceptoFacturadoClient {

    @GetMapping("/articuloMovimiento/{articuloMovimientoId}")
    ConceptoFacturadoDto findByArticuloMovimientoId(@PathVariable Long articuloMovimientoId);

}
