// Ekstra feature
// Lavet af Lea - s245072
Feature: Look up employee
  Description: An employee is found in employee repository
  Actors: Projectleader, Employee

Scenario: Employee is found by name
    Given I am logged in as an employee or a project leader with the initials "huba"
    When I look up an employee by name "William Lopez" 
    Then the employee is found with initials "wilo"

Scenario: Employee is not found by name
    Given I am logged in as an employee or a project leader with the initials "huba"
    When I look up an employee by name "Alma Jensen" 
    Then no employee is found

Scenario: Lookup available employees
    Given I am logged in as an employee or a project leader with the initials "huba"
    When I look up available employees
    Then available employees are listed
    And the employee "wilo" is found in the list of available employees
    And the employee "anda" is not found in the list of available employees
