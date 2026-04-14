Feature: Delete activity
  Description: An activity is deleted from a project
  Actors: Projectleader, Employee


  Scenario: Delete activity
    Given I am logged in as project leader or employee
    And There is an activity named "Analysis" in this project
    When I delete the activity named "Analysis"
    Then the activity no longer exists in the project


  Scenario: Delete non-existent activity
    Given I am logged in as project leader or employee
    And There is no activity named "Analysis" in this project
    When I delete the activity named "Analysis"
    Then an error message is shown indicating that the activity does not exist in the project