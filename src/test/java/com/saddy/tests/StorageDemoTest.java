package com.saddy.tests;

import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.test.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Validates Phase 14 — browser storage helpers (localStorage, sessionStorage, cookies)
 * and clipboard against https://panjatan.netlify.app
 */
public class StorageDemoTest extends BaseTest {

    // ════════════════════════════════════════════════════════════════
    // localStorage
    // ════════════════════════════════════════════════════════════════

    @Test(description = "localStorage.set + get round-trip")
    public void localStorage_setAndGet() {
        open();
        StepLogger.step("Set and retrieve a localStorage value");
        localStorage().set("sb_test_key", "hello-world");
        String value = localStorage().get("sb_test_key");
        assertEquals(value, "hello-world", "localStorage round-trip failed");
        StepLogger.step("localStorage get: " + value, true);
    }

    @Test(description = "localStorage.remove deletes a key")
    public void localStorage_remove() {
        open();
        localStorage().set("sb_remove_key", "temp");
        localStorage().remove("sb_remove_key");
        assertNull(localStorage().get("sb_remove_key"),
                "Key should be null after remove");
        StepLogger.step("localStorage.remove verified", true);
    }

    @Test(description = "localStorage.clear wipes all framework-set keys")
    public void localStorage_clear() {
        open();
        localStorage().set("sb_a", "1");
        localStorage().set("sb_b", "2");
        localStorage().clear();
        assertNull(localStorage().get("sb_a"), "sb_a should be gone after clear");
        assertNull(localStorage().get("sb_b"), "sb_b should be gone after clear");
        StepLogger.step("localStorage.clear verified", true);
    }

    @Test(description = "localStorage.size returns correct count after sets")
    public void localStorage_size() {
        open();
        localStorage().clear();
        localStorage().set("sb_x", "1");
        localStorage().set("sb_y", "2");
        int size = localStorage().size();
        assertTrue(size >= 2, "Expected at least 2 keys, got: " + size);
        StepLogger.step("localStorage.size: " + size, true);
    }

    @Test(description = "localStorage.keys returns key list")
    public void localStorage_keys() {
        open();
        localStorage().clear();
        localStorage().set("sb_key1", "v1");
        localStorage().set("sb_key2", "v2");
        assertTrue(localStorage().keys().contains("sb_key1"),
                "keys() should contain sb_key1");
        StepLogger.step("localStorage.keys verified", true);
    }

    @Test(description = "localStorage.get returns null for non-existent key")
    public void localStorage_getNonExistent() {
        open();
        assertNull(localStorage().get("sb_nonexistent_xyz_key"),
                "Non-existent key should return null");
        StepLogger.step("localStorage null-return verified", true);
    }

    // ════════════════════════════════════════════════════════════════
    // sessionStorage
    // ════════════════════════════════════════════════════════════════

    @Test(description = "sessionStorage.set + get round-trip")
    public void sessionStorage_setAndGet() {
        open();
        StepLogger.step("Set and retrieve a sessionStorage value");
        sessionStorage().set("sb_session_key", "session-value");
        String value = sessionStorage().get("sb_session_key");
        assertEquals(value, "session-value", "sessionStorage round-trip failed");
        StepLogger.step("sessionStorage get: " + value, true);
    }

    @Test(description = "sessionStorage.remove deletes a key")
    public void sessionStorage_remove() {
        open();
        sessionStorage().set("sb_sess_remove", "temp");
        sessionStorage().remove("sb_sess_remove");
        assertNull(sessionStorage().get("sb_sess_remove"),
                "Key should be null after sessionStorage.remove");
        StepLogger.step("sessionStorage.remove verified", true);
    }

    @Test(description = "sessionStorage.clear wipes entries")
    public void sessionStorage_clear() {
        open();
        sessionStorage().set("sb_s1", "val1");
        sessionStorage().set("sb_s2", "val2");
        sessionStorage().clear();
        assertNull(sessionStorage().get("sb_s1"), "sb_s1 should be gone after clear");
        StepLogger.step("sessionStorage.clear verified", true);
    }

    @Test(description = "sessionStorage.size reflects correct entry count")
    public void sessionStorage_size() {
        open();
        sessionStorage().clear();
        sessionStorage().set("sb_sa", "1");
        sessionStorage().set("sb_sb", "2");
        int size = sessionStorage().size();
        assertTrue(size >= 2, "Expected at least 2 sessionStorage keys, got: " + size);
        StepLogger.step("sessionStorage.size: " + size, true);
    }

    // ════════════════════════════════════════════════════════════════
    // Cookies
    // ════════════════════════════════════════════════════════════════

    @Test(description = "cookies.set + get round-trip")
    public void cookies_setAndGet() {
        open();
        StepLogger.step("Set and retrieve a cookie");
        cookies().set("sb_cookie", "cookie-value");
        String value = cookies().get("sb_cookie");
        assertEquals(value, "cookie-value", "Cookie round-trip failed");
        StepLogger.step("cookies.get: " + value, true);
    }

    @Test(description = "cookies.exists returns true for set cookie, false for unknown")
    public void cookies_exists() {
        open();
        cookies().set("sb_exists_cookie", "yes");
        assertTrue(cookies().exists("sb_exists_cookie"),
                "exists() should return true for a set cookie");
        assertFalse(cookies().exists("sb_totally_unknown_cookie_xyz"),
                "exists() should return false for unknown cookie");
        StepLogger.step("cookies.exists verified", true);
    }

    @Test(description = "cookies.delete removes a specific cookie")
    public void cookies_delete() {
        open();
        cookies().set("sb_del_cookie", "to-delete");
        cookies().delete("sb_del_cookie");
        assertNull(cookies().get("sb_del_cookie"),
                "Cookie should be null after delete");
        StepLogger.step("cookies.delete verified", true);
    }

    @Test(description = "cookies.deleteAll clears all cookies")
    public void cookies_deleteAll() {
        open();
        cookies().set("sb_c1", "v1");
        cookies().set("sb_c2", "v2");
        cookies().deleteAll();
        assertNull(cookies().get("sb_c1"), "sb_c1 should be gone after deleteAll");
        assertNull(cookies().get("sb_c2"), "sb_c2 should be gone after deleteAll");
        StepLogger.step("cookies.deleteAll verified", true);
    }

    @Test(description = "cookies.getAll returns a non-null set")
    public void cookies_getAll() {
        open();
        cookies().set("sb_all_cookie", "all-value");
        assertNotNull(cookies().getAll(), "getAll() should never return null");
        StepLogger.step("cookies.getAll returned " + cookies().getAll().size() + " cookies", true);
    }

    // ════════════════════════════════════════════════════════════════
    // Clipboard
    // ════════════════════════════════════════════════════════════════

    @Test(description = "clipboard.write + read round-trip via JS store")
    public void clipboard_writeAndRead() {
        open();
        StepLogger.step("Write and read clipboard value");
        clipboard().write("selenium-boot-clipboard-test");
        String value = clipboard().read();
        assertEquals(value, "selenium-boot-clipboard-test",
                "Clipboard read should return written value");
        StepLogger.step("clipboard read: " + value, true);
    }

    @Test(description = "clipboard.clear nullifies stored value")
    public void clipboard_clear() {
        open();
        clipboard().write("to-be-cleared");
        clipboard().clear();
        assertNull(clipboard().read(), "Clipboard should be null after clear");
        StepLogger.step("clipboard.clear verified", true);
    }
}
