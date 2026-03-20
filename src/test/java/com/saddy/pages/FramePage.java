package com.saddy.pages;

import com.seleniumboot.test.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FramePage extends BasePage {
    public FramePage(WebDriver driver) {
        super(driver);
    }

    private static final By IFRAME      = By.id("testIframe");
    private static final By FRAME_TEXT  = By.xpath("//h2[.='Iframe Content']");

    public String getFrameText() {
        String[] result = {null};
        withinFrame(IFRAME, () -> result[0] = getText(FRAME_TEXT));
        return result[0];
    }
}
