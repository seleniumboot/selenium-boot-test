package com.saddy.pages;

import com.seleniumboot.test.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {
    private static final By FIRST_NAME = By.id("firstName");
    private static final By EMAIL      = By.id("email");
    private static final By COUNTRY    = By.id("country");
    private static final By GENDER_MALE = By.id("male");
    private static final By CODING     = By.id("coding");
    private static final By BIO        = By.id("bio");
    private static final By REG_BTN    = By.xpath("//button[.='Register']");

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void fillRegistrationForm() {
        type(FIRST_NAME, "John");
        type(EMAIL, "jhon.doe@cp.com");
        selectByText(COUNTRY, "United States");
        click(GENDER_MALE);
        click(CODING);
        type(BIO, "Enthusiast coder");
        click(REG_BTN);
//        acceptAlert();
    }
}
