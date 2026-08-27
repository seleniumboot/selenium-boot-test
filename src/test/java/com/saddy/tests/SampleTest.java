package com.saddy.tests;

import com.seleniumboot.locator.Role;
import com.seleniumboot.test.BaseTest;
import org.testng.annotations.Test;

/**
 * The first test most people read. It is deliberately the smallest useful example of
 * what Selenium Boot actually gives you over plain Selenium:
 *
 * <ul>
 *   <li>no driver setup or teardown — {@code BaseTest} owns the lifecycle</li>
 *   <li>no {@code WebDriverWait} — {@code assertThat(...)} retries until the timeout</li>
 *   <li>no CSS selectors or XPath — elements are found by label, role and test id,
 *       the way a user or a screen reader finds them</li>
 * </ul>
 *
 * <p>Raw Selenium is never hidden: {@code getDriver()} is always there when you need it.
 * See {@code LocatorApiDemoTest} for the full locator surface.
 */
public class SampleTest extends BaseTest {

    @Test(description = "Find and fill a form without a single CSS selector")
    public void signsInUsingAccessibleLocators() {
        open();

        getByLabel("Username").type("admin");
        getByLabel("Password").type("password");

        assertThat(getByLabel("Username")).hasValue("admin");
        assertThat(getByRole(Role.BUTTON, "Login")).isVisible();
    }

    @Test(description = "Locate by test id — survives any CSS or DOM refactor")
    public void findsElementsByTestId() {
        open();

        assertThat(getByTestId("login-submit-btn")).isVisible();
        assertThat(getByTestId("remember-checkbox")).isVisible();
    }
}
