package com.saddy.tests;

import com.seleniumboot.locator.Role;
import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.test.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Validates the v3.1.0 accessibility-first locators (getByRole, getByText,
 * getByLabel, getByPlaceholder, getByTestId, getByTitle) against the live
 * playground at https://panjatan.netlify.app.
 *
 * <p>These are real, browser-backed tests — each locator is resolved against
 * the actual accessibility tree of the page.
 */
public class SemanticLocatorDemoTest extends BaseTest {

    // ── getByRole ────────────────────────────────────────────────────

    @Test(description = "getByRole(BUTTON).withName — resolves the Login button by accessible name")
    public void role_button_byName_isVisible() {
        open();
        StepLogger.step("getByRole(BUTTON).withName(\"Login\")");
        assertTrue(getByRole(Role.BUTTON).withName("Login").isVisible(),
                "Login button should be found by role + accessible name");
        StepLogger.step("Login button located via role", true);
    }

    @Test(description = "getByRole(HEADING).withLevel(1) — reads the h1 heading text")
    public void role_heading_level1_getText() {
        open();
        StepLogger.step("getByRole(HEADING).withLevel(1).getText()");
        String h1 = getByRole(Role.HEADING).withLevel(1).getText();
        assertTrue(h1.toLowerCase().contains("playground"),
                "h1 should mention 'Playground' — was: " + h1);
        StepLogger.step("Heading text: " + h1, true);
    }

    @Test(description = "getByRole(LINK, name) — two-arg overload locates a nav link")
    public void role_link_twoArg_isVisible() {
        open();
        StepLogger.step("getByRole(LINK, \"Web Table\")");
        assertTrue(getByRole(Role.LINK, "Web Table").isVisible(),
                "'Web Table' link should be found by role + name");
        StepLogger.step("Link located via role overload", true);
    }

    @Test(description = "getByRole(BUTTON).withName().exact() — exact accessible-name match")
    public void role_button_exactName() {
        open();
        StepLogger.step("getByRole(BUTTON).withName(\"Login\").exact()");
        assertTrue(getByRole(Role.BUTTON).withName("Login").exact().isVisible(),
                "Exact accessible-name match should still find Login");
        StepLogger.step("Exact name match succeeded", true);
    }

    // ── getByText ────────────────────────────────────────────────────

    @Test(description = "getByText — case-insensitive substring locates hero CTA text")
    public void text_caseInsensitive_isVisible() {
        open();
        StepLogger.step("getByText(\"start practicing\") — case-insensitive");
        assertTrue(getByText("start practicing").isVisible(),
                "getByText should match regardless of case");
        StepLogger.step("Text located case-insensitively", true);
    }

    // ── getByLabel ───────────────────────────────────────────────────

    @Test(description = "getByLabel — types into a field located by its <label> text")
    public void label_typesIntoField() {
        open();
        StepLogger.step("getByLabel(\"Username\").type(...)");
        getByLabel("Username").type("admin");
        // The field is #username; verify the value landed via attribute read
        assertEquals(getByLabel("Username").getAttribute("value"), "admin",
                "Value typed via getByLabel should be present");
        StepLogger.step("Typed into label-associated field", true);
    }

    // ── getByPlaceholder ─────────────────────────────────────────────

    @Test(description = "getByPlaceholder — types into the table search box")
    public void placeholder_typesIntoSearch() {
        open();
        StepLogger.step("getByPlaceholder(\"Search table\").type(...)");
        getByPlaceholder("Search table").type("Priya");
        assertEquals(getByPlaceholder("Search table").getAttribute("value"), "Priya");
        StepLogger.step("Typed into placeholder-located field", true);
    }

    // ── getByTestId ──────────────────────────────────────────────────

    @Test(description = "getByTestId — default data-testid attribute locates the login button")
    public void testId_default_isVisible() {
        open();
        StepLogger.step("getByTestId(\"login-submit-btn\")");
        assertTrue(getByTestId("login-submit-btn").isVisible(),
                "Element with data-testid='login-submit-btn' should be visible");
        StepLogger.step("Located by data-testid", true);
    }

    @Test(description = "getByTestId — toBy() escape hatch returns a usable Selenium By")
    public void testId_toBy_escapeHatch() {
        open();
        StepLogger.step("getByTestId(...).toBy() → driver.findElement");
        var by = getByTestId("username-input").toBy();
        assertNotNull(getDriver().findElement(by),
                "toBy() should hand back a By usable with raw Selenium");
        StepLogger.step("toBy() interop works: " + by, true);
    }

    // ── getByTitle ───────────────────────────────────────────────────

    @Test(description = "getByTitle — locates the disabled button and confirms it is disabled")
    public void title_locatesDisabledButton() {
        open();
        StepLogger.step("getByTitle(\"currently disabled\")");
        assertTrue(getByTitle("currently disabled").isVisible(), "Disabled button should be visible");
        assertFalse(getByTitle("currently disabled").isEnabled(), "Button should report disabled");
        StepLogger.step("Located disabled button by title", true);
    }

    // ── web-first assertion on a semantic locator ────────────────────

    @Test(description = "assertThat(semantic locator) — web-first assertion integrates with getBy*")
    public void assertThat_onSemanticLocator() {
        open();
        StepLogger.step("assertThat(getByTestId(...)).isVisible()");
        assertThat(getByTestId("login-submit-btn")).isVisible();
        assertThat(getByRole(Role.HEADING).withLevel(1)).isVisible();
        StepLogger.step("Web-first assertions on semantic locators passed", true);
    }
}
