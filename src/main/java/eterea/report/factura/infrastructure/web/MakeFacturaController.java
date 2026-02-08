package eterea.report.factura.infrastructure.web;

import eterea.report.factura.domain.ports.in.GenerateInvoicePdfUseCase;
import eterea.report.factura.domain.ports.in.SendInvoiceUseCase;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report/makeFactura")
@RequiredArgsConstructor
public class MakeFacturaController {

    private final SendInvoiceUseCase sendInvoiceUseCase;
    private final GenerateInvoicePdfUseCase generateInvoicePdfUseCase;

    @GetMapping("/pdf/{clienteMovimientoId}")
    public ResponseEntity<Resource> makePdf(@PathVariable Long clienteMovimientoId) {
        Resource resource = generateInvoicePdfUseCase.generatePdf(clienteMovimientoId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura.pdf");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        
        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/send/{clienteMovimientoId}/{email}")
    public ResponseEntity<String> send(@PathVariable Long clienteMovimientoId, @PathVariable String email) throws MessagingException {
        return new ResponseEntity<>(sendInvoiceUseCase.sendInvoice(clienteMovimientoId, email), HttpStatus.OK);
    }

}
