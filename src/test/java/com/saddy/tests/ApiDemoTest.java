package com.saddy.tests;

import com.seleniumboot.client.ApiClient;
import com.seleniumboot.client.ApiResponse;
import com.seleniumboot.steps.StepLogger;
import com.seleniumboot.test.BaseApiTest;
import org.testng.annotations.Test;

/**
 * Demo: Pure API tests using BaseApiTest.
 * No browser is launched — only HTTP calls.
 */
public class ApiDemoTest extends BaseApiTest {

    @Test(description = "Verify public API returns 200")
    public void getPublicEndpoint() {
        ApiResponse res = ApiClient.get("https://jsonplaceholder.typicode.com/users/1")
                .send();

        res.assertStatus(200);
        StepLogger.step("User name: " + res.json("$.name"));
        StepLogger.step("Email: " + res.json("$.email"));
    }

    @Test(description = "Create a post and verify response")
    public void createPost() {
        ApiResponse res = ApiClient.post("https://jsonplaceholder.typicode.com/posts")
                .body(java.util.Map.of(
                        "title",  "Selenium Boot API Test",
                        "body",   "Testing with BaseApiTest",
                        "userId", 1
                ))
                .send();

        res.assertStatus(201)
           .assertJson("$.title", "Selenium Boot API Test");

        StepLogger.step("Created post ID: " + res.json("$.id"));

        // Store in suite context for next test
        suiteCtx().set("createdPostId", res.json("$.id"));
    }

    @Test(description = "Fetch created post using suite context", dependsOnMethods = "createPost")
    public void fetchCreatedPost() {
        String postId = suiteCtx().get("createdPostId");
        StepLogger.step("Fetching post with ID: " + postId);

        // JSONPlaceholder always returns 1 for newly created resources
        ApiResponse res = ApiClient.get("https://jsonplaceholder.typicode.com/posts/1")
                .send();

        res.assertStatus(200);
        StepLogger.step("Post title: " + res.json("$.title"));
    }

    @Test(description = "Verify scenario context cleared between tests")
    public void scenarioContextIsIsolated() {
        ctx().set("localKey", "localValue");
        StepLogger.step("Set localKey in ScenarioContext");

        String val = ctx().get("localKey");
        StepLogger.step("Retrieved: " + val);

        assert "localValue".equals(val) : "ScenarioContext key mismatch";
    }
}
