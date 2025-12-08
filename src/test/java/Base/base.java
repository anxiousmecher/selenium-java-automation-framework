package Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class base {
    protected WebDriver driver;
    protected PDFReportGenerator pdfReportGenerator;
    protected ExtentReports extent_report;
    protected ExtentTest extent_test;

    @BeforeMethod
    public void setup(Method method)
    {
        extent_report=ExtentManager.getInstance();
        extent_test=extent_report.createTest(method.getName());
        System.setProperty("webdriver.chrome.driver","driver/chromedriver.exe");
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--incognito");
        driver=new ChromeDriver(options);
        driver.get("https://www.saucedemo.com");
        driver.manage().window().maximize();
        pdfReportGenerator=new PDFReportGenerator();
    }
    @AfterMethod
    public void savepdf(Method method) throws IOException {
        if(pdfReportGenerator!=null)
        {
            String reportpath=new java.text.SimpleDateFormat("ddMMyyyy_HHmmss").format(new java.util.Date());
            File directory=new File("reports/"+reportpath);
            if(!directory.exists())
            {
                directory.mkdirs();
            }
            String reportspathfile="reports/"+reportpath+"/"+reportpath+".pdf";
            pdfReportGenerator.save(reportspathfile);
        }
        if(driver!=null)
        {
            driver.quit();
        }
    }
    @AfterSuite
    public void extentflush()
    {
        if(extent_report!=null) {
            extent_report.flush();
        }
    }

}
