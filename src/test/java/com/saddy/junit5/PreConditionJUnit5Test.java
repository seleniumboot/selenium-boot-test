package com.saddy.junit5;

import com.saddy.pages.LoginPage;
import com.seleniumboot.junit5.BaseJUnit5Test;
import com.seleniumboot.precondition.PreCondition;
import com.seleniumboot.steps.StepLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

/**
 * Demonstrates @PreCondition for JUnit 5.
 *
 * The "loginAsAdmin" condition runs once and caches the session
 * (cookies + localStorage). Every test in this class restores
 * that cached session instead of logging in again.
 */
@DisplayName("@PreCondition — JUnit 5")
class PreConditionJUnit5Test extends BaseJUnit5Test {

    @Test
    @PreCondition("loginAsAdmin")
    @DisplayName("Dashboard loads — session restored from cache")
    void viewDashboard() {
        step("Session restored by @PreCondition — no login needed");
        open("/");
        assertThat(By.id("username")).isVisible();
        StepLogger.step("Page loaded with cached session");
    }

    @Test
    @PreCondition("loginAsAdmin")
    @DisplayName("Second test reuses cached session — provider runs once total")
    void viewDashboardAgain() {
        step("Same @PreCondition — session served from cache, provider not re-run");
        open("/");
        assertThat(By.id("username")).isVisible();
    }

    @Test
    @DisplayName("Test without @PreCondition — no session restoration")
    void noPreCondition() {
        open();
        step("No @PreCondition — fresh session, login form visible");
        assertThat(By.id("username")).isVisible();
        assertThat(By.id("password")).isVisible();
    }
}
