Feature: View own schedule
  Description: An employee can view their own schedule
  Actors: Projectleader, Employee

  Scenario: View own schedule
    Given I am logged in as an employee or a project leader with the initials "huba"
    When I attempt to access my own schedule
    Then access is approved
    And I can view my own schedule