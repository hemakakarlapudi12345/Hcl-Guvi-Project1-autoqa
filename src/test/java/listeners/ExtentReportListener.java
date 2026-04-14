package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportListener implements ITestListener {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }
    
    @Override
    public void onStart(ITestContext context) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = "reports/ExtentReport_" + timestamp + ".html";
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Selenium TestNG Framework");
        sparkReporter.config().setTheme(Theme.STANDARD);
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", System.getProperty("browser", "Chrome"));
        extent.setSystemInfo("Tester", "Automation Team");
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        test.log(Status.INFO, "Test Started: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        test = extentTest.get();
        if (test != null) {
            test.log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
        }
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        test = extentTest.get();
        if (test != null) {
            test.log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
            test.log(Status.FAIL, "Failure Reason: " + result.getThrowable().getMessage());
            
            // Capture screenshot on failure
            String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
            if (screenshotPath != null) {
                test.addScreenCaptureFromPath(screenshotPath);
                test.log(Status.FAIL, "Screenshot attached", 
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        test = extentTest.get();
        if (test != null) {
            test.log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
            test.log(Status.SKIP, "Skip Reason: " + result.getThrowable().getMessage());
        }
    }
    
    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
    
    private String captureScreenshot(String testName) {
        try {
            WebDriver currentDriver = driver.get();
            if (currentDriver == null) {
                return null;
            }
            
            // Create screenshots directory if it doesn't exist
            Path screenshotsDir = Paths.get("screenshots");
            if (!Files.exists(screenshotsDir)) {
                Files.createDirectories(screenshotsDir);
            }
            
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String screenshotName = testName + "_" + timestamp + ".png";
            String screenshotPath = "screenshots/" + screenshotName;
            
            TakesScreenshot screenshot = (TakesScreenshot) currentDriver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            File destination = new File(screenshotPath);
            
            Files.copy(source.toPath(), destination.toPath());
            
            return screenshotPath;
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    public static ExtentTest getTest() {
        return extentTest.get();
    }
}
