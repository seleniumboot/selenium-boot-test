package com.saddy.tests;

import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.test.BaseTest;
import com.seleniumboot.wait.WaitEngine;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Demonstrates self-healing locators (Phase 17).
 *
 * Requires: locators.selfHealing: true in selenium-boot.yml
 *
 * Healing fires inside WaitEngine.waitForVisible / waitForClickable when the primary
 * locator times out. The framework parses By.toString() to extract fallback strategies
 * (id, name, class, data-testid, placeholder from CSS; id, name, text from XPath) and
 * tries each in order. Healed tests get a ⚠ healed badge in the HTML report and are
 * written to target/healed-locators.json.
 *
 * Test site: https://the-internet.herokuapp.com/login
 *   username field — id="username", name="username", type="text"
 *   password field — id="password", name="password", type="password"
 */
public class SelfHealingDemoTest extends BaseTest {

    private static final String LOGIN_URL = "https://the-internet.herokuapp.com/login";

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 1 — CSS compound selector: stale id + valid name
    // ─────────────────────────────────────────────────────────────────────────

    @Test(description = "Stale CSS id healed via name='username' — compound selector")
    public void heal_cssCompound_staleIdValidName() {
        StepLogger.step("Navigate to login page");
        getDriver().get(LOGIN_URL);

        StepLogger.step("Attempt find with stale id — heals via name='username'", true);
        // Primary:  #renamed-username-field[name='username']  — fails (no such id)
        // Fallback: By.id("renamed-username-field")           — fails
        // Fallback: By.name("username")                       — succeeds ✓
        WebElement field = WaitEngine.waitForVisible(
                By.cssSelector("#renamed-username-field[name='username']"));

        Assert.assertNotNull(field, "Username field should be recovered via name fallback");
        StepLogger.step("Healed — check target/healed-locators.json for the record");
    }

