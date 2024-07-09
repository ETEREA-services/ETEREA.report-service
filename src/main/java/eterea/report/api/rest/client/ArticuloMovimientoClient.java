package eterea.report.api.rest.client;

import eterea.report.api.rest.kotlin.model.dto.ArticuloMovimientoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "core-service/api/core/articuloMovimiento")
public interface ArticuloMovimientoClient {

    @GetMapping("/clienteMovimiento/{clienteMovimientoId}")
    List<ArticuloMovimientoDto> findAllByClienteMovimientoId(@PathVariable Long clienteMovimientoId);

    @GetMapping("/{articuloMovimientoId}")
    ArticuloMovimientoDto findByArticuloMovimientoId(@PathVariable Long articuloMovimientoId);

}
