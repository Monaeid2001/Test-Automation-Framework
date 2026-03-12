package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupPage {
    private final GUIDriver driver;
    public SignupPage(GUIDriver driver) {
        this.driver = driver;
    }
    //locators
    private final By name = By.cssSelector("[data-qa=\"name\"]");
    private final By email = By.cssSelector("[data-qa=\"email\"]");
    private final By password = By.cssSelector("[data-qa=\"password\"]");
    private final By day = By.id("days");
    private final By month = By.id("months");
    private final By year = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By offersCheckbox = By.id("optin");
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By company = By.id("company");
    private final By address1 = By.id("address1");
    private final By address2 = By.id("address2");
    private final By country = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipcode = By.id("zipcode");
    private final By mobileNumber = By.id("mobile_number");
    private final By createAccountButton = By.cssSelector("[data-qa=\"create-account\"]");
    private final By accountCreatedMessage = By.tagName("p");
    private final By continueButton = By.cssSelector("[data-qa=\"continue-button\"]");


    //actions
    //dynamic mr and mrs locator
    @Step("Choose title {title}") //Mr - Mrs
    private SignupPage chooseTitle(String title){
        By titleLocator = By.xpath("//input[@value='"+title+"']");
        driver.element().click(titleLocator);
        return this;
    }
    @Step("fill  registeration form ")
    public SignupPage fillRegistrationForm(String title, String password, String day, String month, String year, String firstName, String lastName, String company, String address1, String address2, String country, String state, String city, String zipcode, String mobileNumber){
        chooseTitle(title);
        driver.element().type(this.password,password);
        driver.element().selectFromDropdown(this.day,day);
         driver.element().selectFromDropdown(   this.month,month);
         driver.element().selectFromDropdown( this.year,year);
        driver.element().click(newsletterCheckbox);
        driver.element().click(offersCheckbox);
        driver.element().type(this.firstName,firstName);
        driver.element().type(this.lastName,lastName);
        driver.element().type(this.company,company);
        driver.element().type(this.address1,address1);
        driver.element().type(this.address2,address2);
        driver.element().selectFromDropdown(this.country,country);
        driver.element().type(this.state,state);
        driver.element().type(this.city,city);
        driver.element().type(this.zipcode,zipcode);
        driver.element().type(this.mobileNumber,mobileNumber);

        return this;
    }
    @Step("click on create account button")
    public SignupPage clickCreateAccount(){
        driver.element().click(createAccountButton);
        return this;
    }
    @Step("click on continue button")
    public NavigationBarComponent clickContinue(){
        driver.element().click(continueButton);
        return new NavigationBarComponent(driver);
    }


    //validations
  @Step("Verify account created successfully")
    public SignupPage getAccountCreatedMessage(){
        driver.verification().isElementVisible(accountCreatedMessage);
        return this;
    }

}
