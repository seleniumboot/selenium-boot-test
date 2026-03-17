package com.saddy.tests;

import com.seleniumboot.precondition.PreCondition;
import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.steps.StepStatus;
import com.seleniumboot.test.BaseTest;
import org.testng.annotations.Test;

public class MultiConditionsDemoTest extends BaseTest {

    @Test(description = "Multi Condition Demo Test")
    @PreCondition({"fillRegistrationForm", "dashboardTitle"})
    public void multiConditionsDemoTest() {
        StepLogger.step("Multi Condition Demo Test");
        StepLogger.step("Completed - Multi Condition Demo Test", StepStatus.PASS);
    }

}
