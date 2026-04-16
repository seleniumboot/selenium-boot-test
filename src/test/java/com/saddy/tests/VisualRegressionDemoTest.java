package com.saddy.tests;

import com.seleniumboot.test.BaseTest;
import com.seleniumboot.visual.VisualTolerance;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * Demonstrates VisualAssert — pixel-by-pixel screenshot regression testing.
 *
 * First run: baselines are created in src/test/resources/baselines/.
 * Subsequent runs: current screenshots are compared against the baselines.
 * To regenerate baselines: mvn test -DupdateBaselines=true
 */
public class VisualRegressionDemoTest extends BaseTest {

    private static final String BASE_URL = "https://testpages.eviltester.com/styled/basic-web-page-test.html";

    @Test(description = "Full-page visual baseline — first run saves baseline, subsequent runs compare")
    public void fullPageVisualMatch() {
        getDriver().get(BASE_URL);
        // First run: saves baseline. Subsequent runs: compares against it.
        assertScreenshot("basic-page-full");
    }

    @Test(description = "Full-page comparison with 2% pixel-difference tolerance")
    public void fullPageWithTolerance() {
        getDriver().get(BASE_URL);
        assertScreenshot("basic-page-tolerant", VisualTolerance.of(2));
    }

    @Test(description = "Element-scoped visual comparison — only the heading area")
    public void elementScopedVisualMatch() {
        getDriver().get(BASE_URL);
        assertScreenshot("basic-page-heading", By.tagName("h1"));
    }

    @Test(description = "Element-scoped with 1% tolerance")
    public void elementScopedWithTolerance() {
        getDriver().get(BASE_URL);
        assertScreenshot("basic-page-heading-tolerant", By.tagName("h1"), VisualTolerance.of(1));
    }
}
