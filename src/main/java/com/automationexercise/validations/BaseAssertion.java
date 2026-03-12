package com.automationexercise.validations;

import com.automationexercise.FileUtils;
import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.actions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BaseAssertion {
    protected  WebDriver driver;
    protected  WaitManager waitManager;
    protected  ElementActions elementActions;
    protected BaseAssertion() {
    }
    protected BaseAssertion(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
        this.elementActions = new ElementActions(driver);
    }
   protected abstract void assertTrue(boolean condition, String message);
    protected abstract void assertFalse(boolean condition, String message);
    protected abstract void assertEquals(String actual, String expected, String message);
    public BaseAssertion Equals(String actual, String expected, String message){
        assertEquals(actual,expected,message);
        return this;
    }
    public void isElementVisible(By locator){
        boolean flag =waitManager.fluentWait().until(d->{
           try{
               d.findElement(locator).isDisplayed();
               return true;
           }catch (Exception e){
               return false;
           }
       });
         assertTrue(flag,"Element is not visible: "+locator);
    }
    //assert page url
    public void assertPageUrl(String expectedUrl){
        String actualUrl=driver.getCurrentUrl();
        assertEquals(actualUrl,expectedUrl,"Page URL does not match. Expected: "+expectedUrl+" but found: "+actualUrl);
    }
    //assert page title
    public void assertPageTitle(String expectedTitle){
        String actualTitle=driver.getTitle();
        assertEquals(actualTitle,expectedTitle,"Page title does not match. Expected: "+expectedTitle+" but found: "+actualTitle);
    }

    //verify that file exists

    // Verify that file exists
    public void assertFileExists(String fileName, String message) {
        boolean fileExists = FileUtils.isFileExist(fileName,3);
        assertTrue(FileUtils.isFileExists(fileName), message);
    }
}
