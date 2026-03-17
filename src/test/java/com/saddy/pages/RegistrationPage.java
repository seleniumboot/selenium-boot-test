package com.saddy.pages;

import com.seleniumboot.test.BasePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends BasePage {
    By firstName = By.id("firstName");
    By email = By.id("email");
    By country = By.id("country");
    By genderMale = By.id("male");
    By coding = By.id("coding");
    By bio = By.id("bio");
    By regBtn = By.xpath("//button[.='Register']");

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void fillRegistrationForm() {
        type(firstName, "John");
        type(email, "jhon.doe@cp.com");

        Select country = new Select(driver.findElement(By.id("country")));
        country.selectByVisibleText("United States");

        click(genderMale);
        click(coding);
        type(bio, "Enthusiast coder");

        click(regBtn);
        acceptRegAlert();
    }

    public void acceptRegAlert() {
        driver.switchTo().alert().accept();
    }
    public String getAlertText() {
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
}
