package com.saddy.tests;

import com.seleniumboot.client.ApiResponse;
import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Demo: Hybrid UI + API test using BaseTest.
 * Uses apiClient() to make API calls, then validates via browser.
 */
public class HybridDemoTest extends BaseTest {

    @Test(description = "Fetch user via API, store in context, verify on page")
    public void hybridApiThenUi() {
        // Step 1: Fetch data via API
        ApiResponse res = apiClient()
                .get("https://jsonplaceholder.typicode.com/users/1")
                .send();

        res.assertStatus(200);
        String username = res.json("$.username");
        StepLogger.step("Fetched username from API: " + username);

        // Store in context for later use
        ctx().set("username", username);

        // Step 2: Navigate UI and verify (example: check page title)
        open();
        String pageTitle = getDriver().getTitle();
        StepLogger.step("Page title: " + pageTitle);
        Assert.assertNotNull(pageTitle, "Page title should not be null");

        // Step 3: Confirm context survives within the same test
        String storedUsername = ctx().get("username");
        Assert.assertEquals(storedUsername, username, "ScenarioContext should retain value within test");
        StepLogger.step("ScenarioContext verified: username = " + storedUsername);
    }
}
