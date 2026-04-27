package com.saddy.plugins;

import com.seleniumboot.driver.DriverManager;
import com.seleniumboot.hooks.ExecutionHook;
import com.seleniumboot.internal.SeleniumBootContext;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Reports Sauce Labs job status after each test via the ExecutionHook SPI.
 *
 * Active only when execution.gridUrl contains "saucelabs.com" — no-op otherwise.
 * Registered via META-INF/services/com.seleniumboot.hooks.ExecutionHook.
 */
public class SauceLabsPlugin implements ExecutionHook {

    @Override
    public void onTestEnd(String testId, String status) {
        if (!isActive()) return;
        // onTestEnd fires for PASSED and SKIPPED — mark SKIPPED as passed
        // since the job ran successfully (just skipped, not a failure)
        executeScript("sauce:job-result=passed");
    }

    @Override
    public void onTestFailure(String testId, Throwable cause) {
        if (!isActive()) return;
        executeScript("sauce:job-result=failed");
    }

    private boolean isActive() {
        try {
            String gridUrl = SeleniumBootContext.getConfig().getExecution().getGridUrl();
            return gridUrl != null && gridUrl.contains("saucelabs.com");
        } catch (Exception e) {
            return false;
        }
    }

    private void executeScript(String script) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript(script);
            }
        } catch (Exception ignored) {
            // Driver may be closing; non-critical
        }
    }
}
