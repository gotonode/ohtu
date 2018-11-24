Feature: User can list all bookmarks that were found from the database

  Scenario:  User can list all bookmarks
  Given      command listing all bookmarks is selected
  And       sort order "by id" is chosen
  Then       system will start to list all bookmarks and respond with "Listing all bookmarks..."

