Feature: User can list all bookmarks that were found from the database

  Scenario: User can list all bookmarks and order the list by id
    Given new bookmarks have been created and saved to the database
    And command listing all bookmarks is selected
    And command "I" is chosen to order the list by id
    Then system will start to list all bookmarks and respond with "Listing all bookmarks..."

  Scenario: User can list all bookmarks and order the list by title
    Given new bookmarks have been created and saved to the database
    And command listing all bookmarks is selected
    And command "T" is chosen to order the list by title
    Then system will start to list all bookmarks and respond with "Listing all bookmarks..."

  Scenario: User can list bookmarks whose titles contain given keywords
    Given new bookmarks have been created and saved to the database
    And commmand searching bookmarks is selected
    And command "T" is chosen to search bookmarks by title
    And keyword "computer" is entered
    Then bookmarks whose titles contain the given keywords will be listed

  Scenario: No bookmark will be listed if user wants to search bookmarks by title but the given keyword doesn't exist in any title
    Given new bookmarks have been created and saved to the database
    And commmand searching bookmarks is selected
    And command "T" is chosen to search bookmarks by title
    And keyword "Math" is entered
    Then system will report no bookmark is found and respond with "No bookmarks found with those search terms."

  Scenario: User will be told that the database is empty when trying to list an empty database
    Given command listing all bookmarks is selected
    And command "I" is chosen to order the list by id
    Then system won't list anything and will respond with "No bookmarks have been saved in the database."
