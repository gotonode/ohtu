Feature: User can delete a bookmark of type blogpost by given id

Scenario: user can delete a blogpost by giving existed id and a confirmation
Given two blogposts have been created and saved to the database
And command deleting a blogpost is selected
When existed id 1 is entered
And confirmation "Y" is entered
Then system will respond with "Successfully deleted bookmark with ID 1."

Scenario: blogpost will not be deleted if user give an existed id but doese not confirm
Given two blogposts have been created and saved to the database
And command deleting a blogpost is selected
When existed id 1 is entered
And user cancells the deletion by entering "N"
Then system will respond with "Bookmark will not be deleted."


