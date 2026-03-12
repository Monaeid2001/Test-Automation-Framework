package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentPage {
    private final GUIDriver driver;
    public PaymentPage(GUIDriver driver) {
        this.driver = driver;
    }
    //vars
    private final String paymentEndpoint = "/payment";
    //locators
    private final By nameOnCard = By.name("name_on_card");
    private final By cardNumber = By.name("card_number");
    private final By cvc = By.name("cvc");
    private final By expiryMonth = By.name("expiry_month");
    private final By expiryYear = By.name("expiry_year");
    private final By payAndConfirmOrderButton = By.id("submit");
    private final By successMessage = By.cssSelector("h2>b");
    private final By downloadInvoiceButton = By.xpath("//a[.='Download Invoice']");
    //actions
    @Step("fill card info")
    public PaymentPage fillPaymentDetails(String nameOnCard, String cardNumber, String cvc, String expiryMonth, String expiryYear){
        driver.element().type(this.nameOnCard,nameOnCard)
                .type(this.cardNumber,cardNumber)
                .type(this.cvc,cvc)
                .type(this.expiryMonth,expiryMonth)
                .type(this.expiryYear,expiryYear)
                .click(payAndConfirmOrderButton);

        return this;
    }
    @Step("click download invoice button")
    public PaymentPage clickDownloadInvoice(){
        driver.element().click(downloadInvoiceButton);
        return this;
    }
    //validations
    @Step("Verify order success message")
    public PaymentPage verifyOrderSuccessMessage(String expectedMessage){
        String actualMessage = driver.element().getText(successMessage);
        LogsManager.info("Verifying order success message: actual message - " + actualMessage);
        driver.verification().Equals(actualMessage,expectedMessage,"Order success message does not match");
        return this;
    }

    public PaymentPage verifyInvoiceDownloaded(String invoiceName) {
        driver.verification().assertFileExists(invoiceName,"Invoice file was not downloaded");
        return this;
    }
}
