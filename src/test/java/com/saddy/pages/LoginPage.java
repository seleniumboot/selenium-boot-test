package com.saddy.pages;

import com.seleniumboot.test.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.xpath("//button[contains(text(), 'Login')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username).enterPassword(password).clickLoginButton();
    }
}
