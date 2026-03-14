package com.saddy.tests;

import com.saddy.pages.LoginPage;
import com.seleniumboot.test.BaseTest;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test
    public void loginTest(){
        open();
        LoginPage loginPage = new LoginPage();

        loginPage.enterUsername("admin");
        loginPage.enterPassword("password");
        loginPage.clickLoginButton();
    }
}
