
Feature: User can add a bookmark of type blogpost (US1,US7,US10)

  Background: 
    Given user has registered and logged in with username "user" and password "password"
    And command adding a bookmark is selected

  Scenario: user can add a blogpost with valid information (US1)
    Given command adding a blogpost is selected
    When title "The 2018 Top Programming Languages" and author "Ian Watson" and url "http://uoacomputerscience.blogspot.com/2018/08/the-2018-top-programminglanguages.html" are entered
    Then system will respond with "A new Blogpost has been created and saved to the database."

  Scenario: user cannot add a bookmark when some of the information is empty (US1,US7,US10)
    Given command adding a blogpost is selected
    When title is empty
    Then system will ask the user to enter something by responding with "Please write something."

  Scenario: user cannot add a blogpost or a video with invalid url (US1,US7)
    Given command adding a video is selected
    When title "Map of Computer Science" and an invalid url "youtube.com" are entered
    Then sysytem will ask user to enter a valid url by responding with "The URL is not valid. Please remember to add 'http://' at the beginning of it."

  Scenario: user can add a video with valid information (US7)
    Given command adding a video is selected
    When title "Map of Computer Science" and url "https://www.youtube.com/watch?v=SzJ46YA_RaA" are entered
    Then system will respond with "A new Video has been created and saved to the database."

  Scenario: use can add a book with valide information (US10)
    Given command adding a book is selected
    When title "Introduction to algorithms" and author "Thomas H. Cormen" and ISBN "9-780-262-0338-48" are entered
    Then system will respond with "A new Book has been created and saved to the database."