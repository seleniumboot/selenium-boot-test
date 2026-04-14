package com.saddy.tests;

import com.seleniumboot.test.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Demonstrates the video recording on failure feature.
 *
 * When recording.enabled=true in selenium-boot.yml:
 *   - Passing tests → frames are discarded, no GIF saved
 *   - Failing tests → animated GIF saved to target/recordings/
 */
public class RecordingDemoTest extends BaseTest {

    @Test(description = "Passing test — recording is discarded, no GIF produced")
    public void recordingPassTest() {
        getDriver().get("https://google.com");
        assertEquals(getDriver().getTitle(), "Google");
    }

    /*
    @Test(description = "Failing test — animated GIF captured and saved to target/recordings/")
    public void recordingFailTest() throws InterruptedException {
        getDriver().get("https://msn.com");
        // Intentional failure to trigger recording save
        assertEquals(getDriver().getTitle(), "Google",
                "This test is intentionally failing to demonstrate GIF recording");
    }
*/
}
