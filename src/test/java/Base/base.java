package Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class base {
    protected Logger logger= LogManager.getLogger(this.getClass());
    protected WebDriver driver;
    protected PDFReportGenerator pdfReportGenerator;
    protected ExtentReports extent_report;
    protected ExtentTest extent_test;

    @BeforeSuite
            public void createextentreport()
    {
        extent_report=ExtentManager.getInstance();
    }
    @BeforeMethod
    public void setup(Method method)
    {
        logger.info("----------------------------Started test "+method.getName()+"-----------------------");
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
           String reportfolder="reports/"+method.getName();
            File directory=new File("reports/"+reportfolder);
            if(!directory.exists())
            {
                directory.mkdirs();
            }
            String reportspathfile="reports/"+reportfolder+"/"+reportpath+".pdf";
            pdfReportGenerator.save(reportspathfile);
        }
        if(driver!=null)
        {
            driver.quit();
        }
        logger.info("----------------------------Ended[ test "+method.getName()+"-----------------------");
    }
    @AfterSuite
    public void extentflush()
    {
        if(extent_report!=null) {
            extent_report.flush();
        }
    }

}
