package com.saddy.tests;

import com.seleniumboot.client.ApiClient;
import com.seleniumboot.client.ApiResponse;
import com.seleniumboot.test.BaseApiTest;
import com.seleniumboot.steps.StepLogger;
import org.testng.annotations.Test;

/**
 * Demonstrates schema validation with ApiResponse.assertSchema().
 * Validates that the API response matches the JSON Schema defined in
 * src/test/resources/schemas/user.json
 */
public class ApiSchemaDemoTest extends BaseApiTest {

    @Test(description = "Response structure matches user schema")
    public void userResponseMatchesSchema() {
        StepLogger.step("Fetching user from JSONPlaceholder");

        ApiResponse res = ApiClient.get("https://jsonplaceholder.typicode.com/users/1")
                .send();

        StepLogger.step("Asserting status 200");
        res.assertStatus(200);

        StepLogger.step("Validating response against schemas/user.json");
        res.assertSchema("schemas/user.json");

        StepLogger.step("Schema validation passed — response structure is correct");
    }

    @Test(description = "All users in list match user schema")
    public void allUsersMatchSchema() {
        StepLogger.step("Fetching all users");

        ApiResponse res = ApiClient.get("https://jsonplaceholder.typicode.com/users")
                .send()
                .assertStatus(200);

        StepLogger.step("Fetched users list successfully");

        // Validate first user in the array matches schema
        StepLogger.step("Validating first user against schemas/user.json");
        ApiClient.get("https://jsonplaceholder.typicode.com/users/1")
                .send()
                .assertStatus(200)
                .assertSchema("schemas/user.json");

        StepLogger.step("Schema validation passed for user object");
    }
}
