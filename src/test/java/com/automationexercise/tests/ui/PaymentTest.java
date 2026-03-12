package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagmentAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.CheckoutPage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("Payment Management")
@Story("Payment Process")
@Severity(SeverityLevel.CRITICAL)
@Owner("Monmon")
@UITest

public class PaymentTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimestamp();

    @Test
    public void registerNewAccount(){
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

    }
    @Test(dependsOnMethods = "registerNewAccount")
    public void loginToAccount(){
        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBarComponent
                .verifyUserLoggedIn(testData.getJsonData("name"));

    }
    @Test(dependsOnMethods = {"loginToAccount","registerNewAccount"})
    public void addProductToCart(){
        new ProductsPage(driver).navigate()
                .addProductToCart(testData.getJsonData("product.name"))
                .validateItemAddedMessage(
                        testData.getJsonData("messages.cartAdded")
                ).clickViewCartButton()
                .verifyProductInCart(
                        testData.getJsonData("product.name"),
                        testData.getJsonData("product.price"),
                        testData.getJsonData("product.quantity"),
                        testData.getJsonData("product.total")
                );

    }
    @Test(dependsOnMethods = {"addProductToCart","loginToAccount","registerNewAccount"})
    public void proceedToCheckout(){
        new CartPage(driver).proceedToCheckout()
                .verifyDeliveryAddress(
                        testData.getJsonData("titleFemale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyBillingAddress(
                        testData.getJsonData("titleFemale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber")
                );

    }
    @Test(dependsOnMethods = {"proceedToCheckout","addProductToCart","loginToAccount","registerNewAccount"})
    public void paymentTest(){
          new CheckoutPage(driver)
                  .placeOrder()
                  .fillPaymentDetails(
                            testData.getJsonData("card.name"),
                            testData.getJsonData("card.number"),
                            testData.getJsonData("card.cvc"),
                            testData.getJsonData("card.expiryMonth"),
                            testData.getJsonData("card.expiryYear")
                  ).verifyOrderSuccessMessage(testData.getJsonData("messages.paymentSuccess"));
    }

    @Test(dependsOnMethods = {"paymentTest","proceedToCheckout","addProductToCart","loginToAccount","registerNewAccount"})
    public void deleteUserAccount(){
        new UserManagmentAPI().deleteUserAccount(testData.getJsonData("email") + timestamp + "@gmail.com", testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }










    //configurations
    @BeforeClass
    public void setUp(){

        testData = new JsonReader("checkout-data");
        driver= new GUIDriver();
        new NavigationBarComponent(driver).navigate();
        driver.browser().closeExtensionTab();
    }

    @AfterClass
    public void tearDown() {

        driver.quitDriver();
    }
}
