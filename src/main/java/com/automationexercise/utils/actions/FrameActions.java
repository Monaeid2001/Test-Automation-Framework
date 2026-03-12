package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrameActions {
    private final WebDriver driver;
    private final WaitManager waitManager;
    public FrameActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }
    /**
     * Switches to a frame using its name or ID.
     *
     * @param nameOrId The name or ID of the frame to switch to.
     */
    public void switchToFrameByNameOrId(String nameOrId) {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().frame(nameOrId);
                LogsManager.info("Switched to frame: " + nameOrId);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
    /**
     * Switches to a frame using its index.
     *
     * @param index The index of the frame to switch to.
     */
    public void switchToFrameByIndex(int index) {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().frame(index);
                LogsManager.info("Switched to frame at index: " + index);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
    /**
     * Switches to a frame using its WebElement.
     *
     * @param frameLocator The WebElement of the frame to switch to.
     */
    public void switchToFrameByElement(By frameLocator) {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().frame(d.findElement(frameLocator));
                LogsManager.info("Switched to frame: " + frameLocator);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
    /**
     * Switches back to the default content from a frame.
     */
    public void switchToDefaultContent() {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().defaultContent();
                LogsManager.info("Switched to default content");
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
}
