Feature: User Login As a registered user I want to log into the application So that I can access the dashboard

  Scenario: Login page displays all required fields
    Given the user is on the login page
    Then the username field is visible
    And the password field is visible
    And the login button is visible

  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user enters username "admin" and password "password"
    And the user clicks the login button
    Then the user should be logged in successfully

  Scenario Outline: Login with multiple valid accounts
    Given the user is on the login page
    When the user enters username "<username>" and password "<password>"
    And the user clicks the login button
    Then the user should be logged in successfully

    Examples:
      | username | password |
      | admin    | password |
      | user1    | pass123  |
