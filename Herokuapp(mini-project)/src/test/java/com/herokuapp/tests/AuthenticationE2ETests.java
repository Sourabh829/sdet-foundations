package com.herokuapp.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.herokuapp.base.BaseTest;
import com.herokuapp.pages.HomePage;
import com.herokuapp.pages.LoginPage;
import com.herokuapp.pages.SecureAreaPage;

public class AuthenticationE2ETests extends BaseTest {

    @Test(description = "Scenario 1: User can login with valid credentials")
    public void shouldLoginSuccessfullyWithValidCredentials() {
        SecureAreaPage secureArea = new HomePage(driver, wait)
                .open()
                .openFormAuthentication()
                .loginAs(LoginPage.VALID_USERNAME, LoginPage.VALID_PASSWORD);

        Assert.assertEquals(secureArea.getHeading(), "Secure Area");
        Assert.assertTrue(secureArea.getFlashMessage().contains("You logged into a secure area!"));
        Assert.assertTrue(secureArea.isLogoutButtonDisplayed());
    }

    @Test(description = "Scenario 2: User can logout after successful login")
    public void shouldLogoutAfterSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver, wait)
                .open()
                .loginAs(LoginPage.VALID_USERNAME, LoginPage.VALID_PASSWORD)
                .logout();

        Assert.assertTrue(loginPage.isLoginPageDisplayed());
        Assert.assertTrue(loginPage.getFlashMessage().contains("You logged out of the secure area!"));
    }

    @Test(description = "Scenario 3: Invalid username shows an error")
    public void shouldRejectInvalidUsername() {
        LoginPage loginPage = new LoginPage(driver, wait)
                .open()
                .attemptLogin("wrong-user", LoginPage.VALID_PASSWORD);

        Assert.assertTrue(loginPage.isLoginPageDisplayed());
        Assert.assertTrue(loginPage.getFlashMessage().contains("Your username is invalid!"));
    }

    @Test(description = "Scenario 4: Invalid password shows an error")
    public void shouldRejectInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver, wait)
                .open()
                .attemptLogin(LoginPage.VALID_USERNAME, "wrong-password");

        Assert.assertTrue(loginPage.isLoginPageDisplayed());
        Assert.assertTrue(loginPage.getFlashMessage().contains("Your password is invalid!"));
    }

    @Test(description = "Scenario 5: Signup is not available on The Internet app")
    public void shouldConfirmSignupFlowIsNotAvailable() {
        LoginPage loginPage = new LoginPage(driver, wait).open();

        Assert.assertTrue(loginPage.isLoginPageDisplayed());
        Assert.assertFalse(
                loginPage.hasSignupEntryPoint(),
                "the-internet.herokuapp.com does not expose a signup/register flow.");
    }
}
