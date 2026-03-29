package com.saddy.tests;

import com.saddy.pages.ShadowDomPage;
import com.seleniumboot.test.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for the Shadow DOM section at https://panjatan.netlify.app/#shadow-dom
 *
 * Covers three shadow DOM examples on the page:
 *   1. Simple Shadow DOM   — text, button (alert), input
 *   2. Nested Shadow DOM   — two levels deep (L1 heading, L2 button/select via pierce)
 *   3. Shadow DOM Form     — fill name/email/message, submit, verify result
 */
public class ShadowDomDemoTest extends BaseTest {

    // @BeforeMethod cannot use getDriver() — driver is created by onTestStart which fires after
    // @BeforeMethod. Each test calls shadowPage() at the top instead.
    private ShadowDomPage shadowPage() {
        ShadowDomPage page = new ShadowDomPage(getDriver());
        page.navigateTo();
        return page;
    }

    // ── Simple Shadow DOM ─────────────────────────────────────────────────

    @Test(description = "Read heading text from inside a shadow root")
    public void simpleShadowHeadingIsVisible() {
        assertEquals(shadowPage().getSimpleHeading(), "Content inside Shadow DOM");
    }

    @Test(description = "Read paragraph text from inside a shadow root")
    public void simpleShadowParagraphText() {
        assertEquals(shadowPage().getSimpleParagraph(),
                "This content is encapsulated within a shadow DOM");
    }

    @Test(description = "Shadow button is present inside the shadow root")
    public void simpleShadowButtonExists() {
        assertTrue(shadowPage().isShadowButtonVisible());
    }

    @Test(description = "Click button inside shadow root — alert fires and text is captured")
    public void simpleShadowButtonClickTriggersAlert() {
        ShadowDomPage page = shadowPage();
        assertEquals(page.clickShadowButtonAndGetAlert(), "Button inside Shadow DOM clicked!");
    }

    @Test(description = "Type into an input inside a shadow root")
    public void typingInShadowInput() {
        ShadowDomPage page = shadowPage();
        page.typeInShadowInput("Shadow typing test");
        assertEquals(page.getShadowInputValue(), "Shadow typing test");
    }

    // ── Nested Shadow DOM ─────────────────────────────────────────────────

    @Test(description = "Read Level 1 shadow DOM heading")
    public void nestedL1HeadingIsVisible() {
        assertEquals(shadowPage().getNestedL1Heading(), "Level 1 Shadow DOM");
    }

    @Test(description = "Read Level 2 heading by piercing through two shadow roots")
    public void nestedL2HeadingViaPierce() {
        assertEquals(shadowPage().getNestedL2Heading(), "Level 2 Nested Shadow DOM");
    }

    @Test(description = "Read Level 2 paragraph by piercing through two shadow roots")
    public void nestedL2ParagraphViaPierce() {
        assertEquals(shadowPage().getNestedL2Paragraph(),
                "This is content inside a nested shadow DOM");
    }

    @Test(description = "Click button in Level 2 shadow root — alert fires and text is captured")
    public void nestedShadowButtonClickTriggersAlert() {
        ShadowDomPage page = shadowPage();
        assertEquals(page.clickNestedButtonAndGetAlert(), "Button inside Nested Shadow DOM clicked!");
    }

    @Test(description = "Select an option from a <select> inside a nested shadow root")
    public void selectOptionInNestedShadow() {
        ShadowDomPage page = shadowPage();
        page.selectNestedOption("Shadow Option 2");
        assertEquals(page.getSelectedNestedOption(), "Shadow Option 2");
    }

    // ── Shadow DOM Form ───────────────────────────────────────────────────

    @Test(description = "Shadow form heading is readable")
    public void shadowFormHeadingVisible() {
        assertEquals(shadowPage().getFormHeading(), "Form inside Shadow DOM");
    }

    @Test(description = "Fill and submit the form inside a shadow root — verify success message")
    public void shadowFormSubmitShowsSuccess() {
        ShadowDomPage page = shadowPage();
        page.fillAndSubmitForm("John Doe", "john@example.com", "Hello from shadow form");
        assertEquals(page.getFormResult(), "Shadow DOM form submitted successfully!");
    }
}
