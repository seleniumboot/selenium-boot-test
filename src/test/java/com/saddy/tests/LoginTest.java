package com.saddy.tests;

import com.saddy.pages.LoginPage;
import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.steps.StepStatus;
import com.seleniumboot.test.BaseTest;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Valid login with correct credentials")
    public void loginTest() {
        open();
        StepLogger.step("Navigate to Login Page");

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterUsername("admin");
        loginPage.enterPassword("password");
        StepLogger.step("Entered username and password", true);

        loginPage.clickLoginButton();
        StepLogger.step("Clicked Login button", StepStatus.PASS);
    }
}
