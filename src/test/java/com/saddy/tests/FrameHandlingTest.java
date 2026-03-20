package com.saddy.tests;

import com.saddy.pages.FramePage;
import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FrameHandlingTest extends BaseTest {

    @Test(description = "Verify that text inside frame showing correct")
    public void verifyFrameText() {
        open("/");
        FramePage framePage = new FramePage(getDriver());
        String frameText = framePage.getFrameText();
        StepLogger.step("Text Fetched: "  + frameText);
        Assert.assertEquals(frameText, "Iframe Content");
        StepLogger.step("Verification of FrameText Success!");
    }
}
