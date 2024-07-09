package eterea.report.api.rest.client;

import eterea.report.api.rest.kotlin.model.dto.ClienteDto;
import eterea.report.api.rest.kotlin.model.dto.ClienteSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "core-service/api/core/cliente")
public interface ClienteClient {

    @GetMapping("/search/{chain}")
    List<ClienteSearchDto> findAllBySearch(@PathVariable String chain);

    @GetMapping("/{clienteId}")
    ClienteDto findByClienteId(@PathVariable Long clienteId);

    @GetMapping("/numeroDocumento/{numeroDocumento}")
    ClienteDto findByNumeroDocumento(@PathVariable String numeroDocumento);

    @GetMapping("/last")
    ClienteDto findLast();

    @PostMapping("/")
    ClienteDto add(@RequestBody ClienteDto cliente);

}
