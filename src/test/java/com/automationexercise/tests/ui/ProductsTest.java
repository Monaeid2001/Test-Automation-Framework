package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.ProductsPage;
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
@Feature("UI Product Management")
@Story(" Products Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Monmon")
@UITest

public class ProductsTest extends BaseTest {


    @Test
    @Description("Search for a product and validate its details")
    public void searchForProductWithoutLogin() {
        new ProductsPage(driver).navigate()
                .searchForProduct(testData.getJsonData("searchedProduct.name"))
                .validateProductDetails(
                        testData.getJsonData("searchedProduct.name"),
                        testData.getJsonData("searchedProduct.price")
                );


    }

    @Test
    @Description("Add a product to the cart without logging in")
    public void addProductToCartWithoutLogin() {
        new ProductsPage(driver).navigate()
                .addProductToCart(testData.getJsonData("product1.name"))
                .validateItemAddedMessage(
                        testData.getJsonData("messages.cartAdded")
                );
    }


        //configurations
        @BeforeClass
        public void precondition () {

            testData = new JsonReader("products-data");
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


