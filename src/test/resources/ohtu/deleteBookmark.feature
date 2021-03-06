Feature: User can delete a bookmark of type blogpost by given id (US3, US13)

  Background: 
    Given user has registered and logged in with username "user" and password "password"

  Scenario: user can delete a blogpost by giving existed id and a confirmation (US3, US13)
    Given two blogposts have been created and saved to the database
    And command deleting is selected
    When existed id 1 is entered to be deleted
    And confirmation "Y" is entered
    Then bookmark with given id will be deleted and system will respond with "Successfully deleted bookmark with ID 1."

  Scenario: user can delete a video by giving existed id and a confirmation (US3, US13)
    Given two videos have been created and saved to the database
    And command deleting is selected
    When existed id 2 is entered to be deleted
    And confirmation "Y" is entered
    Then bookmark with given id will be deleted and system will respond with "Successfully deleted bookmark with ID 2."

  Scenario: user can delete a book by giving existed id and a confirmation (US3, US13)
    Given two books have been created and saved to the database
    And command deleting is selected
    When existed id 1 is entered to be deleted
    And confirmation "Y" is entered
    Then bookmark with given id will be deleted and system will respond with "Successfully deleted bookmark with ID 1."

  Scenario: bookmark will not be deleted if user gives an existed id but doese not confirm (US3)
    Given two blogposts have been created and saved to the database
    And command deleting is selected
    When existed id 1 is entered to be deleted
    And user cancells the deletion by entering "N"
    Then blogpost with given id will not be deleted and system will respond with "Bookmark will not be deleted."

  Scenario: bookmark will not be deleted if user gives an id which isn't exist in his bookmark list (US13)
    Given two blogposts have been created and saved to the database
    And command deleting is selected
    When unexisted id 10 is entered to be deleted
    Then system will remind user of unvaluable id by responding with "This Bookmark belongs to a different user. Please try again with a Bookmark that you own."

  Scenario: system will remind the user of the empty database if user tries to delete something from an empty database (US3)
    Given command deleting is selected
    When unexisted id 10 is entered to be deleted
    Then system will report about the empty database by responding with "No bookmarks have been saved in the database."
