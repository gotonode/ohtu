Feature: User can add a bookmark of type blogpost

Scenario: user can add a blogpost with valid information
Given command A is selected
When title "title" and author "author" and url "url" are entered
Then system will respond with "A new Blogpost has been created and saved to the database."

Scenario: user cannot add a blogpost when some of the information is empty
Given command A is selected
When title is empty
Then system will ask the user to enter something by responding with "Please write something."

