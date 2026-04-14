package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators
    private By checkoutTitle = By.cssSelector("h2.title.text-center");
    private By nameInput = By.cssSelector("input[name='name']");
    private By emailInput = By.cssSelector("input[name='email']");
    private By addressInput = By.cssSelector("textarea[name='address']");
    private By countryInput = By.cssSelector("input[name='country']");
    private By cityInput = By.cssSelector("input[name='city']");
    private By stateInput = By.cssSelector("input[name='state']");
    private By zipInput = By.cssSelector("input[name='zipcode']");
    private By cardNumberInput = By.cssSelector("input[name='card_number']");
    private By cardExpiryInput = By.cssSelector("input[name='card_expiry']");
    private By cardCvcInput = By.cssSelector("input[name='card_cvc']");
    private By payButton = By.cssSelector("button[type='submit']");
    private By orderSuccessMessage = By.cssSelector("h2.title.text-center");
    private By continueButton = By.cssSelector("a[href='/']");
    
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public boolean isCheckoutPageDisplayed() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutTitle));
            return element.isDisplayed() && element.getText().contains("Checkout");
        } catch (Exception e) {
            return false;
        }
    }
    
    public void fillCheckoutDetails() {
        try {
            // Fill in billing details - try different possible locators
            try {
                WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
                name.clear();
                name.sendKeys("Test User");
            } catch (Exception e) {
                // Try alternative name locator
                WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-qa='name']")));
                name.clear();
                name.sendKeys("Test User");
            }
            
            try {
                WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
                email.clear();
                email.sendKeys("test@example.com");
            } catch (Exception e) {
                // Try alternative email locator
                WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-qa='email']")));
                email.clear();
                email.sendKeys("test@example.com");
            }
            
            try {
                WebElement address = wait.until(ExpectedConditions.visibilityOfElementLocated(addressInput));
                address.clear();
                address.sendKeys("123 Test Street");
            } catch (Exception e) {
                // Try alternative address locator
                WebElement address = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("textarea[data-qa='address']")));
                address.clear();
                address.sendKeys("123 Test Street");
            }
            
            // Fill other fields with fallbacks
            try {
                WebElement country = wait.until(ExpectedConditions.visibilityOfElementLocated(countryInput));
                country.clear();
                country.sendKeys("United States");
            } catch (Exception e) {
                // Skip if not found
            }
            
            try {
                WebElement city = wait.until(ExpectedConditions.visibilityOfElementLocated(cityInput));
                city.clear();
                city.sendKeys("New York");
            } catch (Exception e) {
                // Skip if not found
            }
            
            try {
                WebElement state = wait.until(ExpectedConditions.visibilityOfElementLocated(stateInput));
                state.clear();
                state.sendKeys("NY");
            } catch (Exception e) {
                // Skip if not found
            }
            
            try {
                WebElement zip = wait.until(ExpectedConditions.visibilityOfElementLocated(zipInput));
                zip.clear();
                zip.sendKeys("10001");
            } catch (Exception e) {
                // Skip if not found
            }
            
            // Fill in payment details with fallbacks
            try {
                WebElement cardNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumberInput));
                cardNumber.clear();
                cardNumber.sendKeys("4111111111111111");
            } catch (Exception e) {
                // Try alternative card number locator
                WebElement cardNumber = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-qa='card-number']")));
                cardNumber.clear();
                cardNumber.sendKeys("4111111111111111");
            }
            
            try {
                WebElement cardExpiry = wait.until(ExpectedConditions.visibilityOfElementLocated(cardExpiryInput));
                cardExpiry.clear();
                cardExpiry.sendKeys("12/25");
            } catch (Exception e) {
                // Try alternative card expiry locator
                WebElement cardExpiry = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-qa='card-expiry']")));
                cardExpiry.clear();
                cardExpiry.sendKeys("12/25");
            }
            
            try {
                WebElement cardCvc = wait.until(ExpectedConditions.visibilityOfElementLocated(cardCvcInput));
                cardCvc.clear();
                cardCvc.sendKeys("123");
            } catch (Exception e) {
                // Try alternative card CVC locator
                WebElement cardCvc = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-qa='card-cvc']")));
                cardCvc.clear();
                cardCvc.sendKeys("123");
            }
        } catch (Exception e) {
            // If form filling fails, continue with test
            System.out.println("Some form fields may not be available, continuing with test");
        }
    }
    
    public void clickPayButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(payButton));
        element.click();
    }
    
    public boolean isOrderSuccessful() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage));
            return element.isDisplayed() && element.getText().toLowerCase().contains("order placed");
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clickContinue() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            element.click();
        } catch (Exception e) {
            // Continue button might not be present
        }
    }
}
