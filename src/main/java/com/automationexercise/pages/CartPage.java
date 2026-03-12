package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    private GUIDriver driver;
    public CartPage(GUIDriver driver) {
        this.driver = driver;
    }
    //vars
    private final String cartEndpoint = "/view_cart";
    
    //locators
    private final By proceedToCheckoutButton = By.xpath("//a[.='Proceed To Checkout']");
    //dynamic locators
    private By productName(String productName) {
        return By.xpath("(//h4/a[.='" + productName + "'])[1]");
    }
    private By productPrice(String productName) {
        return By.xpath("(//h4/a[.='" + productName + "']//following::td[@class='cart_price']/p)[1]");
    }
    private By productQuantity(String productName) {
        return By.xpath("(//h4/a[.='" + productName + "']//following::td[@class='cart_quantity']/button)[1]");
    }
    private By productTotal(String productName) {
        return By.xpath("(//h4/a[.='" + productName + "']//following::td[@class='cart_total']/p)[1]");
    }
    private By removeProduct(String productName) {
        return By.xpath("(//h4/a[.='" + productName + "']//following::td[@class='cart_delete']/a)[1]");
    }

    //actions
    @Step("Navigate to the Cart page")
    public CartPage navigate(){
        driver.browser().navigateToUrl(PropertyReader.getProperty("baseUrlWeb")+cartEndpoint);
        return this;
    }
        @Step("Proceed to checkout")
     public CheckoutPage proceedToCheckout(){
         driver.element().click(proceedToCheckoutButton);
         return new CheckoutPage(driver);
     }
        @Step("Remove product: {productName} from cart")
     public CartPage removeProductFromCart(String productName){
         driver.element().click(removeProduct(productName));
         return this;
     }
    //validations
        @Step("Verify product details in cart for product: {productName}")
    public CartPage verifyProductInCart(String productName, String expectedPrice, String expectedQuantity, String expectedTotal){
        String actualProductName = driver.element().getText(productName(productName));
        String actualPrice = driver.element().getText(productPrice(productName));
        String actualQuantity = driver.element().getText(productQuantity(productName));
        String actualTotal = driver.element().getText(productTotal(productName));
        driver.validation().Equals(actualProductName,productName,"Product name in cart does not match")
        .Equals(actualPrice,expectedPrice,"Product price in cart does not match")
       .Equals(actualQuantity,expectedQuantity,"Product quantity in cart does not match")
            .Equals(actualTotal,expectedTotal,"Product total in cart does not match");
        return this;
    }

}
