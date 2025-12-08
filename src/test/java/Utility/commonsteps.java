package Utility;


import Base.PDFReportGenerator;
import Base.StepLogger;
import PageObjects.Homepage;
import PageObjects.LoginPage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.ArrayList;

public class commonsteps {
private static WebDriver driver;
private static PDFReportGenerator pdfReportGenerator;
public commonsteps(WebDriver driver, PDFReportGenerator pdfReportGenerator)
{
    commonsteps.driver =driver;
    commonsteps.pdfReportGenerator =pdfReportGenerator;
}
public ArrayList<String> screenshotpath=new ArrayList<>();
public boolean overallstatus=true;
public boolean stepresult=true;
public void verifyloginpage() throws IOException {
    screenshotpath.clear();
    StepLogger stepLogger=new StepLogger(driver,pdfReportGenerator);
    try{
        if(driver.findElement(LoginPage.loginpageheader).isDisplayed())
        {
            stepLogger.CaptureStep("Verify User is navigated to Login page","User shall be navigated to login page","User is navigated to login page","Pass");
        stepresult=true;
        overallstatus=overallstatus && stepresult;
        }
        else
        {
            screenshotpath.add(stepLogger.CaptureStep("Verify User is navigated to Login page","User shall be navigated to login page","User is not navigated to login page","Fail"));
            stepresult=false;
            overallstatus=overallstatus && stepresult;
        }
    } catch (Exception e) {
        screenshotpath.add(stepLogger.CaptureStep("Verify User is navigated to Login page","User shall be navigated to login page","User is not navigated to login page due to error: "+e.getMessage(),"Fail"));
        stepresult=false;
        overallstatus=overallstatus && stepresult;
    }
}

    public void verifylogin(String username, String password) throws IOException {
        screenshotpath.clear();
        StepLogger stepLogger=new StepLogger(driver,pdfReportGenerator);
        try{
           commonactions.inputdata(driver.findElement(LoginPage.loginpage_username_textbox),username);
            commonactions.inputdata(driver.findElement(LoginPage.loginpage_password_textbox),password);
            commonactions.clickelement(driver.findElement(LoginPage.loginpage_loginbutton));
            if(driver.findElement(Homepage.cart).isDisplayed())
            {
                stepLogger.CaptureStep("Verify User is navigated to Home page","User shall be navigated to Home page","User is navigated to Home page","Pass");
                stepresult=true;
                overallstatus=overallstatus && stepresult;
            }
            else
            {
                screenshotpath.add(stepLogger.CaptureStep("Verify User is navigated to Login page","User shall be navigated to Home page","User is not navigated to Home page","Fail"));
                stepresult=false;
                overallstatus=overallstatus && stepresult;
            }
        } catch (Exception e) {
            screenshotpath.add(stepLogger.CaptureStep("Verify User is navigated to Login page","User shall be navigated to Home page","User is not navigated to Home page due to error: "+e.getMessage(),"Fail"));
            stepresult=false;
            overallstatus=overallstatus && stepresult;
        }
    }

}
