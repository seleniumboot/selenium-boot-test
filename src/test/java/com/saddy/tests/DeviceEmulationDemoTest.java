package com.saddy.tests;

import com.seleniumboot.browser.DeviceProfiles;
import com.seleniumboot.test.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Demonstrates DeviceEmulator — applying mobile/tablet device profiles
 * to the current browser session via CDP (Chrome) or window-resize fallback (Firefox).
 */
public class DeviceEmulationDemoTest extends BaseTest {

    private static final String URL = "https://testpages.eviltester.com/styled/basic-web-page-test.html";

    @Test(description = "Verify built-in device profiles are registered")
    public void builtInProfilesAvailable() {
        Assert.assertTrue(DeviceProfiles.contains("iPhone 14"),   "iPhone 14 should be registered");
        Assert.assertTrue(DeviceProfiles.contains("Pixel 7"),     "Pixel 7 should be registered");
        Assert.assertTrue(DeviceProfiles.contains("iPad"),        "iPad should be registered");
        Assert.assertTrue(DeviceProfiles.contains("Galaxy S23"),  "Galaxy S23 should be registered");
        Assert.assertTrue(DeviceProfiles.contains("iPad Pro 12"), "iPad Pro 12 should be registered");
        Assert.assertTrue(DeviceProfiles.contains("iPhone SE"),   "iPhone SE should be registered");
    }

    @Test(description = "Emulate iPhone 14 — page should load at 390px wide")
    public void emulateIPhone14() {
        emulateDevice("iPhone 14");
        getDriver().get(URL);
        long width = (Long) ((org.openqa.selenium.JavascriptExecutor) getDriver())
                .executeScript("return window.innerWidth;");
        Assert.assertEquals(width, 390L, "innerWidth should match iPhone 14 profile (390px)");
        resetDevice();
    }

    @Test(description = "Emulate Pixel 7 — verify viewport width")
    public void emulatePixel7() {
        emulateDevice("Pixel 7");
        getDriver().get(URL);
        long width = (Long) ((org.openqa.selenium.JavascriptExecutor) getDriver())
                .executeScript("return window.innerWidth;");
        Assert.assertEquals(width, 412L, "innerWidth should match Pixel 7 profile (412px)");
        resetDevice();
    }

    @Test(description = "Emulate iPad — verify viewport width")
    public void emulateIpad() {
        emulateDevice("iPad");
        getDriver().get(URL);
        long width = (Long) ((org.openqa.selenium.JavascriptExecutor) getDriver())
                .executeScript("return window.innerWidth;");
        Assert.assertEquals(width, 810L, "innerWidth should match iPad profile (810px)");
        resetDevice();
    }

    @Test(description = "Reset device emulation — returns to desktop viewport")
    public void resetEmulation() {
        emulateDevice("iPhone 14");
        resetDevice();
        getDriver().get(URL);
        long width = (Long) ((org.openqa.selenium.JavascriptExecutor) getDriver())
                .executeScript("return window.innerWidth;");
        // After reset, desktop viewport should be significantly wider than 390px
        Assert.assertTrue(width > 600, "After reset, innerWidth should be wider than any mobile viewport");
    }
}