    @Test(description = "Stale CSS id healed via name='password' — password field")
    public void heal_cssCompound_passwordField() {
        StepLogger.step("Navigate to login page");
        getDriver().get(LOGIN_URL);

        StepLogger.step("Attempt find with stale id — heals via name='password'", true);
        // Primary:  #old-pwd-input[name='password']  — fails
        // Fallback: By.name("password")              — succeeds ✓
        WebElement field = WaitEngine.waitForVisible(
                By.cssSelector("#old-pwd-input[name='password']"));

        Assert.assertNotNull(field, "Password field should be recovered via name fallback");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 2 — Simulated DOM drift: JS renames the id at runtime
    // ─────────────────────────────────────────────────────────────────────────

    @Test(description = "DOM drift: JS renames id, healing recovers via name attribute")
    public void heal_afterJsDomDrift_usernameField() {
        StepLogger.step("Navigate to login page");
        getDriver().get(LOGIN_URL);

        StepLogger.step("Simulate frontend refactor — rename id 'username' → 'username_v2'");
        ((JavascriptExecutor) getDriver()).executeScript(
                "document.getElementById('username').id = 'username_v2';"
        );

        StepLogger.step("Try original CSS selector — id is now stale, healing via name", true);
        // Primary:  #username[name='username']  — fails (id is now username_v2)
        // Fallback: By.id("username")           — fails (same, id renamed)
        // Fallback: By.name("username")         — succeeds ✓
        WebElement field = WaitEngine.waitForVisible(
                By.cssSelector("#username[name='username']"));

        Assert.assertNotNull(field, "Field should be found after DOM drift via self-healing");
        StepLogger.step("Self-healing recovered element after simulated DOM drift");
    }

    @Test(description = "DOM drift on both fields — full login still completes via healing")
    public void heal_afterDomDrift_fullLoginFlow() {
        StepLogger.step("Navigate to login page");
        getDriver().get(LOGIN_URL);

        StepLogger.step("Rename both field ids to simulate a frontend refactor");
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("document.getElementById('username').id = 'username_new';");
        js.executeScript("document.getElementById('password').id = 'password_new';");

        StepLogger.step("Find username — healing via name='username'", true);
        WebElement username = WaitEngine.waitForVisible(
                By.cssSelector("#username[name='username']"));

        StepLogger.step("Find password — healing via name='password'", true);
        WebElement password = WaitEngine.waitForVisible(
                By.cssSelector("#password[name='password']"));

        username.clear();
        username.sendKeys("tomsmith");
        password.clear();
        password.sendKeys("SuperSecretPassword!");

        StepLogger.step("Submit — full login flow completed after healing both fields");
        WaitEngine.waitForClickable(By.cssSelector("button[type='submit']")).click();

        Assert.assertTrue(
                getDriver().getCurrentUrl().contains("secure"),
                "Should land on secure page after healed login"
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 3 — XPath with stale @id healed via @name
    // ─────────────────────────────────────────────────────────────────────────

    @Test(description = "XPath with stale @id healed via @name='username'")
    public void heal_xpath_staleIdViaName() {
        StepLogger.step("Navigate to login page");
        getDriver().get(LOGIN_URL);

        StepLogger.step("XPath locator has wrong @id — healing extracts @name and retries", true);
        // Primary:  //input[@id='stale-login-field' and @name='username']  — fails
        // Fallback: By.name("username")                                     — succeeds ✓
        WebElement field = WaitEngine.waitForVisible(
                By.xpath("//input[@id='stale-login-field' and @name='username']"));

        Assert.assertNotNull(field, "Username field should be found via XPath @name fallback");
        StepLogger.step("XPath self-healing via @name succeeded");
    }

    @Test(description = "XPath with stale @id healed via @name='password'")
    public void heal_xpath_passwordViaName() {
        StepLogger.step("Navigate to login page");
        getDriver().get(LOGIN_URL);

        StepLogger.step("XPath with wrong @id on password — healing via @name='password'", true);
        // Primary:  //input[@id='outdated-pwd' and @name='password']  — fails
        // Fallback: By.name("password")                               — succeeds ✓
        WebElement field = WaitEngine.waitForVisible(
                By.xpath("//input[@id='outdated-pwd' and @name='password']"));

        Assert.assertNotNull(field, "Password field should be found via XPath @name fallback");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 4 — No healing when element genuinely does not exist
    // ─────────────────────────────────────────────────────────────────────────

    @Test(description = "No healing when element is genuinely absent — exception surfaced")
    public void noHeal_whenElementGenuinelyMissing() {
        StepLogger.step("Navigate to login page");
        getDriver().get(LOGIN_URL);

        StepLogger.step("Use locator with no valid fallbacks — expect TimeoutException");
        boolean exceptionThrown = false;
        try {
            // No element on page has id="ghost-button", and no other attribute matches
            WaitEngine.waitForVisible(By.cssSelector("#ghost-button-that-does-not-exist"));
        } catch (TimeoutException e) {
            exceptionThrown = true;
            StepLogger.step("Correct — TimeoutException surfaced after all fallbacks exhausted");
        }

        Assert.assertTrue(exceptionThrown,
                "Framework must surface the original exception when no fallback heals the locator");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Scenario 5 — waitForClickable also heals
    // ─────────────────────────────────────────────────────────────────────────

    @Test(description = "waitForClickable heals stale id on submit button via class fallback")
    public void heal_waitForClickable_submitButton() {
        StepLogger.step("Navigate to login page");
        getDriver().get(LOGIN_URL);

        StepLogger.step("Find submit button by stale id + class — heals via className", true);
        // The submit button has class="radius" and no id — primary selector #old-submit.radius fails
        // Fallback: By.className("radius") — succeeds ✓
        WebElement btn = WaitEngine.waitForClickable(
                By.cssSelector("#old-submit-id.radius"));

        Assert.assertNotNull(btn, "Submit button should be found via className fallback");
        Assert.assertTrue(btn.isEnabled(), "Healed submit button should be enabled");
        StepLogger.step("waitForClickable self-healing via className succeeded");
    }
}
