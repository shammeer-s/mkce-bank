package com.aura.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameInput = By.id("input-usernamee");
    private By passwordInput = By.id("input-password");
    private By loginBtn = By.id("btn-login");
    private By testModeToggle = By.id("toggle-test-mode");
    private By togglePasswordBtn = By.id("btn-toggle-password");
    private By loginError = By.id("login-error");
    private By logoIcon = By.cssSelector(".logo-icon");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickSignIn() {
        driver.findElement(loginBtn).click();
    }

    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignIn();
    }

    public boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginError)).getText();
    }

    public boolean isPasswordMasked() {
        return driver.findElement(passwordInput).getAttribute("type").equals("password");
    }

    public void clickTogglePassword() {
        driver.findElement(togglePasswordBtn).click();
    }

    public By getUsernameLocator() { return usernameInput; }
    public By getPasswordLocator() { return passwordInput; }
    public By getLoginBtnLocator() { return loginBtn; }
    public By getTestModeLocator() { return testModeToggle; }
    public By getTogglePasswordLocator() { return togglePasswordBtn; }
    public By getLogoLocator() { return logoIcon; }
}