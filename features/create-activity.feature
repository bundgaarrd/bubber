Feature: Create activity
  Description: An activity is created for a project and it is possible,
  to add number of hours needed for the activity, as well as start and end date.
  Actors: Projectleader, Employee


  Scenario: Create activity
    Given I am logged in as project leader or employee with the initials "huba"
    And There is no activity named "Analysis" in this project
    When I create an activity named "Analysis"
    Then the activity now exists in the project


  Scenario: Create preexisting activity
    Given I am logged in as project leader or employee with the initials "huba"
    And There is already an activity named "Analysis" in this project
    When I create an activity named "Analysis"
    Then an error message is shown indicating that an activity with the same name already exists in this project
    And the activity is not duplicated in the project


  Scenario: Register working hours for activity as employee
    Given I am logged in as an employee with the initials "huba"
    When I create an activity
    And add how many hours needed for the activity
    Then the needed hours are not registered


  Scenario: Register working hours for activity as project leader
    Given I am logged in as a project leader with the initials "huba"
    When I create an activity
    And add how many hours needed for the activity
    Then the needed hours are registered


  Scenario: Hours expected for an activity
    Given I am logged in as a project leader with the initials "laha"
    When I edit an activity
    And add the expected hours to finish the activity
    Then the number of expected hours to finish are stored
    And Previous number of expected hours to finish are overwritten


  Scenario: Start and finish time for activity
    Given I am logged in as a project leader with the initials "laha"
    When I edit an activity
    And add a start and finish date
    Then the dates are added to that activity
    And Previous start and finish dates are overwritten