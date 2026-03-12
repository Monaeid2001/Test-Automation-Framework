package com.automationexercise.media;

import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.utils.report.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenshotsManager {
    public static final String SCREENSHOTS_PATH = "test-output/screenshots/";

    //take full page screenshot
    public static void takeFullPageScreenshot(WebDriver driver, String screenshotName) {
        // Implementation for taking full page screenshot
        //capture screeshot using TakesScreenshot interface and save it with screenshotName\
        try {
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            //save the screenshot to desired location
            File screenshotFile = new File( SCREENSHOTS_PATH+screenshotName+ "-" + TimeManager.getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc,screenshotFile);
            AllureAttachmentManager.attachScreenshot(screenshotName,screenshotFile.getAbsolutePath());


            //TODO : Attach the screenshot to Allure report
            LogsManager.info("Screenshot captured");


        } catch (Exception e) {
            LogsManager.error("Failed to capture screenshot: " + e.getMessage());

        }
    }
    //take screenshot of specific element
    public static void takeElementScreenshot(WebDriver driver, By elementSelector) {
        // Implementation for taking element screenshot
        //capture screeshot using TakesScreenshot interface and save it with screenshotName\
        try {
            String ariaName = driver.findElement(elementSelector).getAccessibleName();
            File screenshotSrc = driver.findElement(elementSelector).getScreenshotAs(OutputType.FILE);
            //save the screenshot to desired location
            File screenshotFile = new File( SCREENSHOTS_PATH+ariaName+ "-" + TimeManager.getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc,screenshotFile);

            //TODO : Attach the screenshot to Allure report
            LogsManager.info("Screenshot captured");
    }catch (Exception e) {
            LogsManager.error("Failed to capture screenshot: " + e.getMessage());

        }
    }
}
