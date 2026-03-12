package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetailsPage {
    private final GUIDriver driver;
    public ProductDetailsPage(GUIDriver driver) {
        this.driver = driver;
    }
    //vars
        private final String productDetailsEndpoint = "/product_details/2";

    //locators
    private final By productName = By.cssSelector(".product-information h2");
    private final By productPrice = By.cssSelector(".product-information span span");
    private final By name = By.id("name");
    private final By email = By.id("email");
    private final By reviewTextArea = By.id("review");
    private final By submitButton = By.id("button-review");
    private final By reviewMsg = By.cssSelector("#review-section span");

    //actions
    public ProductDetailsPage navigate(){
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb")+productDetailsEndpoint);
        return this;
    }
    @Step("Add a review with name: {name}, email: {email} and review: {review}")
    public ProductDetailsPage addReview(String name, String email, String review){
        driver.element().type(this.name,name)
                .type(this.email,email)
                .type(reviewTextArea,review)
                .click(submitButton);
        return this;
    }

    //validations
    @Step("Verify product details")
    public ProductDetailsPage verifyProductDetails(String expectedName, String expectedPrice){
        String actualName = driver.element().getText(productName);
        String actualPrice = driver.element().getText(productPrice);
        LogsManager.info("Verifying product details:,actual name - " + actualName, "actual price - " + actualPrice);
        driver.validation().Equals(actualName,expectedName,"Product name does not match");
        driver.validation().Equals(actualPrice,expectedPrice,"Product price does not match");
        return this;
    }
        @Step("Verify review submission message")
        public ProductDetailsPage verifyReviewSubmission(String expectedMessage){
           String actualMessage = driver.element().getText(reviewMsg);
            LogsManager.info("Verifying review submission message: actual message - " + actualMessage);
            driver.verification().Equals(actualMessage,expectedMessage,"Review submission message does not match");
            return this;
        }
}
