package com.saddy.tests;

import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Demonstrates Phase 17 features:
 *
 * 1. Self-Healing Locators — enable via locators.selfHealing: true in selenium-boot.yml
 *    When a locator fails, the framework tries fallback strategies automatically.
 *    Heals are logged to target/healed-locators.json and shown in the HTML report.
 *
 * 2. AI Failure Analysis — enable via ai.failureAnalysis: true + ai.apiKey: ${CLAUDE_API_KEY}
 *    On failure, Claude analyses the error + steps and generates a root-cause explanation.
 *    Shown in the HTML report detail panel below the stack trace.
 *
 * 3. Flakiness Prediction — runs automatically after suite; reads target/metrics-history/.
 *    High-risk tests shown in the "Flakiness Radar" section of the HTML report.
 */
public class Phase17DemoTest extends BaseTest {

    private static final String URL = "https://testpages.eviltester.com/styled/basic-web-page-test.html";

    @Test(description = "Normal test — passes cleanly; contributes to flakiness history")
    public void stablePassingTest() {
        StepLogger.step("Navigate to test page");
        getDriver().get(URL);

        StepLogger.step("Verify heading is present", true);
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));

        String heading = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertFalse(heading.isEmpty(), "Heading should not be empty");
    }

    @Test(description = "Failing test — triggers AI failure analysis (if ai.failureAnalysis=true)")
    public void failingTestForAiAnalysis() {
        StepLogger.step("Navigate to page");
        getDriver().get(URL);

        StepLogger.step("Check title", true);
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));

        StepLogger.step("Intentional assertion failure");
        // Deliberate failure — AI analysis will describe why this assertion fails
        Assert.assertEquals(
                getDriver().findElement(By.tagName("h1")).getText(),
                "WRONG_EXPECTED_TITLE",
                "Intentional failure to demonstrate AI failure analysis"
        );
    }
}
