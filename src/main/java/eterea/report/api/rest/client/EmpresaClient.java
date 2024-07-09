package eterea.report.api.rest.client;

import eterea.report.api.rest.kotlin.model.dto.EmpresaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "core-service/api/core/empresa")
public interface EmpresaClient {

    @GetMapping("/top")
    EmpresaDto findTop();

}
