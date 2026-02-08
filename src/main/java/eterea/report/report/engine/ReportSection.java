package eterea.report.report.engine;

import org.openpdf.text.Document;
import org.openpdf.text.pdf.PdfWriter;

@FunctionalInterface
public interface ReportSection {
    void render(Document document, PdfWriter writer) throws Exception;
}
