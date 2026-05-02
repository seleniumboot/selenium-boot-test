package com.saddy.junit5;

import com.saddy.pages.LoginPage;
import com.seleniumboot.junit5.BaseJUnit5Test;
import com.seleniumboot.steps.StepStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Demonstrates Selenium Boot JUnit 5 integration via BaseJUnit5Test.
 *
 * Driver lifecycle, HTML report, step timeline, and screenshots are
 * all handled automatically by SeleniumBootExtension.
 */
@DisplayName("Login — JUnit 5")
class LoginJUnit5Test extends BaseJUnit5Test {

    @Test
    @DisplayName("Login page shows username, password and submit fields")
    void loginPageShowsRequiredFields() {
        step("Open login page");
        open();

        step("Assert all fields visible", true);
        assertThat(By.id("username")).isVisible();
        assertThat(By.id("password")).isVisible();
        assertThat(By.cssSelector("button[type='submit']")).isVisible();
    }

    @Test
    @DisplayName("Valid credentials allow login")
    void validCredentialsAllowLogin() {
        step("Open application");
        open();

        step("Enter credentials and submit", true);
        new LoginPage(getDriver()).login("admin", "password");

        step("Verify login succeeded", StepStatus.PASS);
        assertFalse(getDriver().getTitle().isEmpty(), "Page title should not be empty after login");
    }

    @Test
    @DisplayName("Fluent locator API works in JUnit 5")
    void fluentLocatorApiWorks() {
        open();
        step("Read page heading via $()");

        String heading = $("h1").getText();
        assertFalse(heading.isEmpty(), "h1 heading should not be empty");
        step("Heading: " + heading, StepStatus.PASS);
    }

    @Test
    @DisplayName("Web-first assertThat() auto-retries until visible")
    void webFirstAssertionAutoRetries() {
        open();
        step("assertThat username field is visible");
        assertThat(By.id("username")).isVisible();

        step("assertThat password field is enabled");
        assertThat(By.id("password")).isEnabled();
    }
}
