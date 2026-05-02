package com.saddy.bdd.steps;

import com.saddy.pages.LoginPage;
import com.seleniumboot.steps.StepLogger;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.seleniumboot.cucumber.BaseCucumberSteps;

public class LoginSteps extends BaseCucumberSteps {

    private LoginPage loginPage;

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        open();
        loginPage = new LoginPage(getDriver());
    }

    @Then("the username field is visible")
    public void theUsernameFieldIsVisible() {
        assertThat(By.id("username")).isVisible();
    }

    @Then("the password field is visible")
    public void thePasswordFieldIsVisible() {
        assertThat(By.id("password")).isVisible();
    }

    @Then("the login button is visible")
    public void theLoginButtonIsVisible() {
        assertThat(By.cssSelector("button[type='submit']")).isVisible();
    }

    @When("the user enters username {string} and password {string}")
    public void theUserEntersCredentials(String username, String password) {
        StepLogger.step("Entering credentials for: " + username);
        loginPage.enterUsername(username).enterPassword(password);
    }

    @And("the user clicks the login button")
    public void theUserClicksTheLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("the user should be logged in successfully")
    public void theUserShouldBeLoggedInSuccessfully() {
        String title = getDriver().getTitle();
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty after login");
        StepLogger.step("Login successful — page title: " + title);
    }
}
