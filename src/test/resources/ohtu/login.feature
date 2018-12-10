Feature: User can login with a valid account

  Background: 
    Given user has registered and logged in with username "username" and password "password"
    And logged out
    And command to log in is selected

Scenario: User can log in with valid username and password
When valid username "username" and password "password" are given
Then login will succeed and system will respond with "You have been logged in! Your user ID is 1."

Scenario: User cannot login with wrong username
When wrong username "wrongname" and right password "password" are given
Then login will fail and system will respond with "Could not log in. Please check your username and password."

Scenario: User cannot login with wrong password
When right username "username" but wrong password "wrongpassword" are given
Then login will fail and system will respond with "Could not log in. Please check your username and password."