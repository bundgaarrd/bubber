Feature: Delete project
  Description: A project is deleted from the system
  Actors: Projectleader, Employee


  Scenario: Deleting a project
    Given I am logged in as projectleder or employee
    And A project with the name "Hansen ApS" exists in the system
    When I delete the project "Hansen ApS"
    Then the project no longer exists in the system


  Scenario: Deleting a non-existent project
    Given I am logged in as projectleder or employee
    And A project with the name "Hansen ApS" does not exist in the system
    When I delete the project "Hansen ApS"
    Then An error message is shown indicating that the project does not exist in the system