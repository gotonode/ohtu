Feature: User can register an account

  Background: 
    Given command to register is selected

  Scenario: User can register an account with a valid username and password and password confirmation matches to the given password
    When valid username "user1" and password "password1" and matched password confirmation are given
    Then register will success and systen will respond with "Your new account was created and you have been logged in! Your user ID is 2147483647."

  Scenario: Register will fail if the given username is too short or too long
    When too short username "u" is given
    Then register will fail and system will respond with "Your username must be between 3 and 10 characters."

  Scenario: Register will fail if the given password is too short or too long
    When valid username "user" and too short password "short" are given
    Then register will fail and system will respond with "Your password must be between 6 and 32 characters."

  Scenario: Register will fail if the password confirmation doesn't match
    When valid username "user1" and password "password1" but unmatched password confirmation "unmatchedconfirmation" are given
    Then register will fail and system will respond with "The passwords didn't match. Please try again."
