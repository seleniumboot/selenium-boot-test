package com.saddy.junit5;

import com.seleniumboot.junit5.BaseJUnit5Test;
import com.seleniumboot.listeners.Retryable;
import com.seleniumboot.steps.StepLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Demonstrates @Retryable for JUnit 5.
 *
 * The test intentionally passes on the second attempt to show that:
 *  - the framework retries after failure
 *  - the HTML report shows a retry badge (↻ 1x)
 *  - the final status is PASSED
 */
@DisplayName("Retry — JUnit 5")
class RetryJUnit5Test extends BaseJUnit5Test {

    private static int attempt = 0;

    @Test
    @Retryable(maxAttempts = 1)
    @DisplayName("Passes on second attempt — retry badge appears in report")
    void passesOnSecondAttempt() {
        attempt++;
        step("Attempt #" + attempt);
        StepLogger.step("Current attempt: " + attempt);

        // Fail on first attempt, pass on retry
        assertTrue(attempt > 1,
                "Intentional failure on attempt 1 — framework should retry once");
    }

    @Test
    @Retryable(maxAttempts = 2)
    @DisplayName("Retry respects maxAttempts = 2 from annotation")
    void respectsAnnotationMaxAttempts() {
        open();
        step("Verify page loads — this test is stable, retry never fires");
        assertTrue(!getDriver().getTitle().isEmpty(), "Page title should not be empty");
    }
}
