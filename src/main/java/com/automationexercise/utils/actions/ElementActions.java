package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ElementActions {
    private static final Logger log = LoggerFactory.getLogger(ElementActions.class);
    private final WebDriver driver;
    private WaitManager waitManager;
    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }
    // actions can be added here
    //Clicking
    public ElementActions click(By locator) {
        waitManager.fluentWait().until(d->
        {
            try{
                WebElement element=d.findElement(locator);
                scrollToElementUsingJS(locator);
                Point intialLocation = element.getLocation();
                LogsManager.info("Element "+ locator + " is located at: " + intialLocation);
                Point finalLocation = element.getLocation();
                LogsManager.info("Element "+ locator + " is located at: " + finalLocation);
                if(!intialLocation.equals(finalLocation)){
                    return false; //element is still moving after scrolling, wait for it to stabilize
                }
                element.click();
                LogsManager.info("Clicked on element: "+ locator);
                return true;
            } catch (Exception e) {

              return false;
            }
        });
        return this;

    }

    //Typing
    public ElementActions type(By locator, String text) {
        waitManager.fluentWait().until(d->
        {
            try{
                WebElement element=driver.findElement(locator);
                scrollToElementUsingJS(locator);
                 element.clear();
                element.sendKeys(text);
                LogsManager.info("Typed text '"+ text + "' in element: "+ locator);
                return true;
            } catch (Exception e) {

                return false;
            }
        });
        return this;

    }
    //hovering over element
    public ElementActions hover(By locator) {
        waitManager.fluentWait().until(d->
        {
            try{
                WebElement element=d.findElement(locator);
                scrollToElementUsingJS(locator);
                 new Actions(d).moveToElement(element).perform();
                LogsManager.info("Hovered over element: "+ locator);
                return true;
            } catch (Exception e) {

                return false;
            }
        });
        return this;
    }
    //upload file
    public ElementActions uploadFile(By locator, String filePath) {
        String fileAbsolutePath = System.getProperty("user.dir")+ File.separator + filePath;
        waitManager.fluentWait().until(d->
        {
            try{
                WebElement element=driver.findElement(locator);
                scrollToElementUsingJS(locator);
                element.sendKeys(fileAbsolutePath);
                LogsManager.info("Uploaded file '"+ fileAbsolutePath + "' in element: "+ locator);
                return true;
            } catch (Exception e) {

                return false;
            }
        });
        return this;
    }
    //Getting Text
    public String getText(By locator) {
        return waitManager.fluentWait().until(d->
        {
            try{
                WebElement element=driver.findElement(locator);
                scrollToElementUsingJS(locator);
                String msg = element.getText();
                LogsManager.info("Got text '"+ msg + "' from element: "+ locator);
                return !msg.isEmpty() ? msg : null;
            } catch (Exception e) {
                return null;
            }
        });
    }
    //find element function
    public WebElement findElement(By locator) {
                return driver.findElement(locator);
    }
    // scrollIntoView function using javascript
    public void scrollToElementUsingJS(By locator) {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("""
                arguments[0].scrollIntoView({behviour: "auto",block:"center", inline:"center"});""", findElement(locator));
    }

    //select from dropdown
        public ElementActions selectFromDropdown(By locator, String value) {
            waitManager.fluentWait().until(d->
            {
                try{
                    WebElement dropdown = driver.findElement(locator);
                    scrollToElementUsingJS(locator);
                   Select select = new Select(dropdown);
                    select.selectByVisibleText(value);
                    LogsManager.info("Selected '" + value + "' from dropdown: " + locator);
                    return true;
                } catch (Exception e) {

                    return false;
                }
            });
            return this;
        }



}
