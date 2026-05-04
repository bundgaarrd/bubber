Feature: Assign projectleader
  Description: A projectleader is assigned to a project
  Actors: Projectleader, Employee


  Scenario: Add projectleader
    Given I am logged in as an employee or a project leader with the initials "huba"
    And there is a project named "Hansen ApS" without a project leader
    When an employee "laha" is assigned to be the project leader for "Hansen ApS"
    Then "laha" is added as the project leader for "Hansen ApS"

  Scenario: Add projectleader to a project that already has a project leader
    Given I am logged in as an employee or a project leader with the initials "huba"
    And there is a project named "DTU" with a project leader "alla"
    When an employee "huba" is assigned to be the project leader for "DTU"
    Then "huba" is not added as the project leader for "DTU"
    And "alla" remains the project leader for "DTU"
    And An error message is shown indicating that "DTU" already has the project leader "alla"