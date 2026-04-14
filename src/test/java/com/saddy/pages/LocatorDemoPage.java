package com.saddy.pages;

import com.seleniumboot.locator.Locator;
import com.seleniumboot.test.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Demo page object showing Fluent Locator API ($) and Web-First Assertions (assertThat).
 * Tests against https://panjatan.netlify.app
 */
public class LocatorDemoPage extends BasePage {

    // -- Raw By locators (existing style) --
    private static final By USERNAME_FIELD  = By.id("username");
    private static final By PASSWORD_FIELD  = By.id("password");
    private static final By LOGIN_BUTTON    = By.cssSelector("button[type='submit']");
    private static final By NAV_LINKS       = By.cssSelector("nav a");
    private static final By PAGE_TITLE      = By.cssSelector("h1");

    public LocatorDemoPage(WebDriver driver) {
        super(driver);
    }

    // ── Using $() — Fluent Locator API ─────────────────────────────

    /** Type into the username field using $() */
    public void enterUsernameWithLocator(String username) {
        $(USERNAME_FIELD).type(username);
    }

    /** Type into the password field using $() */
    public void enterPasswordWithLocator(String password) {
        $(PASSWORD_FIELD).type(password);
    }

    /** Click the login button using $() */
    public void clickLoginWithLocator() {
        $("button[type='submit']").click();
    }

    /** Click the login button using withText() chain */
    public void clickLoginByText() {
        $("button").withText("Login").click();
    }

    /** Get page title text using $() */
    public String getPageTitleText() {
        return $(PAGE_TITLE).getText();
    }

    /** Returns count of nav links using $().count() */
    public int getNavLinkCount() {
        return $(NAV_LINKS).count();
    }

    /** Returns true if login button is visible */
    public boolean isLoginButtonVisible() {
        return $("button[type='submit']").isVisible();
    }

    /** Returns true if a non-existent element is hidden */
    public boolean isNonExistentElementHidden() {
        return $(".this-element-does-not-exist-at-all").isHidden();
    }

    // ── Using assertThat() — Web-First Assertions ──────────────────

    /** Asserts username field is visible and enabled */
    public void assertUsernameFieldReady() {
        assertThat(USERNAME_FIELD).isVisible();
        assertThat(USERNAME_FIELD).isEnabled();
    }

    /** Asserts password field is visible */
    public void assertPasswordFieldVisible() {
        assertThat(PASSWORD_FIELD).isVisible();
    }

    /** Asserts login button is visible and enabled */
    public void assertLoginButtonReady() {
        assertThat(LOGIN_BUTTON).isVisible();
        assertThat(LOGIN_BUTTON).isEnabled();
    }

    /** Asserts the page title contains a given text fragment */
    public void assertPageTitleContains(String fragment) {
        assertThat(PAGE_TITLE).containsText(fragment);
    }

    /** Asserts exact text of page heading */
    public void assertHeadingText(String expected) {
        assertThat(PAGE_TITLE).hasText(expected);
    }

    /** Asserts a non-existent element is hidden */
    public void assertGhostElementIsHidden() {
        assertThat(By.cssSelector(".ghost-element-xyz")).isHidden();
    }

    /** Asserts there is at least one nav link */
    public void assertNavLinksPresent(int expectedCount) {
        assertThat(NAV_LINKS).count(expectedCount);
    }

    // ── Mixed: $() chain + assertThat() together ───────────────────

    /** Use $() to check, then assertThat() to assert */
    public void assertUsernameFieldHasAttribute(String attr, String value) {
        assertThat(USERNAME_FIELD).hasAttribute(attr, value);
    }
}
