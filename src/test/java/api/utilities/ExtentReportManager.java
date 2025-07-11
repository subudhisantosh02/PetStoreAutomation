package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private ExtentSparkReporter sparkReporter;
    private String reportName;

    @Override
    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        reportName = "API_Test_Report_" + timeStamp + ".html";

        String reportDir = System.getProperty("user.dir") + "/reports/";
        new File(System.getProperty("user.dir") + "/reports").mkdirs(); // Ensure the directory exists

        sparkReporter = new ExtentSparkReporter(reportDir + reportName);
        sparkReporter.config().setDocumentTitle("RestAssured API Automation Report");
        sparkReporter.config().setReportName("PetStore REST API Test Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Project Name", "Pet Store API");
        extent.setSystemInfo("QA", "Your Name");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tool", "Rest Assured + Java + TestNG");
        extent.setSystemInfo("OS User", System.getProperty("user.name"));
        extent.setSystemInfo("user", "Santosh");
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        extentTest.assignCategory(result.getMethod().getGroups());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed");
        test.get().log(Status.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
        if (result.getThrowable() != null) {
            test.get().log(Status.SKIP, result.getThrowable().getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext testContext) {
        if (extent != null) {
            extent.flush();
        }
    }
}
