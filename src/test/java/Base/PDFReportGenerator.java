package Base;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.IOException;

public class PDFReportGenerator {

    private PDDocument document;
    private PDPageContentStream contentStream;
    float cover_status;

    public PDFReportGenerator() {
        document = new PDDocument();
    }

    // Updated to match Code 1 behavior
    public float drawrow(String label, String value, float x, float y, float labelwidth, float valuewidth, float rowheight) throws IOException {

        label = label.replace("\n", " ");
        value = value.replace("\n", " ");

        String[] lines = toline(value);

        float totalheight;

        // Match Code 1 logic
        if (lines.length == 1) {
            totalheight = rowheight * lines.length;
        } else {
            totalheight = (rowheight * lines.length) / 2;
        }

        // Draw rectangle
        contentStream.addRect(x, y - totalheight, labelwidth + valuewidth, totalheight);
        contentStream.stroke();

        // Draw vertical divider
        contentStream.moveTo(x + labelwidth, y);
        contentStream.lineTo(x + labelwidth, y - totalheight);
        contentStream.stroke();

        // Draw label text
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(x + 10, y - 10);
        contentStream.showText(label);
        contentStream.endText();

        // Draw multiline value
        float z = y;
        for (String line : lines) {
            contentStream.beginText();
            contentStream.newLineAtOffset(x + 10 + labelwidth, z - 10);
            contentStream.showText(line);
            contentStream.endText();
            z -= 10;  // same spacing as Code 1
        }

        return y - totalheight;
    }

    public void createstep(String objective, String expected, String actual, String status, String screenshotpath) throws IOException {
        if (contentStream != null) {
            contentStream.close();
            contentStream = null;
        }

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);

        float x = 10;
        float y = page.getMediaBox().getHeight();
        float labelwidth = 100;
        float valuewidth = 400;
        float rowheight = 25;

        y = drawrow("Objective", objective, x, y - 20, labelwidth, valuewidth, rowheight);
        y = drawrow("Expected", expected, x, y, labelwidth, valuewidth, rowheight);
        y = drawrow("Actual", actual, x, y, labelwidth, valuewidth, rowheight);
        y = drawrow("Status", status, x, y, labelwidth, valuewidth, rowheight);

        PDImageXObject image = PDImageXObject.createFromFile(screenshotpath, document);
        contentStream.drawImage(image, x + 10, y - 250, labelwidth + valuewidth - 20, 250);

        contentStream.addRect(x, y - 280, labelwidth + valuewidth, 280);
        contentStream.stroke();

        contentStream.close();
    }

    public void coverpage(String description, String testcasename) throws IOException {

        PDPage coverpage = new PDPage(PDRectangle.A4);
        document.addPage(coverpage);
        contentStream = new PDPageContentStream(document, coverpage);

        float pagewidth = coverpage.getMediaBox().getWidth();
        float y = coverpage.getMediaBox().getHeight();

        contentStream.setFont(PDType1Font.TIMES_ROMAN, 18);
        contentStream.beginText();
        contentStream.newLineAtOffset((pagewidth / 2) - 50, y - 50);
        contentStream.showText("Test Execution Report");
        contentStream.endText();

        y -= 100;

        y = drawrow("Description", description, 10, y, 100, 400, 25);
        y = drawrow("Test Case", testcasename, 10, y, 100, 400, 25);
        y = drawrow("Tester Name", System.getProperty("user.name"), 10, y, 100, 400, 25);
        y = drawrow("Timestamp",
                new java.text.SimpleDateFormat("ddMMyyyy_HHmmss").format(new java.util.Date()),
                10, y, 100, 400, 25);
        y = drawrow("Status", "", 10, y, 100, 400, 25);

        cover_status = y;
        contentStream.close();
    }

    public void addstatustocoverpage(String status) throws IOException {

        PDPage coverpage = document.getPage(0);
        contentStream = new PDPageContentStream(document, coverpage, PDPageContentStream.AppendMode.APPEND, true);

        if (status.equalsIgnoreCase("Pass"))
            contentStream.setNonStrokingColor(Color.GREEN);
        else if (status.equalsIgnoreCase("Fail"))
            contentStream.setNonStrokingColor(Color.RED);
        else
            contentStream.setNonStrokingColor(Color.BLACK);

        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(120, cover_status + 10);
        contentStream.showText(status);
        contentStream.endText();
        contentStream.close();
    }

    public String[] toline(String value) {
        return value.split("(?<=\\G.{" + 70 + "})");
    }

    public void save(String reportspath) throws IOException {
        if (contentStream != null) {
            contentStream.close();
            contentStream = null;
        }
        document.save(reportspath);
        document.close();
    }
}
