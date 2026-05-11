Feature: Viewing overall schedule
  Description: A projectleader can view a overall schedule for all employees
  Actors: Projectleader

  Scenario: View employee schedule
    Given I am logged in as an employee with the initials "wilo"
    And A project with the name "KBHShop" exists in the system
    When I attempt to access overview of all employee schedules
    Then access is denied

  Scenario: View employee schedule
    Given I am logged in as a project leader with the initials "laha"
    And A project with the name "KBHShop" exists in the system
    When I attempt to access overview of all employee schedules
    Then access is approved
    And I can view the schedule of all employees