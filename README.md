# Bookmarks Database
*A student project done in a team of 3 members*

| Travis CI | Codecov | Codacy |
| :-: | :-: | :-: |
|[![Build Status](https://travis-ci.org/gotonode/ohtu.svg?branch=master)](https://travis-ci.org/gotonode/ohtu) | [![codecov](https://codecov.io/gh/gotonode/ohtu/branch/master/graph/badge.svg)](https://codecov.io/gh/gotonode/ohtu) | [![Codacy Badge](https://api.codacy.com/project/badge/Grade/2d0bf2d457bf498696afd4075722bf3a)](https://www.codacy.com/app/gotonode/ohtu?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gotonode/ohtu&amp;utm_campaign=Badge_Grade)

### Introduction

Bookmarks Database is a simple app for creating, editing and viewing different types of bookmarks. It's a console-based *Java* application, built using *Gradle* and tested with *JUnit*. Code coverage is done by *JaCoCo* and code style enforcement by *Checkstyle*.

### Instructions

Clone or download this repository to your computer. Unzip the archive into a folder of your choosing.

To run Bookmarks Database, simple type in `gradle run`. This will download all required dependencies.

Once the app starts, you'll be greeted with a list of commands at your disposal. Currently, these commands are available:

| | What will happen |
| :-: | :- |
| `A` | begins adding a new bookmark to the database by asking you for the bookmark's type and information |
| `L` | lists all the bookmarks currently in the database (either by ID or by title) |
| `S` | searches for bookmarks (either by title or by URL) |
| `D` | delete an existing bookmark |
| `M` | modify an existing bookmark's information |
| `X` | lists all of the commands available to the user |
| `E` | exits from the app |

#### Starting application from a jar file
* If you have cloned our project, open your command console in the root folder of the loaded project and give the command `gradle jar` or `./gradlew jar` if using Windows. This creates the jar file that is now accessable by giving the command `java -jar build/libs/ohtu.jar` in the same root file.
* If you have loaded our application as a jar from a release we have made, open your command console in the folder where the loaded jar file is located and give the command `java -jar ohtu.jar`. Starting the application in this way will also create the database bookmarks.db needed by the application in the same folder that the jar file is located at, if there is none already in existence.

### Links

From our wiki:
* [Definition of Done](https://github.com/gotonode/ohtu/wiki/Definition-of-Done)
* [Code Style](https://github.com/gotonode/ohtu/wiki/Code-Style)
* [Database](https://github.com/gotonode/ohtu/wiki/Database)
* [Sprint Charts](https://github.com/gotonode/ohtu/wiki/Sprint-Charts)

Backlogs (GitHub Projects):
* [Product Backlog](https://github.com/gotonode/ohtu/projects/3)
* [Sprint 1 Backlog](https://github.com/gotonode/ohtu/projects/1)
* [Sprint 2 Backlog](https://github.com/gotonode/ohtu/projects/2)
* [Sprint 3 Backlog](https://github.com/gotonode/ohtu/projects/4)
* [Sprint 4 Backlog](https://github.com/gotonode/ohtu/projects/5)

External resources:
* [Travis](https://travis-ci.org/gotonode/ohtu) (continuous integration & tests)
* [Codecov](https://codecov.io/gh/gotonode/ohtu) (test coverage)
* [Codacy](https://www.codacy.com/app/gotonode/ohtu) (static code analysis)

### Commands

| What | Command | Explanation |
| :- | :- | :- |
| Launch this app | `gradle run` | Gradle will build the app first if necessary |
| Start the build process | `gradle build` | All dependencies will be downloaded |
| Build a JAR file | `gradle jar` | Cannot build if project contains errors (use `check` first) 
| Clean the project | `gradle clean` | Sometimes this may fix errors |
| Runs all checks | `gradle check` | This includes checking for errors and for code style violations |
| Run Checkstyle | `gradle checkstyleMain` | Checks for code style violations |
| Generate JaCoCo | `gradle jacocoTestReport` | Generates a JaCoCo (code coverage) report |
