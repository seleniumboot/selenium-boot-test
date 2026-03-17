package com.saddy.tests;

import com.seleniumboot.browser.ConsoleErrorCollector;
import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Demonstrates ConsoleErrorCollector — captures JavaScript console errors
 * that would otherwise go unnoticed during test execution.
 */
public class ConsoleErrorDemoTest extends BaseTest {

    @Test(description = "Assert no JavaScript errors on the home page")
    public void noJsErrorsOnHomePage() {
        StepLogger.step("Open home page");
        open();

        StepLogger.step("Inject console shim (required for Firefox cross-browser support)");
        ConsoleErrorCollector.injectShim();

        StepLogger.step("Collect JS errors");
        List<String> errors = ConsoleErrorCollector.getErrors();

        StepLogger.step("Assert no JS errors — found: " + errors.size());
        Assert.assertTrue(errors.isEmpty(), "Unexpected JS console errors: " + errors);
    }

    @Test(description = "Isolate JS errors per interaction")
    public void jsErrorsIsolatedPerAction() {
        open();
        ConsoleErrorCollector.injectShim();

        StepLogger.step("Interact with page — checking errors per action");
        List<String> pageLoadErrors = ConsoleErrorCollector.getErrors();
        StepLogger.step("Page load errors: " + pageLoadErrors.size());

        ConsoleErrorCollector.clear();

        getDriver().navigate().refresh();
        List<String> refreshErrors = ConsoleErrorCollector.getErrors();
        StepLogger.step("Refresh errors: " + refreshErrors.size());

        Assert.assertTrue(refreshErrors.isEmpty(), "JS errors on refresh: " + refreshErrors);
    }
}
