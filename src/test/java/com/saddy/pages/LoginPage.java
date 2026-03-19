package com.saddy.pages;

import com.seleniumboot.test.BasePage;
import com.seleniumboot.test.SmartLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final By USERNAME   = By.id("username");
    private static final By PASSWORD   = By.id("password");

    // SmartLocator fallback chain — tries each strategy in order
    private static final By LOGIN_BTN_PRIMARY  = By.id("login-btn");
    private static final By LOGIN_BTN_CSS      = By.cssSelector("button[type='submit']");
    private static final By LOGIN_BTN_XPATH    = By.xpath("//button[contains(text(), 'Login')]");

    public boolean isLoginButtonVisible() {
        return SmartLocator.isAnyVisible(driver, LOGIN_BTN_PRIMARY, LOGIN_BTN_CSS, LOGIN_BTN_XPATH);
    }

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterUsername(String username) {
        type(USERNAME, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(PASSWORD, password);
        return this;
    }

    public void clickLoginButton() {
        smartFind(LOGIN_BTN_PRIMARY, LOGIN_BTN_CSS, LOGIN_BTN_XPATH).click();
    }

    public void login(String username, String password) {
        enterUsername(username).enterPassword(password).clickLoginButton();
    }
}
