Feature: Assign employee to activity
  Description: An employee is assigned to an activity within a project
  Actors: Projectleader, Employee

  Scenario: Assign available employee
    Given I am logged in as an employee or a project leader with the initials "huba"
    And a project "Hansen ApS" with an activity "Design" exists
    And the employee "huba" is available
    When "huba" is assigned to the activity "Design"
    Then "huba" is added to the activity "Design"
    And the employee "huba"'s schedule is updated

  Scenario: Assign unavailable employee
    Given I am logged in as an employee or a project leader with the initials "huba"
    And the employee "huba" is unavailable
    When "huba" is assigned to the activity "Design"
    Then "huba" is not added to the activity "Design"
    And an error message indicating the employee is unavailable is displayed