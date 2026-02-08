package eterea.report.report.engine;

import org.openpdf.text.Document;
import org.openpdf.text.PageSize;
import org.openpdf.text.Rectangle;
import org.openpdf.text.pdf.PdfWriter;

import java.io.OutputStream;

public class PdfReportBuilder {

    private Rectangle pageSize = PageSize.A4;
    private float marginLeft = 36;
    private float marginRight = 36;
    private float marginTop = 36;
    private float marginBottom = 36;

    private PageSection header;
    private PageSection footer;
    private ReportSection body;

    public PdfReportBuilder setPageSize(Rectangle pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PdfReportBuilder setOrientation(boolean landscape) {
        if (landscape) {
            if (this.pageSize.getWidth() < this.pageSize.getHeight()) {
                this.pageSize = this.pageSize.rotate();
            }
        } else {
            if (this.pageSize.getWidth() > this.pageSize.getHeight()) {
                this.pageSize = this.pageSize.rotate();
            }
        }
        return this;
    }

    public PdfReportBuilder setMargins(float left, float right, float top, float bottom) {
        this.marginLeft = left;
        this.marginRight = right;
        this.marginTop = top;
        this.marginBottom = bottom;
        return this;
    }

    public PdfReportBuilder setHeader(PageSection header) {
        this.header = header;
        return this;
    }

    public PdfReportBuilder setFooter(PageSection footer) {
        this.footer = footer;
        return this;
    }

    public PdfReportBuilder setBody(ReportSection body) {
        this.body = body;
        return this;
    }

    public void build(OutputStream outputStream) {
        Document document = new Document(pageSize, marginLeft, marginRight, marginTop, marginBottom);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            
            // Register Header/Footer event
            if (header != null || footer != null) {
                writer.setPageEvent(new HeaderFooterPageEvent(header, footer));
            }

            document.open();

            if (body != null) {
                body.render(document, writer);
            }

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error building PDF report", e);
        }
    }
}
