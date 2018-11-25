Feature: User can delete a bookmark of type blogpost by given id

Scenario: user can delete a blogpost by giving existed id and a confirmation
Given two blogposts have been created and saved to the database
And command deleting a blogpost is selected
When existed id 1 is entered
And confirmation "Y" is entered
Then system will respond with "Successfully deleted bookmark with ID 1."

Scenario: blogpost will not be deleted if user gives an existed id but doese not confirm
Given two blogposts have been created and saved to the database
And command deleting a blogpost is selected
When existed id 1 is entered
And user cancells the deletion by entering "N"
Then system will respond with "Bookmark will not be deleted."

Scenario: blogpost will not be deleted if user gives an unexisted id
Given two blogposts have been created and saved to the database
And command deleting a blogpost is selected
When unexisted id 10 is entered
Then system will respond with "Could not find a bookmark with that ID. Please re-check it."

Scenario: system will remind the user of the empty database if user tries to deleted something from an empty database
Given command deleting a blogpost is selected
When unexisted id 10 is entered
Then system will tell the user that the database is empty by responding with "No bookmarks have been saved in the database."

