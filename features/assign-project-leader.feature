Feature: Assign projectleader
  Description: A projectleader is assigned to a project
  Actors: Projectleader, Employee


  Scenario: Add projectleader
    Given I am logged in as an employee or a project leader with the initials "huba"
    And there is a project named "Hansen ApS" without a project leader
    When an employee is chosen to be the project leader for a project
    And the selected employee confirms the role of project leader
    Then the selected employee is added as the project leader for the project


  Scenario: Fail to add projectleader
    Given I am logged in as an employee or a project leader with the initials "huba"
    And there is a project named "Hansen ApS" without a project leader
    When an employee is chosen to be the project leader for a project
    And the selected employee does not confirm the role of project leader
    Then the selected employee is not added as the project leader for the project