Feature: User can list all bookmarks that were found from the database

  Scenario:  User can list all bookmarks and order the list by id
  Given      two blogposts have been created and saved to the database
  And        command listing all bookmarks is selected
  And        command "I" is chosen to order the list by id
  Then       system will start to list all bookmarks and respond with "Listing all bookmarks..."

Scenario:  User can list all bookmarks and order the list by title
  Given      two blogposts have been created and saved to the database
  And        command listing all bookmarks is selected
  And        command "T" is chosen to order the list by title
  Then       system will start to list all bookmarks and respond with "Listing all bookmarks..."

Scenario: User will be told that the database is empty when trying to list an empty database
Given command listing all bookmarks is selected 
And   command "I" is chosen to order the list by id
Then system will tell the user that the database is empty by responding with "No bookmarks have been saved in the database."


