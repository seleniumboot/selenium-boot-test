package com.saddy.tests;

import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.steps.StepStatus;
import com.seleniumboot.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

/**
 * Demonstrates Phase 16 — Trace Viewer.
 *
 * Enable tracing in selenium-boot.yml:
 *   tracing:
 *     enabled: true
 *
 * On failure a self-contained trace HTML is generated at:
 *   target/traces/{ClassName}/{testMethod}-trace.html
 * and linked from the HTML report's "View Trace" button.
 */
public class TracingDemoTest extends BaseTest {

    private static final String URL = "https://testpages.eviltester.com/styled/basic-web-page-test.html";

    @Test(description = "Passing test with step logging — trace only if captureOnPass: true")
    public void passingTestWithSteps() {
        StepLogger.step("Open test page");
        getDriver().get(URL);

        StepLogger.step("Verify page title visible", true);   // with screenshot
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));

        StepLogger.step("Assert heading text", StepStatus.PASS);
        String heading = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertFalse(heading.isEmpty(), "Heading should not be empty");

        StepLogger.step("Test complete", StepStatus.PASS);
    }

    @Test(description = "Deliberately failing test — trace file should be generated at target/traces/")
    public void failingTestWithSteps() {
        StepLogger.step("Open test page");
        getDriver().get(URL);

        StepLogger.step("Look for heading", true);   // screenshot captured at this step
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));

        StepLogger.step("Intentional failure — check for trace file", StepStatus.FAIL);
        // This assertion will fail and trigger trace generation
        Assert.assertEquals(
                getDriver().getTitle(),
                "THIS_TITLE_DOES_NOT_EXIST",
                "Intentional failure to demonstrate trace viewer"
        );
    }
}
