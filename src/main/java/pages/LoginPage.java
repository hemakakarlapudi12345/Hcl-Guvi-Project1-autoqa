package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators
    private By signupLoginLink = By.cssSelector("a[href='/login']");
    private By loginEmailInput = By.cssSelector("input[data-qa='login-email']");
    private By loginPasswordInput = By.cssSelector("input[data-qa='login-password']");
    private By loginButton = By.cssSelector("button[data-qa='login-button']");
    private By logoutButton = By.cssSelector("a[href='/logout']");
    private By loginErrorMessage = By.cssSelector("form[action='/login'] p");
    private By loginFormTitle = By.cssSelector("div.login-form h2");
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void clickSignupLogin() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(signupLoginLink));
        element.click();
    }
    
    public void enterLoginEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(loginEmailInput));
        element.clear();
        element.sendKeys(email);
    }
    
    public void enterLoginPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(loginPasswordInput));
        element.clear();
        element.sendKeys(password);
    }
    
    public void clickLoginButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        element.click();
    }
    
    public boolean isLogoutButtonVisible() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isLoginErrorMessageDisplayed() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(loginErrorMessage));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getLoginErrorMessage() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(loginErrorMessage));
        return element.getText();
    }
    
    public boolean isLoginFormDisplayed() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(loginFormTitle));
            return element.isDisplayed() && element.getText().contains("Login to your account");
        } catch (Exception e) {
            return false;
        }
    }
    
    public void login(String email, String password) {
        enterLoginEmail(email);
        enterLoginPassword(password);
        clickLoginButton();
    }
}
