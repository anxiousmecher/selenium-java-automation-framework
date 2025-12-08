package TestCases;

import Base.ReadExcel;
import Base.SheetName;
import Base.UserType;
import Base.base;
import Utility.commonsteps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC01_Verifylogin extends base {
    private Logger logger= LogManager.getLogger(this.getClass());
    @Test(dataProvider = "Readexcel", dataProviderClass = ReadExcel.class)
    @SheetName("Sauce")
    @UserType({"Invalid User","Standard user","locked user","Performance user","glitch user","error user"})
    public void TC01_Verifylogin(String username, String password) throws IOException {
        extent_test.info("Verify login test case started");
        pdfReportGenerator.coverpage("The objective of this test case is to verify user can loggin successfully with valid credentials","TC01_Verifylogin");
        commonsteps commonsteps=new commonsteps(driver,pdfReportGenerator);
        logger.info("Verify login page is displayed");
        commonsteps.verifyloginpage();
        if(commonsteps.stepresult)
        {
            extent_test.pass("User is navigated to login page");
            logger.info("Login page is displayed");
        }
        else
        {
            extent_test.fail("User is not navigated to login page").addScreenCaptureFromPath(commonsteps.screenshotpath.get(0));
        logger.error("Loginpage is not displayed");
        }
        commonsteps.verifylogin(username,password);
        logger.info("Verify user can Login");
        if(commonsteps.stepresult)
        {
            logger.info("User is logged in");
            extent_test.pass("User is logged in successfully");
        }
        else
        {
            extent_test.fail("User is unable to login").addScreenCaptureFromPath(commonsteps.screenshotpath.get(0));
            logger.error("user is not able to login");
        }
      if(commonsteps.overallstatus)
      {
          pdfReportGenerator.addstatustocoverpage("Pass");
      }
        if(!commonsteps.overallstatus)
        {
            pdfReportGenerator.addstatustocoverpage("Fail");
        }
        Assert.assertTrue(commonsteps.overallstatus);

    }

}
