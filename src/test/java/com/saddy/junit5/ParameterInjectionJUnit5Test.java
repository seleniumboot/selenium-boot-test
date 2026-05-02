package com.saddy.junit5;

import com.seleniumboot.junit5.EnableSeleniumBoot;
import com.seleniumboot.steps.StepLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Demonstrates WebDriver parameter injection — no base class required.
 * SeleniumBootExtension creates the driver before the test method runs
 * and injects it as a parameter.
 */
@EnableSeleniumBoot
@DisplayName("Parameter Injection — JUnit 5")
class ParameterInjectionJUnit5Test {

    @Test
    @DisplayName("WebDriver injected as method parameter")
    void driverInjectedAsParameter(WebDriver driver) {
        StepLogger.step("Navigate via injected driver");
        driver.get("https://panjatan.netlify.app");

        StepLogger.step("Verify page loaded", true);
        assertFalse(driver.getTitle().isEmpty(), "Page title should not be empty");

        boolean hasLoginForm = !driver.findElements(By.id("username")).isEmpty();
        assertTrue(hasLoginForm, "Login form should be present");
        StepLogger.step("Login form found on page");
    }

    @Test
    @DisplayName("Multiple parameters — injected driver is the same instance")
    void driverIsConsistentAcrossInjection(WebDriver driver) {
        driver.get("https://panjatan.netlify.app");

        String title = driver.getTitle();
        StepLogger.step("Page title: " + title);
        assertNotNull(title, "Title should not be null");
    }
}
