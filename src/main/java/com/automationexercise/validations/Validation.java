package com.automationexercise.validations;
//soft assertion

import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

public class Validation extends BaseAssertion {
    private static SoftAssert softAssert = new SoftAssert();
    private static boolean used = false;
    public Validation() {
        super();
    }
    public Validation(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        used = true;
       softAssert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        used = true;
    softAssert.assertFalse(condition, message);
    }

    @Override
    protected void assertEquals(String actual, String expected, String message) {
        used = true;
     softAssert.assertEquals(actual, expected, message);
    }
    //assert all
    public static void assertAll(ITestResult result){
        if(!used){
            LogsManager.warn("No assertions were made to assert.");
            return;
        }
        try{
        softAssert.assertAll();
    }catch (AssertionError e) {
            LogsManager.error("Assertion failed: ", e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        }finally {
            softAssert = new SoftAssert(); // Reset the soft assert instance for future use
        }
    }
}
