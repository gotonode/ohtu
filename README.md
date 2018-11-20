# Bookmarks Database
A student project done in a team of 3 members

Course: *Ohjelmistotuotanto 2018*

| Travis CI | Codecov | Codacy |
| :-: | :-: | :-: |
|[![Build Status](https://travis-ci.org/gotonode/ohtu.svg?branch=master)](https://travis-ci.org/gotonode/ohtu) | [![codecov](https://codecov.io/gh/gotonode/ohtu/branch/master/graph/badge.svg)](https://codecov.io/gh/gotonode/ohtu) | [![Codacy Badge](https://api.codacy.com/project/badge/Grade/2d0bf2d457bf498696afd4075722bf3a)](https://www.codacy.com/app/gotonode/ohtu?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gotonode/ohtu&amp;utm_campaign=Badge_Grade)

### Introduction

Bookmarks Database is a simple app for creating, editing and viewing different types of bookmarks. It's a console-based *Java* application, built using *Gradle* and tested with *JUnit*. Code coverage is done by *JaCoCo* and code style enforcement by *Checkstyle*.

### Instructions

Clone or download this repository to your computer. Unzip the archive into a folder of your choosing.

To run Bookmarks Database, simple type in `gradle run`. This will download all required dependencies.

Once the app starts, you'll be greeted with a list of commands at your disposal. Currently, these commands are available:

| Command | What will happen |
| :- | :- |
| `A` | begins adding a new blogpost to the database by asking you for the blogpost's information |
| `L` | lists all the blogposts currently in the database, if any |
| `X` | lists all the commands available to the user |
| `E` | exits from the app |

### Links

From our wiki:
* [Definition of Done](https://github.com/gotonode/ohtu/wiki/Definition-of-Done)
* [Code Style](https://github.com/gotonode/ohtu/wiki/Code-Style)
* [Database](https://github.com/gotonode/ohtu/wiki/Database)
* [Sprint Charts](https://github.com/gotonode/ohtu/wiki/Sprint-Charts)

Backlogs (GitHub Projects):
* [Product Backlog](https://github.com/gotonode/ohtu/projects/3)
* [Sprint 1 Backlog](https://github.com/gotonode/ohtu/projects/1)

External resources:
* [Travis](https://travis-ci.org/gotonode/ohtu) (continuous integration & tests)
* [Codecov](https://codecov.io/gh/gotonode/ohtu) (test coverage)
* [Codacy](https://www.codacy.com/app/gotonode/ohtu) (static code analysis)

### Commands

| What | Command | Explanation |
| :- | :- | :- |
| Launch this app | `gradle run` | Gradle will build the app first if necessary |
| Start the build process | `gradle build` | All dependencies will be downloaded |
| Clean the project | `gradle clean` | Sometimes this may fix errors |
