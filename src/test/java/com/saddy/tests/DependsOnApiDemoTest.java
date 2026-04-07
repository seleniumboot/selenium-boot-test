package com.saddy.tests;

import com.seleniumboot.precondition.DependsOnApi;
import com.seleniumboot.test.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Demonstrates @DependsOnApi behaviour.
 *
 * Scenarios:
 *   1. reachable URL  → test runs normally
 *   2. unreachable URL → test is skipped (not failed)
 *   3. class-level annotation → all tests in the class share the guard
 */
public class DependsOnApiDemoTest extends BaseTest {

    // ── Scenario 1: reachable API — test should RUN ───────────────────────

    @Test(description = "API is up — test runs normally")
    @DependsOnApi("https://panjatan.netlify.app")
    public void test_runsWhenApiIsUp() {
        getDriver().get("https://panjatan.netlify.app");
        assertTrue(getDriver().getTitle().length() > 0, "Page should load when API is reachable");
    }

    // ── Scenario 2: unreachable API — test should be SKIPPED ─────────────

    @Test(description = "API is down — test should be skipped")
    @DependsOnApi("http://127.0.0.1:1")
    public void test_skippedWhenApiIsDown() {
        // This line should never execute — the framework skips this test
        // before the browser even opens.
        throw new AssertionError("This test must be skipped, not run!");
    }

    // ── Scenario 3: multiple dependencies — all must be up ────────────────

    @Test(description = "Both APIs up — test runs")
    @DependsOnApi("https://panjatan.netlify.app")
    @DependsOnApi("https://httpbin.org/status/200")
    public void test_runsWhenBothApisAreUp() {
        getDriver().get("https://panjatan.netlify.app");
        assertFalse(getDriver().getTitle().isEmpty(), "Page should load with all deps healthy");
    }

    @Test(description = "One of two APIs is down — test should be skipped")
    @DependsOnApi("https://panjatan.netlify.app")
    @DependsOnApi("http://127.0.0.1:1")
    public void test_skippedWhenOneDepIsDown() {
        throw new AssertionError("This test must be skipped, not run!");
    }
}
