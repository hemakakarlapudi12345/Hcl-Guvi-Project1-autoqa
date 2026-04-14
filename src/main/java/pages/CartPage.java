package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators
    private By cartTitle = By.cssSelector("div#cart_info h2");
    private By proceedToCheckoutButton = By.cssSelector("a.btn.btn-default.check_out");
    private By productTable = By.cssSelector("table#cart_info_table");
    private By productName = By.cssSelector("table#cart_info_table td.cart_description h4 a");
    private By productPrice = By.cssSelector("table#cart_info_table td.cart_price p");
    private By productQuantity = By.cssSelector("table#cart_info_table td.cart_quantity button");
    private By productTotal = By.cssSelector("table#cart_info_table td.cart_total p");
    
    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public boolean isCartPageDisplayed() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(cartTitle));
            return element.isDisplayed() && element.getText().contains("Shopping Cart");
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clickProceedToCheckout() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton));
        element.click();
    }
    
    public boolean isProductInCart(String expectedProductName) {
        try {
            WebElement productTableElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productTable));
            return productTableElement.getText().contains(expectedProductName);
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getProductName() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
            return element.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getProductPrice() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
            return element.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
