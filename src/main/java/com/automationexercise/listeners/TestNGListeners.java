package com.automationexercise.listeners;

import com.automationexercices.utils.report.AllureConstants;
import com.automationexercise.FileUtils;
import com.automationexercise.drivers.UITest;
import com.automationexercise.drivers.WebDriverProvider;
import com.automationexercise.media.ScreenRecordManager;
import com.automationexercise.media.ScreenshotsManager;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.utils.report.AllureAttachmentManager;
import com.automationexercise.utils.report.AllureEnvironmentManager;
import com.automationexercise.utils.report.AllureReportGenerator;
import com.automationexercise.validations.Validation;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListeners implements ISuiteListener, IExecutionListener, ITestListener, IInvokedMethodListener {
    public void onStart(ISuite suite) {
        suite.getXmlSuite().setName("Automation Exercise");
    }
    @Override
    public void onExecutionStart() {
        LogsManager.info("Test execution started.");
        cleanTestOutputResults();
        LogsManager.info("Test output results cleaned.");
        createTestOutputDirectories();
        LogsManager.info("Test output directories created.");
        PropertyReader.loadProperties();
        LogsManager.info("Properties loaded.");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsManager.info("Allure environment variables set.");

    }

    @Override
    public void onExecutionFinish() {
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("Test execution finished. Allure report generated and opened.");

    }


    @Override
    public void onTestSuccess(org.testng.ITestResult result) {
        LogsManager.info("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(org.testng.ITestResult result) {
        LogsManager.info("Test failed: " + result.getName());
    }

    @Override
    public void onTestSkipped(org.testng.ITestResult result) {
        LogsManager.info("Test skipped: " + result.getName());
    }

    @Override
    public void beforeInvocation(org.testng.IInvokedMethod method, org.testng.ITestResult testResult) {
        if(method.isTestMethod()){
            if(testResult.getInstance()instanceof UITest) {
                ScreenRecordManager.startRecording();

            }
            LogsManager.info("TestCase " + testResult.getName() + " started ");
        }
    }

    @Override
    public void afterInvocation(org.testng.IInvokedMethod method, org.testng.ITestResult testResult) {
        WebDriver driver = null;
        if(method.isTestMethod()){
            if(testResult.getInstance()instanceof UITest){
                ScreenRecordManager.stopRecording(testResult.getName());
                if(testResult.getInstance() instanceof WebDriverProvider provider){
                    driver = provider.getWebDriver(); //initialize driver from WebDriverProvider interface implemented in test class
                }
                switch (testResult.getStatus()) {
                    case ITestResult.SUCCESS -> ScreenshotsManager.takeFullPageScreenshot(driver,"passed-" + testResult.getName());
                    case ITestResult.FAILURE -> ScreenshotsManager.takeFullPageScreenshot(driver,"failed-" + testResult.getName());
                    case ITestResult.SKIP -> ScreenshotsManager.takeFullPageScreenshot(driver,"skipped-" + testResult.getName());
                }
                AllureAttachmentManager.attachRecords(testResult.getName());

            }
            Validation.assertAll(testResult);

            AllureAttachmentManager.attachLogs();
             LogsManager.info("TestCase " + testResult.getName() + " finished with status: " + testResult.getStatus());
        }
    }

    //cleaning and creating dirs(logs,recordings, screenshots, allure-results)
    private void cleanTestOutputResults() {
        // Implement logic to clean test output results (e.g., delete old logs, screenshots, etc.)
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File(ScreenshotsManager.SCREENSHOTS_PATH));
        FileUtils.cleanDirectory(new File(ScreenRecordManager.RECORDINGS_PATH));
        FileUtils.cleanDirectory(new File("src/test/resources/downloads/"));
        FileUtils.forceDelete(new File(LogsManager.LOGS_PATH +"logs.log") );

    }
    private void createTestOutputDirectories() {
        // Implement logic to create necessary directories for test output (e.g., logs, screenshots, allure-results)
        FileUtils.createDirectory(ScreenshotsManager.SCREENSHOTS_PATH);
        FileUtils.createDirectory(ScreenRecordManager.RECORDINGS_PATH);
        FileUtils.createDirectory("src/test/resources/downloads/");

    }
}
