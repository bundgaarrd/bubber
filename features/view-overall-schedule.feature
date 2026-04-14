Feature: Viewing overall schedule
  Description: A projectleader can view a overall schedule for all employees
  Actors: Projectleader


  Scenario: View employee schedule
    Given I am logged in as an employee
    When I attempt to access overview of all employee schedules
    Then access is denied


  Scenario: View employee schedule
    Given I am logged in as a project leader
    When I attempt to access overview of all employee schedules
    Then access is approved
    And I can view the schedule of all employees


  Scenario: Create project
    Given I am logged in as project leader or employee
    When I create a project with the name "Hansen ApS"
    Then the project exists in the system
    And the project tildeles is assigned a project number

  Scenario: Create activity
    Given I am logged in as project leader or employee
    When I create an activity named "Analysis”
    Then the activity should exist in the system
  Scenario: Assign employee
    Given I am logged in as an employee or a project leader
    And the selected employee is available
    When the selected employee is assigned to an activity
    Then the selected employee is added to the activity
    And the employee's schedule is updated

  Scenario: Register fixed activities
    Given I am logged in as an employee or a project leader
    When I add fixed activities
    Then the timesheet is updated

  Scenario: View employee schedule
    Given I am logged in as an employee
    When I attempt to access overview of all employee schedules
    Then access is denied

  Scenario: Deleting a project
  Given: I am a project leader or an employee
  When: project leader deletes a project
  Then:  the project does not exist in the system anymore

  Scenario: Hours expected for an activity
  Given: I am a project leader
  When: project leader enters the expected hours to finish activity
  Then: number of expected to finish are stored

  Scenario: Enforcing start and finish time for an activity
  Given: I am a project leader
  When: determine start date and finish date
  Then: to start and finish the project are saved in the system

  Scenario: Registering invalid hours
  Given: I am an employee
  When: the employee registers negative hours
  Then: an error message is sent