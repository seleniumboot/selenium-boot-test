package com.saddy.conditions;

import com.saddy.pages.DashboardPage;
import com.saddy.pages.LoginPage;
import com.saddy.pages.RegistrationPage;
import com.seleniumboot.precondition.BaseConditions;
import com.seleniumboot.precondition.ConditionProvider;

public class AppConditions extends BaseConditions {

    @ConditionProvider("loginAsAdmin")
    public void loginAsAdmin() {
        open("/");
        new LoginPage(getDriver()).login("admin", "password");
    }

    @ConditionProvider("loginAsUser")
    public void loginAsUser() {
        open("/");
        new LoginPage(getDriver()).login("user", "password");
    }

    @ConditionProvider("fillRegistrationForm")
    public void fillRegistrationForm() {
        open("/");
        new RegistrationPage(getDriver()).fillRegistrationForm();
    }
    @ConditionProvider("dashboardTitle")
    public void dashboardTitle() {
        open("/");
        String title = new DashboardPage(getDriver()).getDashboardTitle();
        System.out.println(title);
    }
}
