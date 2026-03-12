package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductsPage {
    private final GUIDriver driver;
    public NavigationBarComponent navigationBarComponent;

    public ProductsPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBarComponent = new NavigationBarComponent(driver);
    }

    //variables
    private String productPage = "/products";

    //locators
    private final By searchField = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By itemAddedLabel = By.cssSelector(".modal-body > p");
    private final By viewCartButton = By.cssSelector("p > [href=\"/view_cart\"]");
    private final By continueShoppingButton = By.cssSelector(".modal-footer >button");

    //dynamic locator
    private By productName(String productName) {
        return By.xpath("//div[@class='features_items']//div[@class='overlay-content']/p[.='" + productName + "']");
    }

    private By productPrice(String productName) {
        return By.xpath("//div[@class='features_items']//div[@class='overlay-content']/p[.='" + productName + "']//preceding-sibling::h2");
    }

    private By hoverOnProduct(String productName) {
        return By.xpath("//div[@class='features_items']//div[@class='features_items'] //div[@class='productinfo text-center'] /p[.='" + productName + "']");
    }


    private By addToCartButton(String productName) {
        return By.xpath("//div[@class='features_items']//div[@class='overlay-content'] /p[.='" + productName + "'] //following-sibling::a");
    }

    private By viewProduct(String productName) {
        return By.xpath("//p[.='" + productName + "'] //following::div[@class='choose'][1]");
    }

    //actions
    @Step("Navigate to the Products page")
    public ProductsPage navigate() {
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb") + productPage);
        return this;
    }

    @Step("Search for product: {productName}")
    public ProductsPage searchForProduct(String productName) {
        driver.element().type(searchField, productName)
                .click(searchButton);
        return this;
    }

    @Step("Add product to cart: {productName}")
    public ProductsPage addProductToCart(String productName) {
        driver.element().hover(hoverOnProduct(productName))
                .click(addToCartButton(productName));
        return this;
    }

    @Step("View product details: {productName}")
    public ProductDetailsPage viewProductDetails(String productName) {
        driver.element().click(viewProduct(productName));
        return new ProductDetailsPage(driver);
    }

    @Step("Click on the View Cart button")
    public CartPage clickViewCartButton() {
        driver.element().click(viewCartButton);
        return new CartPage(driver);
    }

    @Step("Click on the Continue Shopping button")
    public ProductsPage clickContinueShoppingButton() {
        driver.element().click(continueShoppingButton);
        return this;
    }

    //validations
    @Step("Validate product details for product: {productName} and price: {productPrice}")
    public ProductsPage validateProductDetails(String productName, String productPrice) {
        String actualProductName = driver.element().hover(productName(productName)).getText(productName(productName));
        String actualProductPrice = driver.element().hover(productName(productName)).getText(productPrice(productName));
        driver.validation().Equals(actualProductName, productName, "Product name does not match");
        driver.validation().Equals(actualProductPrice, productPrice, "Product price does not match");
        return this;
    }

    @Step("Validate item added to cart message: {expectedMessage}")
    public ProductsPage validateItemAddedMessage(String expectedMessage) {
        String actualMessage = driver.element().getText(itemAddedLabel);
        LogsManager.info("Actual item added message: " + actualMessage);
        driver.verification().Equals(actualMessage, expectedMessage, "Item added message does not match. Expected: " + expectedMessage + " but found: " + actualMessage);
        return this;
    }
}
