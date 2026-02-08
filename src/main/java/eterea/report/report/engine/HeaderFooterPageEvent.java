package eterea.report.report.engine;

import lombok.RequiredArgsConstructor;
import org.openpdf.text.Document;
import org.openpdf.text.pdf.PdfPageEventHelper;
import org.openpdf.text.pdf.PdfWriter;

@RequiredArgsConstructor
public class HeaderFooterPageEvent extends PdfPageEventHelper {

    private final PageSection header;
    private final PageSection footer;

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        if (header != null) {
            try {
                header.render(document, writer);
            } catch (Exception e) {
                throw new RuntimeException("Error rendering header", e);
            }
        }
        
        if (footer != null) {
            try {
                footer.render(document, writer);
            } catch (Exception e) {
                throw new RuntimeException("Error rendering footer", e);
            }
        }
    }

}
