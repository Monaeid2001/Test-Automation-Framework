package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {
    public NavigationBarComponent navigationBarComponent;
    private  GUIDriver driver;
    private final String signupLoginEndpoint = "/login";
    public SignupLoginPage(GUIDriver driver) {
        this.driver = driver;
            navigationBarComponent = new NavigationBarComponent(driver);
    }

    //locators
    private final By loginEmail = By.cssSelector("[data-qa=\"login-email\"]");
    private final By loginPassword = By.cssSelector("[data-qa=\"login-password\"]");
    private final By loginButton = By.cssSelector("[data-qa=\"login-button\"]");
    private final By signupName = By.cssSelector("[data-qa=\"signup-name\"]");
    private final By signupEmail = By.cssSelector("[data-qa=\"signup-email\"]");
    private final By signupButton = By.cssSelector("[data-qa=\"signup-button\"]");
    private final By signupLabel = By.cssSelector(".signup-form>h2");
    private final By loginErrorMessage = By.cssSelector(".login-form p");
    private final By signupErrorMessage = By.cssSelector(".signup-form p");

    //actions
    @Step("Navigate to the Signup/Login page")
    public SignupLoginPage navigate(){
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb")+signupLoginEndpoint);
        return this;
    }
    @Step("Enter login email: {email}")
    public SignupLoginPage enterLoginEmail(String email){
        driver.element().type(loginEmail,email);
        return this;
    }
    @Step("Enter login password: {password}")
    public SignupLoginPage enterLoginPassword(String password){
        driver.element().type(loginPassword,password);
        return this;
    }
    @Step("Click on the Login button")
    public SignupLoginPage clickLoginButton(){
        driver.element().click(loginButton);
        return this;
    }
    @Step("Enter signup name: {name}")
    public SignupLoginPage enterSignupName(String name){
        driver.element().type(signupName,name);
        return this;
    }
    @Step("Enter signup email: {email}")
    public SignupLoginPage enterSignupEmail(String email){
        driver.element().type(signupEmail,email);
        return this;
    }
    @Step("Click on the Signup button")
    public SignupLoginPage clickSignupButton(){
        driver.element().click(signupButton);
        return new SignupLoginPage(driver);
    }


    //validations
    @Step("Verify that the Signup/Login page is displayed")
    public SignupLoginPage verifySignupLoginPageDisplayed(){
        driver.verification().isElementVisible(signupLabel);
        return this;
    }
    @Step("Verify login error message: {expectedMessage}")
    public SignupLoginPage verifyLoginErrorMessage(String expectedMessage) {
        String actualMessage = driver.element().getText(loginErrorMessage);
        LogsManager.info("Actual login error message: " + actualMessage);
        driver.verification().Equals(actualMessage, expectedMessage, "Login error message does not match. Expected: " + expectedMessage + " but found: " + actualMessage);
        return this;
    }
    @Step("Verify signup error message: {expectedMessage}")
    public SignupLoginPage verifySignupErrorMessage(String expectedMessage) {
        String actualMessage = driver.element().getText(signupErrorMessage);
        LogsManager.info("Actual signup error message: " + actualMessage);
        driver.verification().Equals(actualMessage, expectedMessage, "Signup error message does not match. Expected: " + expectedMessage + " but found: " + actualMessage);
        return this;
    }

}
