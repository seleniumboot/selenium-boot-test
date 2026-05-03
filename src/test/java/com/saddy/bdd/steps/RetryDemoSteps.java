package com.saddy.bdd.steps;

import com.seleniumboot.cucumber.BaseCucumberSteps;
import com.seleniumboot.steps.StepLogger;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.concurrent.ConcurrentHashMap;

import static org.testng.Assert.assertTrue;

public class RetryDemoSteps extends BaseCucumberSteps {

    // Keyed by scenario ID so each scenario tracks its own attempt count
    // independently — required because the static field persists across retries
    private static final ConcurrentHashMap<String, Integer> ATTEMPTS = new ConcurrentHashMap<>();

    @Given("a step that fails on the first attempt")
    public void stepThatFailsOnFirstAttempt() {
        String scenarioId = getScenario().getId();
        int attempt = ATTEMPTS.merge(scenarioId, 1, Integer::sum);
        StepLogger.step("Scenario " + getScenario().getName() + " — attempt #" + attempt);

        assertTrue(attempt > 1,
                "Intentional failure on attempt 1 — framework should retry");
    }

    @Then("the scenario should have retried successfully")
    public void scenarioShouldHaveRetried() {
        String scenarioId = getScenario().getId();
        int attempt = ATTEMPTS.getOrDefault(scenarioId, 1);
        StepLogger.step("Passed on attempt #" + attempt);
        assertTrue(attempt > 1, "Should be on attempt 2 or later");
    }
}
