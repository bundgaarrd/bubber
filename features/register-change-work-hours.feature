Feature: Register and change working hours
  Description: An employee can register and change their hours worked.
  Actors: Projectleader, Employee


  Scenario: Register working hours for activity
    Given I am logged in as an employee or project leader
    When I log that I have worked 5 hours
    Then 5 working hours are registered


  Scenario: Registering invalid hours
    Given I am logged in as an employee or project leader
    When I register negative hours
    Then an error message is sent
    And the hours are not logged