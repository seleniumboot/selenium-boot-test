Feature: Retry Demo

  # Global retry from selenium-boot.yml applies to all scenarios
  Scenario: Flaky scenario passes on second attempt
    Given a step that fails on the first attempt
    Then the scenario should have retried successfully

  # Per-scenario retry — overrides global config
  @retryable=2
  Scenario: Scenario with its own retry count
    Given a step that fails on the first attempt
    Then the scenario should have retried successfully
