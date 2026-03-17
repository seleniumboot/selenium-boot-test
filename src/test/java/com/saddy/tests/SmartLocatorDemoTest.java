package com.saddy.tests;

import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.test.BaseTest;
import com.seleniumboot.test.SmartLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Demonstrates SmartLocator — tries multiple By strategies in order and
 * returns the first visible element found. Useful when locators may vary
 * across environments or after UI changes.
 */
public class SmartLocatorDemoTest extends BaseTest {

    @Test(description = "SmartLocator finds login field using multiple strategies")
    public void smartLocatorFindsElement() {
        open("/");
        StepLogger.step("Try multiple locator strategies for username field");

        // SmartLocator tries each By in order — returns first visible match
        WebElement usernameField = SmartLocator.find(
                getDriver(),
                By.id("username"),
                By.name("username"),
                By.cssSelector("input[type='text']"),
                By.xpath("//input[@placeholder='Username']")
        );

        StepLogger.step("Element found — tag: " + usernameField.getTagName());
        Assert.assertNotNull(usernameField, "Username field should be found by at least one locator");
    }

    @Test(description = "SmartLocator.isAnyVisible checks element presence across strategies")
    public void smartLocatorIsAnyVisible() {
        open("/");
        StepLogger.step("Check if login button is visible via any locator");

        boolean loginVisible = SmartLocator.isAnyVisible(
                getDriver(),
                By.id("login-btn"),
                By.cssSelector("button[type='submit']"),
                By.xpath("//button[contains(text(), 'Login')]")
        );

        StepLogger.step("Login button visible: " + loginVisible);
        Assert.assertTrue(loginVisible, "Login button should be visible on the home page");
    }
}
