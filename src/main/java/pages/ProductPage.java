package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    
    // Locators
    private By productsLink = By.cssSelector("a[href='/products']");
    private By searchInput = By.cssSelector("input#search_product");
    private By searchButton = By.cssSelector("button#submit_search");
    private By productTitle = By.cssSelector("h2.title.text-center");
    private By firstProduct = By.cssSelector(".product-image-wrapper:first-child");
    private By addToCartButton = By.cssSelector(".product-image-wrapper:first-child .add-to-cart");
    private By viewCartButton = By.cssSelector("a[href='/view_cart']");
    private By continueShoppingButton = By.cssSelector("button.btn-success");
    private By cartModal = By.cssSelector(".modal-content");
    
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }
    
    public void clickProductsLink() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(productsLink));
        element.click();
    }
    
    public boolean isProductsPageDisplayed() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle));
            return element.isDisplayed() && element.getText().toLowerCase().contains("products");
        } catch (Exception e) {
            return false;
        }
    }
    
    public void searchProduct(String productName) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        searchBox.clear();
        searchBox.sendKeys(productName);
        
        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchBtn.click();
    }
    
    public void hoverAndClickAddToCartForFirstProduct() {
        // Wait for first product to be visible
        WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(firstProduct));
        
        // Hover over the product
        actions.moveToElement(product).perform();
        
        // Wait for add to cart button to be visible after hover
        WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        
        // Try regular click first, if fails use JavaScript
        try {
            addToCart.click();
        } catch (Exception e) {
            // Use JavaScript click as fallback
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCart);
        }
    }
    
    public void clickViewCart() {
        try {
            // Handle the modal that appears after adding to cart
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(cartModal));
            WebElement viewCartBtn = wait.until(ExpectedConditions.elementToBeClickable(viewCartButton));
            
            // Try regular click first, if fails use JavaScript
            try {
                viewCartBtn.click();
            } catch (Exception clickException) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCartBtn);
            }
        } catch (Exception e) {
            // If modal doesn't appear, try clicking view cart directly
            WebElement viewCartBtn = wait.until(ExpectedConditions.elementToBeClickable(viewCartButton));
            try {
                viewCartBtn.click();
            } catch (Exception clickException) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCartBtn);
            }
        }
    }
    
    public void clickContinueShopping() {
        try {
            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
            continueBtn.click();
        } catch (Exception e) {
            // Modal might not be present, continue
        }
    }
}
