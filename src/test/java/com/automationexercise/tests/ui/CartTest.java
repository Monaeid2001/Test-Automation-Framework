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

@Epic("Cart Management")
@Feature("UI Cart Details")
@Story(" Cart Details")
@Severity(SeverityLevel.CRITICAL)
@Owner("Monmon")
@UITest
public class CartTest extends BaseTest {

 @Test
 public void verifyProductDetailsOnCartWithoutLogin(){
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







    //configurations
    @BeforeClass
    public void precondition () {

        testData = new JsonReader("cart-data");
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
