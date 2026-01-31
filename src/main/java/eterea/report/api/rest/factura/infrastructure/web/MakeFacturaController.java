package eterea.report.api.rest.factura.infrastructure.web;

import eterea.report.api.rest.factura.infrastructure.service.FacturaPdfService;
import eterea.report.api.rest.factura.infrastructure.service.MakeFacturaService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/report/makeFactura")
@RequiredArgsConstructor
public class MakeFacturaController {

    private final MakeFacturaService service;
    private final FacturaPdfService facturaPdfService;

    @GetMapping("/pdf/{clienteMovimientoId}")
    public ResponseEntity<Resource> makePdf(@PathVariable Long clienteMovimientoId) throws FileNotFoundException {
        String filename = facturaPdfService.generatePdf(clienteMovimientoId, null);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura.pdf");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/send/{clienteMovimientoId}/{email}")
    public ResponseEntity<String> send(@PathVariable Long clienteMovimientoId, @PathVariable String email) throws MessagingException {
        return new ResponseEntity<>(service.send(clienteMovimientoId, email), HttpStatus.OK);
    }

}
