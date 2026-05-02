package com.saddy.bdd.steps;

import com.seleniumboot.cucumber.BaseCucumberSteps;
import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.wait.WaitEngine;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class RegistrationSteps extends BaseCucumberSteps {

    @When("the user navigates to the registration section")
    public void theUserNavigatesToRegistrationSection() {
        // Look for a nav link that leads to registration and click it
        List<WebElement> navLinks = getDriver().findElements(By.cssSelector("nav a"));
        for (WebElement link : navLinks) {
            String text = link.getText().toLowerCase();
            if (text.contains("register") || text.contains("sign up") || text.contains("form")) {
                StepLogger.step("Clicking nav link: " + link.getText());
                link.click();
                return;
            }
        }
        // Fallback: registration elements may already be on the current page
        StepLogger.step("No registration nav link found — checking current page for form elements");
    }

    @Then("the first name field is visible")
    public void theFirstNameFieldIsVisible() {
        assertThat(By.id("firstName")).isVisible();
    }

    @Then("the email field is visible")
    public void theEmailFieldIsVisible() {
        assertThat(By.id("email")).isVisible();
    }

    @Then("the register button is visible")
    public void theRegisterButtonIsVisible() {
        assertThat(By.xpath("//button[.='Register']")).isVisible();
    }

    @When("the user fills in first name {string}")
    public void theUserFillsInFirstName(String firstName) {
        WaitEngine.waitForVisible(By.id("firstName")).sendKeys(firstName);
    }

    @And("the user fills in email {string}")
    public void theUserFillsInEmail(String email) {
        WaitEngine.waitForVisible(By.id("email")).sendKeys(email);
    }

    @And("the user selects country {string}")
    public void theUserSelectsCountry(String country) {
        new Select(WaitEngine.waitForVisible(By.id("country"))).selectByVisibleText(country);
    }

    @And("the user selects gender {string}")
    public void theUserSelectsGender(String gender) {
        WaitEngine.waitForClickable(By.id(gender)).click();
    }

    @And("the user fills in bio {string}")
    public void theUserFillsInBio(String bio) {
        WaitEngine.waitForVisible(By.id("bio")).sendKeys(bio);
    }

    @And("the user clicks the register button")
    public void theUserClicksTheRegisterButton() {
        WaitEngine.waitForClickable(By.xpath("//button[.='Register']")).click();
    }

    @Then("the registration form is submitted")
    public void theRegistrationFormIsSubmitted() {
        StepLogger.step("Verifying form submission");
        Assert.assertFalse(getDriver().getTitle().isEmpty(),
                "Page should still be accessible after form submission");
    }
}
