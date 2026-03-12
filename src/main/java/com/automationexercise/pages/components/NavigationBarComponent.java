package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.*;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {
    private final GUIDriver driver;

    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
    }
    //locators
   private final By homeButton = By.xpath("//a[.=' Home']");
    private final By productsButton = By.cssSelector("a[href='/products']");
    private final By cartButton = By.xpath("//a[.=' Cart']");
    private final By signUpLoginButton = By.xpath("//a[.=' Signup / Login']");
    private final By logoutButton = By.xpath("//a[.=' Logout']");
    private final By testCasesButton = By.xpath("//a[.=' Test Cases']");
    private final By apiTestingButton = By.xpath("//a[.=' API Testing']");
    private final By contactUsButton = By.xpath("//a[.=' Contact us']");
    private final By videoTutorialsButton = By.xpath("//a[.=' Video Tutorials']");
    private final By deleteAccountButton = By.xpath("//a[.=' Delete Account']");
    private final By homePageLabel = By.cssSelector("h1 > span");
    private final By userLabel = By.tagName("b");


    //actions
    @Step("Navigate to the Home page")
    public NavigationBarComponent navigate(){
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }
    @Step("Click on the Home button")
    public NavigationBarComponent clickHomeButton(){
        driver.element().click(homeButton);
        return this;
    }
    @Step("Click on the Products button")
    public ProductsPage clickProductsButton(){
        driver.element().click(productsButton);
        return new ProductsPage(driver);
    }
    @Step("Click on the Cart button")
    public CartPage clickCartButton(){
        driver.element().click(cartButton);
        return new CartPage(driver);
    }
    @Step("Click on the Sign Up/Login button")
    public SignupLoginPage clickSignUpLoginButton(){
        driver.element().click(signUpLoginButton);
        return new SignupLoginPage(driver);
    }
    @Step("Click on the Logout button")
    public LogoutPage clickLogoutButton(){
        driver.element().click(logoutButton);
        return new LogoutPage(driver);
    }
    public TestCasesPage clickTestCasesButton(){
        driver.element().click(testCasesButton);
        return new TestCasesPage(driver);
    }

    public ContactUsPage clickContactUsButton(){
        driver.element().click(contactUsButton);
        return new ContactUsPage(driver);
    }

    public DeleteAccountPage clickDeleteAccountButton(){
        driver.element().click(deleteAccountButton);
        return new DeleteAccountPage(driver);
    }



    //validations
    @Step("Verify that the Home page is visible successfully")
    public NavigationBarComponent verifyHomePageVisible(){
        driver.verification().isElementVisible(homePageLabel);
        return this;
    }
    @Step("Verify that the user is logged in as {expectedName}")
    public NavigationBarComponent verifyUserLoggedIn(String expectedName){
        String actualName=driver.element().getText(userLabel);
        LogsManager.info("Verifying user label: "+actualName);
        driver.verification().Equals(actualName,expectedName,"Logged in user does not match. Expected: "+expectedName+" but found: "+actualName);
        return this;
    }


}
