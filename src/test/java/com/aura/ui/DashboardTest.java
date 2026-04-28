package com.aura.ui;

import com.aura.pages.DashboardPage;
import com.aura.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {

    @Test
    public void tcDash001_verifyUnauthenticatedRedirect() {
        driver.get(BASE_URL + "/dashboard.html");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login.html"));
    }

    @Test
    public void tcDash002_verifyDashboardLoadsForAuthenticatedUser() {
        driver.get(BASE_URL + "/login.html");
        new LoginPage(driver).loginAs("john.doe", "Pass@123");
        Assert.assertTrue(new DashboardPage(driver).isDashboardLoaded());
    }

    @Test
    public void tcDash003_verifyWelcomeMessage() {
        driver.get(BASE_URL + "/login.html");
        new LoginPage(driver).loginAs("john.doe", "Pass@123");
        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.getWelcomeText().contains("John Doe"));
    }

    @Test
    public void tcDash005_verifySidebarLogo() {
        driver.get(BASE_URL + "/login.html");
        new LoginPage(driver).loginAs("john.doe", "Pass@123");
        Assert.assertTrue(new DashboardPage(driver).isSidebarLogoPresent());
    }

    @Test
    public void tcDash006_verifyDashboardNavActive() {
        driver.get(BASE_URL + "/login.html");
        new LoginPage(driver).loginAs("john.doe", "Pass@123");
        Assert.assertTrue(new DashboardPage(driver).isDashboardNavActive());
    }

    @Test
    public void tcDash015_verifyLogoutRedirect() {
        driver.get(BASE_URL + "/login.html");
        new LoginPage(driver).loginAs("john.doe", "Pass@123");
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickLogout();
        Assert.assertTrue(driver.getCurrentUrl().contains("/login.html"));
    }
}