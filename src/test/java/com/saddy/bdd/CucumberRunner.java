package com.saddy.bdd;

import com.seleniumboot.cucumber.BaseCucumberTest;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue     = {"com.saddy.bdd.steps", "com.seleniumboot.cucumber"},
        plugin   = {
                "pretty",
                "com.seleniumboot.cucumber.CucumberStepLogger",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true
)
public class CucumberRunner extends BaseCucumberTest {
}
