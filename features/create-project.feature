Feature: Create project
  Description: A project is created
  Actors: Projectleader, Employee


  Scenario: Create project
    Given I am logged in as an employee or a project leader with the initials "huba"
    And A project with the name "Hansen ApS" does not exist in the system
    When I create a project with the name "Hansen ApS"
    Then the project exists in the system
    And the project is assigned a project number


  Scenario: Create preexisting project
    Given I am logged in as an employee or a project leader with the initials "huba"
    And A project with the name "KBHShop" exists in the system
    When I create a project with the name "KBHShop"
    Then an error message is shown indicating that a project with the same name already exists
    And the project is not duplicated in the system