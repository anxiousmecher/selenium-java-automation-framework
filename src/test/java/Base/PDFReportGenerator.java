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
public PDFReportGenerator()
{
    document=new PDDocument();
}
public float drawrow(String label, String value, float x, float y, float labelwith, float valuewidth, float rowheight) throws IOException {
    label=label.replace("\n"," ");
    value=value.replace("\n"," ");
    String[] lines=toline(value);
    float totalheight=rowheight*lines.length;
    //draw rectangle
    contentStream.addRect(x,y-totalheight,labelwith+valuewidth,totalheight);
    contentStream.stroke();
    contentStream.moveTo(x+labelwith,y);
    contentStream.lineTo(x+labelwith,y-totalheight);
    contentStream.stroke();

    //draw label
    contentStream.setFont(PDType1Font.TIMES_ROMAN,12);
    contentStream.beginText();
    contentStream.newLineAtOffset(x+5,y-10);
    contentStream.showText(label);
    contentStream.endText();
    float z=y-10;
    contentStream.setFont(PDType1Font.TIMES_ROMAN,12);
    for(String line:lines)
    {
        contentStream.beginText();
        contentStream.newLineAtOffset(x+5+labelwith,z-10);
        contentStream.showText(line);
        contentStream.endText();
        z=z-10;
    }
    return y-totalheight;
}
public void createstep(String objective, String expected, String actual, String status,String screenshotpath) throws IOException {
    if(contentStream!=null)
    {
        contentStream.close();
        contentStream=null;
    }
    PDPage page=new PDPage(PDRectangle.A4);
    document.addPage(page);
    contentStream=new PDPageContentStream(document,page);
    float x=5;
    float y=page.getMediaBox().getHeight();
    float labelwidth=100;
    float valuewidth=400;
    y=drawrow("Objective",objective,x,y,labelwidth,valuewidth,25);
    y=drawrow("Expected",expected,x,y,labelwidth,valuewidth,25);
    y=drawrow("Actual",actual,x,y,labelwidth,valuewidth,25);
    y=drawrow("Status",status,x,y,labelwidth,valuewidth,25);

    PDImageXObject image= PDImageXObject.createFromFile(screenshotpath,document);
    contentStream.drawImage(image,20,y-250,labelwidth+valuewidth-40,250);
    contentStream.addRect(10,y-280,labelwidth+valuewidth,280);
    contentStream.stroke();
    contentStream.close();
}

public void coverpage(String description, String testcasename) throws IOException {
PDPage coverpage=new PDPage();
document.addPage(coverpage);
contentStream=new PDPageContentStream(document,coverpage);
contentStream.setFont(PDType1Font.TIMES_ROMAN,18);
contentStream.beginText();
float pagewidth= coverpage.getMediaBox().getWidth();
float pagelength=coverpage.getMediaBox().getHeight();
contentStream.newLineAtOffset((pagewidth/2)-50,pagelength-10);
contentStream.showText("Test Execution Report");
contentStream.endText();
float y=pagelength-20;
    y=drawrow("Description",description,10,y,100,400,25);
    y=drawrow("Test Case",testcasename,10,y,100,400,25);
    y=drawrow("Tester Name",System.getProperty("user.name"),10,y,100,400,25);
    y=drawrow("Timestamp",new java.text.SimpleDateFormat("ddMMyyyy_HHmmss").format(new java.util.Date()),10,y,100,400,25);
    y=drawrow("Status","",10,y,100,400,25);
    cover_status=y;
    contentStream.close();
}
public void addstatustocoverpage(String status) throws IOException {
    PDPage coverpage=document.getPage(0);
    contentStream=new PDPageContentStream(document,coverpage, PDPageContentStream.AppendMode.APPEND,true);
    if(status.equalsIgnoreCase("Pass"))
    {
        contentStream.setNonStrokingColor(Color.GREEN);
    }
   else if(status.equalsIgnoreCase("Fail"))
    {
        contentStream.setNonStrokingColor(Color.RED);
    }
else
    {
        contentStream.setNonStrokingColor(Color.black);
    }
contentStream.setFont(PDType1Font.TIMES_ROMAN,12);
contentStream.beginText();
contentStream.newLineAtOffset(110,cover_status);
contentStream.showText(status);
contentStream.endText();
contentStream.close();
}
public String[] toline(String value)
{
    return value.split("(?<=\\G.{" + 70 + "})");
}
public void save(String reportspath) throws IOException {
    if(contentStream!=null)
    {
        contentStream.close();
        contentStream=null;
    }
    document.save(reportspath);
    document.close();
}
}
