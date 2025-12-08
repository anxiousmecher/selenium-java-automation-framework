package Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extentReports;
    private static ExtentSparkReporter sparkerporter;
    public static ExtentReports getInstance()
    {
        String reportpath="test-output/extentreports/"+System.currentTimeMillis();
        sparkerporter=new ExtentSparkReporter(reportpath);
        extentReports=new ExtentReports();
        extentReports.attachReporter(sparkerporter);
        extentReports.setSystemInfo("Environment","Test");
        extentReports.setSystemInfo("Tester",System.getProperty("user.name"));
        return extentReports;
    }
}
