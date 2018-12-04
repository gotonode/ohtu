Feature: User can modify a bookmark of type blogpost by given id (US4)

  Scenario: user can modify a blogpost by giving existed id
    Given a blogpost has been created and saved to the database
    And command modifying is selected
    When existed id 1 is entered to be modified
    And new title "Data mining for beginners" and new author"Tavish Srivastava" and new url "http://www.analyticsvidhya.com/blog/2018/11/4-secrets-for-a-future-ready-career-in-data-science/" are entered
    Then bookmark with given id will be modified with given data and system will respond with "Successfully updated your bookmark."

  Scenario: user can modify a video by giving existed id
    Given a video has been created and saved to the database
    And command modifying is selected
    When existed id 1 is entered to be modified
    And new title "Java tutorial" and new url "https://www.youtube.com/watch?v=grEKMHGYyns" are entered
    Then bookmark with given id will be modified with given data and system will respond with "Successfully updated your bookmark."

  Scenario: user can modify a book by giving existed id
    Given a book has been created and saved to the database
    And command modifying is selected
    When existed id 1 is entered to be modified
    And new title "Python tutorial" and new author "Guido van Rossum" and new ISBN " 9-789-888-4069-06" are entered
    Then bookmark with given id will be modified with given data and system will respond with "Successfully updated your bookmark."

  Scenario: the field of the bookmark which is left empty won't be modified
    Given a blogpost has been created and saved to the database
    And command modifying is selected
    When existed id 1 is entered to be modified
    And all fields are left empty
    Then data of the chosen blogpost won't be changed and system will respond with "Successfully updated your bookmark."

  Scenario: bookmark will not be modifed if user gives an unexisted id
    Given a blogpost has been created and saved to the database
    And command modifying is selected
    When unexisted id 10 is entered to be modified
    Then system will ask user to enter a valuable id and respond with "Could not find a bookmark with that ID. Please re-check it."

  Scenario: system will remind the user of the empty database if user tries to modify something from an empty database
    Given command modifying is selected
    When unexisted id 10 is entered to be modified
    Then system will report there's nothing can be modified by responding with "No bookmarks have been saved in the database."

  Scenario: Url cannot be updated if the new one is invalid
    Given a video has been created and saved to the database
    And command modifying is selected
    When existed id 1 is entered to be modified
    But a new but invalid url "youtube" is given
    Then system will warn about the invalid url by responding with "The URL is not valid. Please remember to add 'http://' at the beginning of it."
