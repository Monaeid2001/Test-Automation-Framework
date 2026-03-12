package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI Product Details Management")
@Story("Product Details")
@Severity(SeverityLevel.CRITICAL)
@Owner("Monmon")
@UITest

public class ProductDetailsTest extends BaseTest {
    @Test
    public void verifyProductDetailsTCWithoutLogin(){
        new ProductsPage(driver).navigate()
                .viewProductDetails(testData.getJsonData("product.name"))
                .verifyProductDetails(
                        testData.getJsonData("product.name"),
                        testData.getJsonData("product.price")
                );

    }
    @Test
    public void verifyReviewMessageTCWithoutLogin(){
        new ProductsPage(driver).navigate()
                .viewProductDetails(testData.getJsonData("product.name"))
                .addReview(
                        testData.getJsonData("review.name"),
                        testData.getJsonData("review.email"),
                        testData.getJsonData("review.review")
                ).verifyReviewSubmission(
                        testData.getJsonData("messages.review")
                );

    }




    //configurations
    @BeforeClass
    public void precondition () {

        testData = new JsonReader("product-details-data");
    }
    @BeforeMethod
    public void setUp () {
        // Navigate to the signup/login page before each test
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
        driver.browser().closeExtensionTab();

    }
    @AfterMethod
    public void tearDown () {

        driver.quitDriver();
    }
}
