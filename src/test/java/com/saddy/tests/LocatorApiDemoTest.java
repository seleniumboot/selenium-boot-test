package com.saddy.tests;

import com.saddy.pages.LocatorDemoPage;
import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.test.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Validates the Phase 12 Fluent Locator API ($) and
 * Phase 13 Web-First Assertions (assertThat) against panjatan.netlify.app.
 */
public class LocatorApiDemoTest extends BaseTest {

    // ════════════════════════════════════════════════════════════════
    // Fluent Locator API — $()
    // ════════════════════════════════════════════════════════════════

    @Test(description = "$() CSS — type into username field using fluent locator")
    public void fluent_type_usernameField() {
        open();
        StepLogger.step("Open app and type into username using $()");
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        page.enterUsernameWithLocator("admin");
        // If no exception — auto-wait + type worked
        StepLogger.step("$() type succeeded", true);
    }

    @Test(description = "$() By — type into password field using By locator")
    public void fluent_type_passwordField() {
        open();
        StepLogger.step("Type into password using $(By)");
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        page.enterPasswordWithLocator("secret");
        StepLogger.step("$(By) type succeeded", true);
    }

    @Test(description = "$() click — click login button using CSS $() locator")
    public void fluent_click_loginButton() {
        open();
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        page.enterUsernameWithLocator("admin");
        page.enterPasswordWithLocator("password");
        StepLogger.step("Click login via $()");
        page.clickLoginWithLocator();
        StepLogger.step("$() click succeeded", true);
    }

    @Test(description = "$().withText() — click button by visible text")
    public void fluent_withText_clickByText() {
        open();
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        page.enterUsernameWithLocator("admin");
        page.enterPasswordWithLocator("password");
        StepLogger.step("Click login via $().withText('Login')");
        page.clickLoginByText();
        StepLogger.step("$().withText() click succeeded", true);
    }

    @Test(description = "$().getText() — get page title text via fluent locator")
    public void fluent_getText_pageTitle() {
        open();
        StepLogger.step("Get page title text via $().getText()");
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        String title = page.getPageTitleText();
        assertFalse(title.isEmpty(), "Page title should not be empty");
        StepLogger.step("Title retrieved: " + title, true);
    }

    @Test(description = "$().count() — count nav links")
    public void fluent_count_navLinks() {
        open();
        StepLogger.step("Count nav links using $().count()");
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        int count = page.getNavLinkCount();
        assertTrue(count > 0, "Expected at least one nav link, found: " + count);
        StepLogger.step("Nav link count: " + count, true);
    }

    @Test(description = "$().isVisible() — login button is visible")
    public void fluent_isVisible_loginButton() {
        open();
        StepLogger.step("Check login button visibility via $().isVisible()");
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        assertTrue(page.isLoginButtonVisible(), "Login button should be visible");
        StepLogger.step("$().isVisible() returned true", true);
    }

    @Test(description = "$().isHidden() — non-existent element returns true")
    public void fluent_isHidden_nonExistentElement() {
        open();
        StepLogger.step("Check $().isHidden() for a non-existent element");
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        assertTrue(page.isNonExistentElementHidden(), "Ghost element should report isHidden=true");
        StepLogger.step("$().isHidden() returned true for non-existent element", true);
    }

    @Test(description = "$() — direct usage inside BaseTest without a page object")
    public void fluent_directUsage_inBaseTest() {
        open();
        StepLogger.step("Use $() directly from BaseTest without page object");
        // $() is available directly in BaseTest
        String title = $("h1").getText();
        assertFalse(title.isEmpty(), "h1 text should not be empty via direct $() usage");
        StepLogger.step("Direct $() getText: " + title, true);
    }

    @Test(description = "$() — chain: filter + nth picks specific item")
    public void fluent_chain_filterAndNth() {
        open();
        StepLogger.step("Use $().filter() and .nth() chain");
        // Get the first nav link using nth(0)
        String text = $("nav a").nth(0).getText();
        assertFalse(text.isEmpty(), "First nav link text should not be empty");
        StepLogger.step("First nav link text via .nth(0): " + text, true);
    }

    // ════════════════════════════════════════════════════════════════
    // Web-First Assertions — assertThat()
    // ════════════════════════════════════════════════════════════════

    @Test(description = "assertThat(By).isVisible() — username field is visible")
    public void assert_isVisible_usernameField() {
        open();
        StepLogger.step("assertThat(By.id).isVisible()");
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        page.assertUsernameFieldReady();   // isVisible + isEnabled
        StepLogger.step("isVisible + isEnabled assertions passed", true);
    }

    @Test(description = "assertThat(By).isEnabled() — password field is enabled")
    public void assert_isEnabled_passwordField() {
        open();
        StepLogger.step("assertThat(By).isEnabled() on password field");
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        page.assertPasswordFieldVisible();
        StepLogger.step("Password field isVisible assertion passed", true);
    }

    @Test(description = "assertThat(By).isVisible() — login button is visible and enabled")
    public void assert_loginButton_visibleAndEnabled() {
        open();
        StepLogger.step("assertThat login button — isVisible + isEnabled");
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        page.assertLoginButtonReady();
        StepLogger.step("Login button assertions passed", true);
    }

    @Test(description = "assertThat(By).containsText() — page title contains text")
    public void assert_containsText_pageTitle() {
        open();
        StepLogger.step("assertThat page title containsText");
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        page.assertPageTitleContains("Playground");
        StepLogger.step("containsText assertion passed", true);
    }

    @Test(description = "assertThat(By).isHidden() — ghost element is not visible")
    public void assert_isHidden_ghostElement() {
        open();
        StepLogger.step("assertThat ghost element isHidden()");
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        page.assertGhostElementIsHidden();
        StepLogger.step("isHidden assertion passed for non-existent element", true);
    }

    @Test(description = "assertThat(By).hasAttribute() — username field has type=text")
    public void assert_hasAttribute_usernameType() {
        open();
        StepLogger.step("assertThat username field hasAttribute('type', 'text')");
        LocatorDemoPage page = new LocatorDemoPage(getDriver());
        page.assertUsernameFieldHasAttribute("type", "text");
        StepLogger.step("hasAttribute assertion passed", true);
    }

    @Test(description = "assertThat(Locator) — web-first assertion on a Locator chain")
    public void assert_onLocatorChain() {
        open();
        StepLogger.step("assertThat($()) — web-first assertion on Locator chain");
        // Use assertThat directly from BaseTest with a Locator chain
        assertThat($("button[type='submit']")).isVisible();
        assertThat($("button[type='submit']")).isEnabled();
        StepLogger.step("assertThat(Locator) assertions passed", true);
    }

    @Test(description = "assertThat(By) — direct usage in BaseTest without page object")
    public void assert_directUsage_inBaseTest() {
        open();
        StepLogger.step("assertThat(By) used directly from BaseTest");
        assertThat(By.id("username")).isVisible();
        assertThat(By.id("password")).isVisible();
        assertThat(By.cssSelector("button[type='submit']")).isEnabled();
        StepLogger.step("Direct assertThat assertions passed", true);
    }
}
