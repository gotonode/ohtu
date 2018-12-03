Feature: User can delete a bookmark of type blogpost by given id (US3)

  Scenario: user can delete a blogpost by giving existed id and a confirmation 
    Given two blogposts have been created and saved to the database
    And command deleting is selected
    When existed id 1 is entered to be deleted
    And confirmation "Y" is entered
    Then bookmark with given id will be deleted and system will respond with "Successfully deleted bookmark with ID 1."

  Scenario: user can delete a video by giving existed id and a confirmation
    Given two videos have been created and saved to the database
    And command deleting is selected
    When existed id 2 is entered to be deleted
    And confirmation "Y" is entered
    Then bookmark with given id will be deleted and system will respond with "Successfully deleted bookmark with ID 2."

  Scenario: user can delete a book by giving existed id and a confirmation
    Given two books have been created and saved to the database
    And command deleting is selected
    When existed id 1 is entered to be deleted
    And confirmation "Y" is entered
    Then bookmark with given id will be deleted and system will respond with "Successfully deleted bookmark with ID 1."

  Scenario: bookmark will not be deleted if user gives an existed id but doese not confirm
    Given two blogposts have been created and saved to the database
    And command deleting is selected
    When existed id 1 is entered to be deleted
    And user cancells the deletion by entering "N"
    Then blogpost with given id will not be deleted and system will respond with "Bookmark will not be deleted."

  Scenario: bookmark will not be deleted if user gives an unexisted id
    Given two blogposts have been created and saved to the database
    And command deleting is selected
    When unexisted id 10 is entered to be deleted
    Then system will remind user of unvaluable id by responding with "Could not find a bookmark with that ID. Please re-check it."

  Scenario: system will remind the user of the empty database if user tries to delete something from an empty database
    Given command deleting is selected
    When unexisted id 10 is entered to be deleted
    Then system will report there's nothing can be deleted by responding with "No bookmarks have been saved in the database."
