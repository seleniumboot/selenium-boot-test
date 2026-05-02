Feature: User Registration
  As a new visitor
  I want to fill the registration form
  So that I can create an account

  Scenario: Registration form is accessible from the nav
    Given the user opens the application
    When the user navigates to the registration section
    Then the first name field is visible
    And the email field is visible
    And the register button is visible

  Scenario: User submits the registration form with valid details
    Given the user opens the application
    When the user navigates to the registration section
    And the user fills in first name "John"
    And the user fills in email "john.doe@example.com"
    And the user selects country "United States"
    And the user selects gender "male"
    And the user fills in bio "Enthusiast coder"
    And the user clicks the register button
    Then the registration form is submitted
