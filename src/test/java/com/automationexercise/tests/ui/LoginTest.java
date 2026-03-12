package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagmentAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
@Epic("Automation Exercise")
@Feature("UI User Management")
@Story(" User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Monmon")
@UITest

public class LoginTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimestamp();
    //test cases for login functionality can be added here
    @Description("Verify that a user can successfully login with valid credentials")
    @Test
    public void validLoginTC()
    {
        new UserManagmentAPI().createRegisterUserAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email") + timestamp + "@gmail.com",
                testData.getJsonData("password"),
                testData.getJsonData("firstName"),
                testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBarComponent
                .verifyUserLoggedIn(testData.getJsonData("name"));
        new UserManagmentAPI().deleteUserAccount(testData.getJsonData("email") + timestamp + "@gmail.com"
                        , testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }

    @Description("Verify that a user cannot login with invalid Email")
    @Test
    public void inValidLoginUsingInvalidEmailTC()
    {
        new UserManagmentAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") +  "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .verifyLoginErrorMessage(testData.getJsonData("messages.error"));
        new UserManagmentAPI().deleteUserAccount(testData.getJsonData("email") + timestamp + "@gmail.com"
                        , testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }

    @Description("Verify that a user cannot login with invalid password")
    @Test
    public void inValidLoginUsingInvalidPasswordTC()
    {
        new UserManagmentAPI().createRegisterUserAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password")+ timestamp)
                .clickLoginButton()
                .verifyLoginErrorMessage(testData.getJsonData("messages.error"));
        new UserManagmentAPI().deleteUserAccount(testData.getJsonData("email") + timestamp + "@gmail.com"
                        , testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }




   //configurations
    @BeforeClass
    public void precondition(){

        testData = new JsonReader("login-data");
    }
    @BeforeMethod
    public void setUp() {
        // Navigate to the signup/login page before each test
        driver= new GUIDriver();
        new NavigationBarComponent(driver).navigate();
        driver.browser().closeExtensionTab();

    }
    @AfterMethod
    public void tearDown() {

        driver.quitDriver();
    }

}
