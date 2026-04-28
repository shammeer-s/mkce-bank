package com.aura.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By welcomeText = By.id("text-welcome");
    private By navDashboard = By.id("nav-dashboard");
    private By logoutBtn = By.id("btn-logout");
    private By sidebarLogo = By.cssSelector(".sidebar-logo");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isDashboardLoaded() {
        return wait.until(ExpectedConditions.urlContains("/dashboard.html"));
    }

    public String getWelcomeText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeText)).getText();
    }

    public boolean isSidebarLogoPresent() {
        return !driver.findElements(sidebarLogo).isEmpty();
    }

    public boolean isDashboardNavActive() {
        return driver.findElement(navDashboard).getAttribute("class").contains("active");
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
    }
}