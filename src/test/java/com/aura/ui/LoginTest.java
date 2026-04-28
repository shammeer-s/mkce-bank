package com.aura.ui;

import com.aura.pages.DashboardPage;
import com.aura.pages.LoginPage;
import com.aura.utils.csvDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void tcLogin001_verifyLoginPageLoads() {
        driver.get(BASE_URL + "/login.html");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login.html"));
    }

    @Test
    public void tcLogin002_verifyLogoAndTitle() {
        driver.get(BASE_URL + "/login.html");
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isElementPresent(loginPage.getLogoLocator()));
        Assert.assertEquals(driver.getTitle(), "AuraBank — Sign In");
    }

    @Test
    public void tcLogin003_verifyUsernameFieldPresent() {
        driver.get(BASE_URL + "/login.html");
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isElementPresent(loginPage.getUsernameLocator()));
    }

    @Test
    public void tcLogin004_verifyPasswordFieldPresent() {
        driver.get(BASE_URL + "/login.html");
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isElementPresent(loginPage.getPasswordLocator()));
    }

    @Test
    public void tcLogin005_verifyTestModeTogglePresent() {
        driver.get(BASE_URL + "/login.html");
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isElementPresent(loginPage.getTestModeLocator()));
    }

    @Test
    public void tcLogin006_verifySignInButtonPresent() {
        driver.get(BASE_URL + "/login.html");
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isElementPresent(loginPage.getLoginBtnLocator()));
    }

    @Test
    public void tcLogin007_verifyPasswordTogglePresent() {
        driver.get(BASE_URL + "/login.html");
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isElementPresent(loginPage.getTogglePasswordLocator()));
    }

    @Test
    public void tcLogin026_verifyPasswordMaskedByDefault() {
        driver.get(BASE_URL + "/login.html");
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isPasswordMasked());
    }

    @Test
    public void tcLogin027_verifyPasswordUnmaskedOnClick() {
        driver.get(BASE_URL + "/login.html");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickTogglePassword();
        Assert.assertFalse(loginPage.isPasswordMasked());
    }

    @Test
    public void tcLogin028_verifyPasswordMaskedOnSecondClick() {
        driver.get(BASE_URL + "/login.html");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickTogglePassword();
        loginPage.clickTogglePassword();
        Assert.assertTrue(loginPage.isPasswordMasked());
    }

    @Test(dataProvider = "loginData", dataProviderClass = csvDataProvider.class)
    public void dataDrivenLoginTests(String username, String password, String expectedCondition) {
        driver.get(BASE_URL + "/login.html");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickSignIn();

        DashboardPage dashboardPage = new DashboardPage(driver);

        switch (expectedCondition) {
            case "success":
                Assert.assertTrue(dashboardPage.isDashboardLoaded());
                break;
            case "failure":
                Assert.assertTrue(loginPage.getErrorMessage().contains("Invalid username or password"));
                break;
            case "validationError":
                Assert.assertTrue(driver.getCurrentUrl().contains("/login.html"));
                break;
        }
    }
}