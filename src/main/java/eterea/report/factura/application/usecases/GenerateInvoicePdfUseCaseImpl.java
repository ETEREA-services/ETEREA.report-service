package eterea.report.factura.application.usecases;

import eterea.report.factura.domain.ports.in.GenerateInvoicePdfUseCase;
import eterea.report.factura.domain.ports.out.InvoiceDataRepository;
import eterea.report.factura.infrastructure.service.FacturaPdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
@Slf4j
@RequiredArgsConstructor
public class GenerateInvoicePdfUseCaseImpl implements GenerateInvoicePdfUseCase {

    private final InvoiceDataRepository invoiceDataRepository;
    private final FacturaPdfService facturaPdfService;

    @Override
    public Resource generatePdf(Long clienteMovimientoId) {
        log.debug("\n\nProcessing GenerateInvoicePdfUseCaseImpl.generatePdf\n\n");
        try {
            var invoiceData = invoiceDataRepository.findByClienteMovimientoId(clienteMovimientoId);
            log.debug("\n\nInvoice data found: {}\n\n", invoiceData.jsonify());
            String filename = facturaPdfService.generatePdf(clienteMovimientoId, invoiceData);
            log.debug("\n\nInvoice filename: {}\n\n", filename);
            File file = new File(filename);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return new ByteArrayResource(fileContent) {
                @Override
                public String getFilename() {
                    return file.getName();
                }
            };
        } catch (IOException e) {
            log.error("PDF file not found for clienteMovimientoId: {}", clienteMovimientoId, e);
            throw new RuntimeException("PDF file not found", e);
        }
    }

}