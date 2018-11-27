Feature: User can add a bookmark of type blogpost

Background:
Given command adding a bookmark is selected


Scenario: user can add a blogpost with valid information
Given command adding a blogpost is selected
When title "The 2018 Top Programming Languages" and author "Ian Watson" and url "http://uoacomputerscience.blogspot.com/2018/08/the-2018-top-programminglanguages.html" are entered
Then system will respond with "A new Blogpost has been created and saved to the database."

Scenario: user cannot add a bookmark when some of the information is empty
Given command adding a blogpost is selected
When title is empty
Then system will ask the user to enter something by responding with "Please write something."

Scenario: user can add a video with valid information
Given command adding a video is selected
When title "Map of Computer Science" and url "https://www.youtube.com/watch?v=SzJ46YA_RaA" are entered
Then system will respond with "A new Video has been created and saved to the database."
