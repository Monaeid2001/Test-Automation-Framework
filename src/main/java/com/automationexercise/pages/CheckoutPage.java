package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutPage {
    private final GUIDriver driver;
    private final String checkoutEndpoint = "/checkout";

    public CheckoutPage(GUIDriver driver) {
        this.driver = driver;
    }
    //vars

    //locators
    private final By placeOrderButton = By.xpath("//a[.='Place Order']");
    //Delivery address locators
    private final By deliveryName = By.xpath("//ul[@id='address_delivery']/li[@class='address_firstname address_lastname']");
    private final By deliveryCompany = By.xpath("//ul[@id='address_delivery']/li[@class='address_address1 address_address2'][1]");
    private final By deliveryAdress1 = By.xpath("//ul[@id='address_delivery']/li[@class='address_address1 address_address2'][2]");
    private final By deliveryAdress2 = By.xpath("//ul[@id='address_delivery']/li[@class='address_address1 address_address2'][3]");
    private final By deliveryCityStateZip = By.xpath("//ul[@id='address_delivery']/li[@class='address_city address_state_name address_postcode']");
    private final By deliveryCountry = By.xpath("//ul[@id='address_delivery']/li[@class='address_country_name']");
    private final By deliveryMobile = By.xpath("//ul[@id='address_delivery']/li[@class='address_phone']");

    //billing address locators
    private final By billingName = By.xpath("//ul[@id='address_invoice']/li[@class='address_firstname address_lastname']");
    private final By billingCompany = By.xpath("//ul[@id='address_invoice']/li[@class='address_address1 address_address2'][1]");
    private final By billingAdress1 = By.xpath("//ul[@id='address_invoice']/li[@class='address_address1 address_address2'][2]");
    private final By billingAdress2 = By.xpath("//ul[@id='address_invoice']/li[@class='address_address1 address_address2'][3]");
    private final By billingCityStateZip = By.xpath("//ul[@id='address_invoice']/li[@class='address_city address_state_name address_postcode']");
    private final By billingCountry = By.xpath("//ul[@id='address_invoice']/li[@class='address_country_name']");
    private final By billingMobile = By.xpath("//ul[@id='address_invoice']/li[@class='address_phone']");


    //actions
    @Step("Navigate to the Checkout page")
    public CheckoutPage navigate(){
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb")+checkoutEndpoint);
        return this;
    }
    @Step("Place the order")
        public PaymentPage placeOrder(){
            driver.element().click(placeOrderButton);
            return new PaymentPage(driver);
        }

    //validations
    @Step("Verify Delivery Address")
    public CheckoutPage verifyDeliveryAddress(String title,String fName, String lName, String expectedCompany, String expectedAddress1, String expectedAddress2
            , String city ,String state, String zip, String expectedCountry, String expectedMobile){
        driver.validation().Equals(driver.element().getText(deliveryName),(title +". "+ fName+ " "+ lName),"Delivery name does not match");
        driver.validation().Equals(driver.element().getText(deliveryCompany),expectedCompany,"Delivery company does not match");
        driver.validation().Equals(driver.element().getText(deliveryAdress1),expectedAddress1,"Delivery address1 does not match");
        driver.validation().Equals(driver.element().getText(deliveryAdress2),expectedAddress2,"Delivery address2 does not match");
        driver.validation().Equals(driver.element().getText(deliveryCityStateZip),(zip+ " "+city+ " "+ state),"Delivery city/state/zip does not match");
        driver.validation().Equals(driver.element().getText(deliveryCountry),expectedCountry,"Delivery country does not match");
        driver.validation().Equals(driver.element().getText(deliveryMobile),expectedMobile,"Delivery mobile does not match");
        return this;
    }

    @Step("Verify Billing Address")
    public CheckoutPage verifyBillingAddress(String title,String fName, String lName, String expectedCompany, String expectedAddress1, String expectedAddress2
            , String city ,String state, String zip, String expectedCountry, String expectedMobile){
        driver.validation().Equals(driver.element().getText(billingName),(title +". "+ fName+ " "+ lName),"Delivery name does not match");
        driver.validation().Equals(driver.element().getText(billingCompany),expectedCompany,"Billing company does not match");
        driver.validation().Equals(driver.element().getText(billingAdress1),expectedAddress1,"Billing address1 does not match");
        driver.validation().Equals(driver.element().getText(billingAdress2),expectedAddress2,"Billing address2 does not match");
        driver.validation().Equals(driver.element().getText(billingCityStateZip),(zip+ " "+city+ " "+ state),"Billing city/state/zip does not match");
        driver.validation().Equals(driver.element().getText(billingCountry),expectedCountry,"Billing country does not match");
        driver.validation().Equals(driver.element().getText(billingMobile),expectedMobile,"Billing mobile does not match");
        return this;
    }

}
