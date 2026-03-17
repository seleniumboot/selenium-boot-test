package com.saddy.pages;

import com.seleniumboot.test.BasePage;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public String getDashboardTitle() {
        return driver.getTitle();
    }
}
