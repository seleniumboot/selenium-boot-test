Feature: Application Navigation
  As a user
  I want to navigate through the application
  So that I can access different sections

  Scenario: Home page loads with login form
    Given the user opens the application
    Then the page title is not empty
    And the login form is present on the page

  Scenario: Username and password fields are interactive
    Given the user opens the application
    When the user types "testuser" into the username field
    Then the username field contains "testuser"

  Scenario: Page heading is displayed
    Given the user opens the application
    Then the page has a visible heading
