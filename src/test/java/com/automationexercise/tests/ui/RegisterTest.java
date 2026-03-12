package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagmentAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.pages.SignupPage;
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
@Story(" User Registeration")
@Severity(SeverityLevel.CRITICAL)
@Owner("Monmon")
@UITest
public class RegisterTest extends BaseTest {
    //Tests
    String timestamp = TimeManager.getSimpleTimestamp();
    @Description("Verify that a user can successfully register with valid data")
@Test
public void validSignupTC() {
    // Test steps for signup
    new SignupLoginPage(driver).navigate()
            .enterSignupName(testData.getJsonData("name"))
            .enterSignupEmail(testData.getJsonData("email")+timestamp+"@gmail.com")
            .clickSignupButton();
            new SignupPage(driver)
            .fillRegistrationForm(
                    testData.getJsonData("titleFemale"),
                    testData.getJsonData("password"),
                    testData.getJsonData("day"),
                    testData.getJsonData("month"),
                    testData.getJsonData("year"),
                    testData.getJsonData("firstName"),
                    testData.getJsonData("lastName"),
                    testData.getJsonData("company"),
                    testData.getJsonData("address1"),
                    testData.getJsonData("address2"),
                    testData.getJsonData("country"),
                    testData.getJsonData("state"),
                    testData.getJsonData("city"),
                    testData.getJsonData("zipcode"),
                    testData.getJsonData("mobileNumber")
            )
            .clickCreateAccount()
            .getAccountCreatedMessage();
    new UserManagmentAPI().deleteUserAccount(testData.getJsonData("email") + timestamp + "@gmail.com", testData.getJsonData("password"))
            .verifyUserDeletedSuccessfully();

}
 @Description("Verify user cann;t sign up with invalid data")
@Test
public void verifyErrorMessageWhenAccountCreatedBefore(){
    //precondition > create a user account
    new UserManagmentAPI().createRegisterUserAccount(
            testData.getJsonData("name"),
            testData.getJsonData("email")+ timestamp+"@gmail.com",
            testData.getJsonData("password"),
            testData.getJsonData("titleFemale"),
            testData.getJsonData("day"),
            testData.getJsonData("month"),
            testData.getJsonData("year"),
            testData.getJsonData("firstName"),
            testData.getJsonData("lastName"),
            testData.getJsonData("company"),
            testData.getJsonData("address1"),
            testData.getJsonData("address2"),
            testData.getJsonData("country"),
            testData.getJsonData("state"),
            testData.getJsonData("city"),
            testData.getJsonData("zipcode"),
            testData.getJsonData("mobileNumber")
    )
            .verifyUserCreatedSuccessfully();
        new SignupLoginPage(driver).navigate()
            .enterSignupName(testData.getJsonData("name"))
            .enterSignupEmail(testData.getJsonData("email")+ timestamp+"@gmail.com")
            .clickSignupButton()
                .verifySignupErrorMessage(testData.getJsonData("messages.error"));
    new UserManagmentAPI().deleteUserAccount(testData.getJsonData("email") + timestamp + "@gmail.com", testData.getJsonData("password"))
            .verifyUserDeletedSuccessfully();


}
@Test
public void invalidSignupTC() {

}


    // Configurations
    @BeforeClass
    public void precondition(){

        testData = new JsonReader("register-data");
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
