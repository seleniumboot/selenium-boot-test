package com.saddy.bdd.steps;

import com.seleniumboot.cucumber.BaseCucumberSteps;
import com.seleniumboot.wait.WaitEngine;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.testng.Assert;

import static com.seleniumboot.assertion.SeleniumAssert.assertThat;
import static com.seleniumboot.driver.DriverManager.getDriver;

public class NavigationSteps extends BaseCucumberSteps {

    @Given("the user opens the application")
    public void theUserOpensTheApplication() {
        open();
    }

    @Then("the page title is not empty")
    public void thePageTitleIsNotEmpty() {
        String title = getDriver().getTitle();
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty");
    }

    @And("the login form is present on the page")
    public void theLoginFormIsPresentOnThePage() {
        assertThat(By.id("username")).isVisible();
        assertThat(By.id("password")).isVisible();
    }

    @When("the user types {string} into the username field")
    public void theUserTypesIntoUsernameField(String text) {
        WaitEngine.waitForVisible(By.id("username")).clear();
        WaitEngine.waitForVisible(By.id("username")).sendKeys(text);
    }

    @Then("the username field contains {string}")
    public void theUsernameFieldContains(String expected) {
        String actual = WaitEngine.waitForVisible(By.id("username")).getAttribute("value");
        Assert.assertEquals(actual, expected, "Username field value mismatch");
    }

    @Then("the page has a visible heading")
    public void thePageHasAVisibleHeading() {
        assertThat(By.tagName("h1")).isVisible();
    }
}
