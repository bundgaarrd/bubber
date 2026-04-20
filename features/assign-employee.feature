Feature: Assign employee to activity
  Description: An employee is assigned to an activity within a project
  Actors: Projectleader, Employee

  Scenario: Assign available employee
    Given I am logged in as an employee or a project leader
    And the selected employee is available
    When the selected employee is assigned to an activity
    Then the selected employee is added to the activity
    And the employee's schedule is updated

  Scenario: Assign unavailable employee
    Given I am logged in as an employee or a project leader
    And the selected employee is unavailable
    When the selected employee is assigned to an activity
    Then the selected employee is not added to the activity
    And the is an error message indicating the employee is unavailable