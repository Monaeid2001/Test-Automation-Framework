package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;

public class AlertActions {
    private final WebDriver driver;
    private final WaitManager waitManager;
    public AlertActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    /**
     * Accepts the currently displayed alert dialog.
     */
    public void acceptAlert() {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().alert().accept();
                return true;
            } catch (Exception e) {
                //logs
                LogsManager.error("failed to accept alert",e.getMessage());
                return false;
            }
        });
    }
    /**
     * Dismisses the currently displayed alert dialog.
     */
    public void dismissAlert() {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().alert().dismiss();
                return true;
            } catch (Exception e) {
                LogsManager.error("failed to dismiss alert",e.getMessage());
                return false;
            }
        });
    }
    /**
     * Retrieves the text from the currently displayed alert dialog.
     *
     * @return The text of the alert dialog.
     */
    public String getAlertText() {
        return waitManager.fluentWait().until(d -> {
            try {
                String text = d.switchTo().alert().getText();
                return !text.isEmpty() ? text : null;

            } catch (Exception e) {
                LogsManager.error("failed to get alert ", e.getMessage());
                return null;
            }
        });
    }
    /**
     * Sends text to the currently displayed alert dialog.
     */
    public void sendTextToAlert(String text) {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().alert().sendKeys(text);
                return true;
            } catch (Exception e) {
                LogsManager.error("failed to send text to alert", e.getMessage());
                return false;
            }
        });
    }

}
