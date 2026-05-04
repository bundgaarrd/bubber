Feature: Delete project
  Description: A project is deleted from the system
  Actors: Projectleader, Employee


  Scenario: Deleting a project
    Given I am logged in as an employee or a project leader with the initials "laha"
    And A project with the name "KBHShop" exists in the system
    When I delete the project with name "Hansen ApS"
    Then the project no longer exists in the system


  Scenario: Deleting a non-existent project
    Given I am logged in as an employee or a project leader with the initials "laha"
    And A project with the name "KBHShop" does not exist in the system
    When I delete the project with name "KBHShop"
    Then An error message is shown indicating that the project does not exist in the system