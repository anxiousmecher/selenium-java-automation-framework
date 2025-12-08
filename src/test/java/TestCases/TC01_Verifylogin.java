package TestCases;

import Base.ReadExcel;
import Base.SheetName;
import Base.UserType;
import Base.base;
import Utility.commonsteps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC01_Verifylogin extends base {
    @Test(dataProvider = "Readexcel", dataProviderClass = ReadExcel.class)
    @SheetName("Sauce")
    @UserType({"Invalid User","Standard user","locked user","Performance user","glitch user","error user"})
    public void TC01_Verifylogin(String username, String password) throws IOException {
        extent_test.info("Verify login test case started");
        pdfReportGenerator.coverpage("The objective of this test case is to verify user can loggin successfully with valid credentials","TC01_Verifylogin");
        commonsteps commonsteps=new commonsteps(driver,pdfReportGenerator);
        commonsteps.verifyloginpage();
        if(commonsteps.stepresult)
        {
            extent_test.pass("User is navigated to login page");
        }
        else
        {
            extent_test.fail("User is not navigated to login page").addScreenCaptureFromPath(commonsteps.screenshotpath.get(0));
        }
        commonsteps.verifylogin(username,password);
        if(commonsteps.stepresult)
        {
            extent_test.pass("User is logged in successfully");
        }
        else
        {
            extent_test.fail("User is unable to login").addScreenCaptureFromPath(commonsteps.screenshotpath.get(0));
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
