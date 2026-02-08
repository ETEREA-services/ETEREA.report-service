package eterea.report.report.sample;

import eterea.report.report.engine.PageSection;
import eterea.report.report.engine.PdfReportBuilder;
import eterea.report.report.engine.ReportSection;
import org.openpdf.text.*;
import org.openpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class SampleReportService {

    public void generateSampleReport(OutputStream outputStream) {
        new PdfReportBuilder()
                .setPageSize(PageSize.A4)
                .setMargins(36, 36, 100, 50) // Top margin larger for header, Bottom for footer
                .setHeader(createHeader())
                .setFooter(createFooter())
                .setBody(createBody())
                .setOrientation(false)
                .build(outputStream);
    }

    private PageSection createHeader() {
        return (document, writer) -> {
            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setTotalWidth(document.right() - document.left());
            headerTable.setLockedWidth(true);
            headerTable.getDefaultCell().setBorder(Rectangle.BOTTOM);
            headerTable.getDefaultCell().setBorderWidth(2f);
            headerTable.addCell(new Paragraph("EMPRESA DEMO S.A.", new Font(Font.HELVETICA, 12, Font.BOLD)));
            headerTable.addCell(new Paragraph("Reporte General", new Font(Font.HELVETICA, 10)));

            // Write absolute position. 
            // document.top() is the top of the body area. 
            // We want the header above that.
            // Page height is document.getPageSize().getHeight()
            // We can position it relative to the top margin.
            float yPosition = document.top() + 80; 
            
            headerTable.writeSelectedRows(0, -1, document.left(), yPosition, writer.getDirectContent());
        };
    }

    private PageSection createFooter() {
        return (document, writer) -> {
            PdfPTable footerTable = new PdfPTable(1);
            footerTable.setTotalWidth(document.right() - document.left());
            footerTable.setLockedWidth(true);
            footerTable.getDefaultCell().setBorder(Rectangle.TOP);
            footerTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
            String pageInfo = "Página " + writer.getPageNumber();
            footerTable.addCell(new Paragraph(pageInfo, new Font(Font.HELVETICA, 8)));

            // Position at the bottom margin
            footerTable.writeSelectedRows(0, -1, document.left(), document.bottom() - 10, writer.getDirectContent());
        };
    }

    private ReportSection createBody() {
        return (document, writer) -> {
            Font font = new Font(Font.HELVETICA, 10);
            for (int i = 0; i < 100; i++) {
                document.add(new Paragraph("Línea de reporte número " + (i + 1) + ". Este contenido se paginará automáticamente.", font));
            }
        };
    }
}
