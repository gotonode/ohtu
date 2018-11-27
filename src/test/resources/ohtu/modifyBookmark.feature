Feature: User can modify a bookmark of type blogpost by given id

Scenario: user can modify a blogpost by giving existed id
Given a blogpost has been created and saved to the database
And command modifying is selected
When existed id 1 is entered to be modified
And new title "new title" and new author"new author" and new url "new url" are entered
Then blogpost with given id will be modified with given data and system will respond with "Successfully updated your bookmark."

Scenario: the field of the blogpost which is left empty won't be modified
Given a blogpost has been created and saved to the database
And command modifying is selected
When existed id 1 is entered to be modified
And all fields are left empty
Then data of the chosen blogpost won't be changed and system will respond with "Successfully updated your bookmark."

Scenario: blogpost will not be modifed if user gives an unexisted id
Given a blogpost has been created and saved to the database
And command modifying is selected
When unexisted id 10 is entered to be modified
Then system will ask user to enter a valuable id and respond with "Could not find a bookmark with that ID. Please re-check it."

Scenario: system will remind the user of the empty database if user tries to deleted something from an empty database
Given command modifying is selected
When unexisted id 10 is entered to be modified
Then system will report there's nothing can be modified by responding with "No bookmarks have been saved in the database."
