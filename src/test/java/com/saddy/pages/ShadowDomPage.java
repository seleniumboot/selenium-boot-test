package com.saddy.pages;

import com.seleniumboot.test.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Page object for the Shadow DOM section of https://panjatan.netlify.app/#shadow-dom
 *
 * Shadow DOM structure on the page:
 *
 * #simpleShadowHost
 *   └── shadow-root
 *         ├── h4  "Content inside Shadow DOM"
 *         ├── p   "This content is encapsulated within a shadow DOM"
 *         ├── button#shadowBtn1  "Shadow Button"  → alert on click
 *         └── input[placeholder="Shadow input"]
 *
 * #nestedShadowHost
 *   └── shadow-root (L1)
 *         ├── h4  "Level 1 Shadow DOM"
 *         └── #nestedShadowHost2
 *               └── shadow-root (L2)
 *                     ├── h5  "Level 2 Nested Shadow DOM"
 *                     ├── p   "This is content inside a nested shadow DOM"
 *                     ├── button#nestedBtn  → alert on click
 *                     └── select  (Shadow Option 1/2/3)
 *
 * #shadowFormHost
 *   └── shadow-root
 *         ├── form#shadowForm
 *         │     ├── input#shadowName
 *         │     ├── input#shadowEmail
 *         │     ├── textarea#shadowMessage
 *         │     └── button.shadow-submit
 *         └── div#shadowResult  ← success message after submit
 */
public class ShadowDomPage extends BasePage {

    // ── Host locators ─────────────────────────────────────────────────────
    private static final By SIMPLE_HOST  = By.id("simpleShadowHost");
    private static final By NESTED_HOST  = By.id("nestedShadowHost");
    private static final By FORM_HOST    = By.id("shadowFormHost");

    public ShadowDomPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        driver.get("https://panjatan.netlify.app/#shadow-dom");
        // Wait for the shadow DOM to be fully initialised — the button inside
        // simpleShadowHost is a reliable readiness signal
        waitForShadowReady();
    }

    private void waitForShadowReady() {
        // Waits until the shadow button is present in the DOM — confirms setupShadowDOM() has run
        com.seleniumboot.wait.WaitEngine.wait(d ->
            (Boolean) ((JavascriptExecutor) d).executeScript(
                "var h = document.getElementById('simpleShadowHost');" +
                "return !!(h && h.shadowRoot && h.shadowRoot.getElementById('shadowBtn1'));"
            )
        );
    }

    // ── Simple Shadow DOM ─────────────────────────────────────────────────

    public String getSimpleHeading() {
        return shadowFind(SIMPLE_HOST, "h4").getText();
    }

    public String getSimpleParagraph() {
        return shadowFind(SIMPLE_HOST, "p").getText();
    }

    public void clickShadowButton() {
        shadowClick(SIMPLE_HOST, "#shadowBtn1");
    }

    public String getShadowButtonResult() {
        return shadowFind(SIMPLE_HOST, "#shadowBtnResult").getText();
    }

    public void typeInShadowInput(String text) {
        shadowType(SIMPLE_HOST, "input[placeholder='Shadow input']", text);
    }

    public String getShadowInputValue() {
        return shadowFind(SIMPLE_HOST, "input[placeholder='Shadow input']")
                .getAttribute("value");
    }

    public boolean isShadowButtonVisible() {
        return shadowExists(SIMPLE_HOST, "#shadowBtn1");
    }

    // ── Nested Shadow DOM — Level 1 ───────────────────────────────────────

    public String getNestedL1Heading() {
        return shadowFind(NESTED_HOST, "h4").getText();
    }

    // ── Nested Shadow DOM — Level 2 (pierce through both shadow roots) ────

    public String getNestedL2Heading() {
        return shadowPierce("#nestedShadowHost", "#nestedShadowHost2", "h5").getText();
    }

    public String getNestedL2Paragraph() {
        return shadowPierce("#nestedShadowHost", "#nestedShadowHost2", "p").getText();
    }

    public void clickNestedButton() {
        shadowPierce("#nestedShadowHost", "#nestedShadowHost2", "#nestedBtn").click();
    }

    public String getNestedButtonResult() {
        return shadowPierce("#nestedShadowHost", "#nestedShadowHost2", "#nestedBtnResult").getText();
    }

    public void selectNestedOption(String visibleText) {
        WebElement selectEl = shadowPierce("#nestedShadowHost", "#nestedShadowHost2", "select");
        new Select(selectEl).selectByVisibleText(visibleText);
    }

    public String getSelectedNestedOption() {
        WebElement selectEl = shadowPierce("#nestedShadowHost", "#nestedShadowHost2", "select");
        return new Select(selectEl).getFirstSelectedOption().getText();
    }

    // ── Shadow DOM Form ───────────────────────────────────────────────────

    public void fillAndSubmitForm(String name, String email, String message) {
        shadowType(FORM_HOST, "#shadowName",    name);
        shadowType(FORM_HOST, "#shadowEmail",   email);
        shadowType(FORM_HOST, "#shadowMessage", message);
        shadowClick(FORM_HOST, ".shadow-submit");
    }

    public String getFormResult() {
        return shadowFind(FORM_HOST, "#shadowResult").getText();
    }

    public String getFormHeading() {
        return shadowFind(FORM_HOST, "h4").getText();
    }
}
