package com.saddy.tests;

import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Demonstrates SmartLocator — tries multiple By strategies in order and
 * returns the first visible element found. Useful when locators may vary
 * across environments or after UI changes.
 *
 * <p>In page objects, use the {@code BasePage.smartFind()} helper — no need to pass the driver.
 * See {@link com.saddy.pages.LoginPage#clickLoginButton()} for a real-world example.
 *
 * <p>Use {@code SmartLocator} directly in tests only when working outside a page object.
 */
public class SmartLocatorDemoTest extends BaseTest {

    @Test(description = "Login button resolves via SmartLocator fallback chain inside LoginPage")
    public void smartLocatorViaPageObject() {
        open("/");
        StepLogger.step("LoginPage.clickLoginButton uses smartFind — tries id, CSS, XPath in order");

        // LoginPage.clickLoginButton internally calls smartFind(LOGIN_BTN_PRIMARY, CSS, XPATH)
        // The framework logs which strategy succeeded
        new com.saddy.pages.LoginPage(getDriver()).login("admin", "password");

        StepLogger.step("Login completed — SmartLocator resolved the button");
    }

    @Test(description = "SmartLocator.isAnyVisible checks login button presence via page object")
    public void smartLocatorIsAnyVisible() {
        open("/");
        StepLogger.step("Check if login button is visible via any locator");

        boolean loginVisible = new com.saddy.pages.LoginPage(getDriver()).isLoginButtonVisible();

        StepLogger.step("Login button visible: " + loginVisible);
        Assert.assertTrue(loginVisible, "Login button should be visible on the home page");
    }
}
