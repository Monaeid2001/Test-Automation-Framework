package com.automationexercise.drivers;

import com.automationexercise.utils.actions.AlertActions;
import com.automationexercise.utils.actions.BrowserActions;
import com.automationexercise.utils.actions.ElementActions;
import com.automationexercise.utils.actions.FrameActions;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.validations.Validation;
import com.automationexercise.validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {
    private  final String browser = PropertyReader.getProperty("browserType");
    private  ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    public GUIDriver(){
        LogsManager.info("Initializing GUIDriver with browser: " + browser);
        Broswer broswer = Broswer.valueOf(browser.toUpperCase());
        LogsManager.info("Selected browser is: "+browser);
        AbstractDriver driverFactory = broswer.getDriverFactory();
        WebDriver driver= ThreadGuard.protect(driverFactory.createDriver());
        driverThreadLocal.set(driver);
    }
    public ElementActions element(){
        return new ElementActions(get());
    }
    public BrowserActions browser(){
        return new BrowserActions(get());
    }
    public FrameActions frame(){
        return new FrameActions(get());
    }
    public AlertActions alert(){
        return new AlertActions(get());
    }
    //soft assertions
    public Validation validation(){
        return new Validation(get());
    }
    //hard assertions
    public Verification verification(){
        return new Verification(get());
    }


//get driver
public  WebDriver get(){
    return driverThreadLocal.get();
}
public  void  quitDriver(){
    driverThreadLocal.get().quit();
}
}
