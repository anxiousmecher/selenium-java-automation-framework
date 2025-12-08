package Base;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.analysis.function.Exp;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class StepLogger {
    private WebDriver driver;
    private PDFReportGenerator pdfReportGenerator;
    public StepLogger(WebDriver driver, PDFReportGenerator pdfReportGenerator)
    {
        this.driver=driver;
        this.pdfReportGenerator =pdfReportGenerator;
    }
    public String CaptureStep(String Objective, String Expected, String Actual, String Status) throws IOException {
        File screenshotfile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String screenshotpath="output/screenshot"+System.currentTimeMillis()+".png";
        String relativepath=System.getProperty("user.dir")+"/"+screenshotpath;
        FileUtils.copyFile(screenshotfile,new File(screenshotpath));
        FileUtils.copyFile(screenshotfile,new File(relativepath));
        pdfReportGenerator.createstep(Objective, Expected,Actual, Status,screenshotpath);
        return relativepath;
    }
}
