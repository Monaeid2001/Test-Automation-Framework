package com.automationexercise.utils;

import com.automationexercise.utils.dataReader.PropertyReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;

public class WaitManager {
    WebDriver driver;
    public WaitManager(WebDriver driver){
        this.driver=driver;
    }
    public FluentWait<WebDriver> fluentWait(){
   return  new FluentWait<>(driver)
           .withTimeout(Duration.ofSeconds(Long.parseLong(PropertyReader.getProperty("DEFAULT_WAIT"))))
           .pollingEvery(Duration.ofMillis(100))
           .ignoreAll(getExceptions());
    }
    private ArrayList<Class<? extends Exception>> getExceptions(){
        ArrayList<Class<? extends Exception>> exeptions = new ArrayList<>();
        // Add exceptions to ignore during wait
        exeptions.add(NoSuchElementException.class);
        exeptions.add(StaleElementReferenceException.class);
        exeptions.add(ElementNotInteractableException.class);
        exeptions.add(ElementClickInterceptedException.class);
        return exeptions;
    }
}
