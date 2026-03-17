package com.saddy.tests;

import com.seleniumboot.precondition.PreCondition;
import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.test.BaseTest;
import org.testng.annotations.Test;

/**
 * Demonstrates @PreCondition — login runs once per thread, subsequent tests
 * restore the cached session (cookies + localStorage) without re-logging in.
 */
public class PreConditionDemoTest extends BaseTest {

    @Test(description = "View dashboard — session established by @PreCondition")
    @PreCondition("loginAsAdmin")
    public void viewDashboard() {
        StepLogger.step("Session restored — navigating to dashboard");
        open("/dashboard");
        StepLogger.step("Dashboard loaded without re-login");
    }

    @Test(description = "Second test — session restored from cache, no re-login")
    @PreCondition("loginAsAdmin")
    public void viewProfile() {
        StepLogger.step("Session restored from cache");
        open("/profile");
        StepLogger.step("Profile page loaded");
    }

    @Test(description = "Different role — loginAsUser condition")
    @PreCondition("loginAsUser")
    public void viewAsRegularUser() {
        StepLogger.step("Logged in as regular user");
        open("/dashboard");
        StepLogger.step("User dashboard loaded");
    }
}
