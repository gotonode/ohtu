Feature: User can list all bookmarks that were found from the database (US2,US5,US8,US9)

  Scenario: User can list all bookmarks and order the list by id (US2)
    Given new bookmarks have been created and saved to the database
    And command listing all bookmarks is selected
    And command to order the list by id is chosen
    Then system will start to list all bookmarks and respond with "Listing all bookmarks..."

  Scenario: User can list all bookmarks and order the list by title (US5)
    Given new bookmarks have been created and saved to the database
    And command listing all bookmarks is selected
    And command to order the list by title is chosen
    Then system will start to list all bookmarks and respond with "Listing all bookmarks..."

  Scenario: User can list bookmarks whose titles contain given keywords (US9)
    Given new bookmarks have been created and saved to the database
    And commmand searching bookmarks is selected
    And command to search bookmarks by title is chosen
    And keyword "computer" is entered
    Then bookmarks whose titles contain the given keywords will be listed

  Scenario: User can list bookmarks whose urls contain given keywords (US8)
    Given new bookmarks have been created and saved to the database
    And commmand searching bookmarks is selected
    And command to search bookmarks by url is chosen
    And keyword "http" is entered
    Then bookmarks whose urls contain the given keywords will be listed

  Scenario: No bookmark will be listed if user wants to search bookmarks by title but the given keyword doesn't exist in any title (US9)
    Given new bookmarks have been created and saved to the database
    And commmand searching bookmarks is selected
    And command to search bookmarks by title is chosen
    And keyword "Math" is entered
    Then system will report no bookmark is found and respond with "No bookmarks found with those search terms."

  Scenario: No bookmark will be listed if user wants to search bookmarks by url but the given keyword doesn't exist in any url (US8)
    Given new bookmarks have been created and saved to the database
    And commmand searching bookmarks is selected
    And command to search bookmarks by url is chosen
    And keyword "file" is entered
    Then system will report no bookmark is found and respond with "No bookmarks found with those search terms."

  Scenario: User will be told that the database is empty when trying to list an empty database (US2,US5)
    Given command listing all bookmarks is selected
    And command to order the list by id is chosen
    Then system won't list anything and will respond with "No bookmarks have been saved in the database."
