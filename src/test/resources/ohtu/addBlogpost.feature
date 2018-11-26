Feature: User can add a bookmark of type blogpost

Background:
Given command adding a bookmark is selected
And command adding a blogpost is selected

Scenario: user can add a blogpost with valid information
When title "title" and author "author" and url "url" are entered
Then system will respond with "A new Blogpost has been created and saved to the database."

Scenario: user cannot add a blogpost when some of the information is empty
When title is empty
Then system will ask the user to enter something by responding with "Please write something."

