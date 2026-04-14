Feature: Register fixed activities
  Description: A projectleader can register a fixed activity, such as vacation, courses etc.
  Actors: Projectleader, Employee


  Scenario: Register fixed activities
    Given I am logged in as an employee or a project leader
    When I add a fixed activity with name "Vacation" and specified start and end date
    And there is no other fixed activity on the same dates
    Then the fixed activity is added to the timesheet


  Scenario: Register fixed activities
    Given I am logged in as an employee or a project leader
    And I have a fixed activity called "Course" with specified start and end date
    When I add a fixed activity with name "Vacation" at the same dates as "Course"
    Then an error message is shown indicating that there is already a fixed activity on the same dates
    And the timesheet is not updated