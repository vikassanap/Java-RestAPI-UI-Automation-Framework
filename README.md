Multi module approach for test case automation
==============================================
- This framework allows you to automate UI and API test cases with seperate module for reusable libraries

## Requirements
- [Maven](https://maven.apache.org/download.cgi)
- [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Features

### `core` module
1. `helpers` package acts as a wrapper over selenium to handle sync issues, and simply user events
2. `listeners` package defines framework level listeners, especially for report logging
3. `readers` package allows user to read from different file formats
4. `utils` package comes handy when you test case verification is not only limited to UI
5. `webdriver` package manages webdriver instance with ease along with listeners
6. `rest` package provides helper methods for rest api automation

### `ui` module(i.e. Cucumber + Selenium)
1. `drivers` directory contains various browser driver executables, these needs to be updated to support latest browser version
2. `pageobjects` package contains page objects for web application
3. `runners` package defines cucumber runner which executes test cases
4. `stepdefs` package defines underlying code for steps mentioned in feature files
5. `features` directory contains test case feature files
6. `config.properties` allows you to specify test execution configuration, don't use it to provide test data

### `api` module(i.e. TestNG + RestAssured)
1. `apis` contains API definitions
2. `base` contains base test definition, will be used as a parent class for test suites
3. `tests` contains testng test suite files
4. `dataproviders` contains generic data providers, can be used across test suites
5. `pojos` contains object to store request and responses
6. `utils` contains common code that can be used across framework
7. `runner.xml` contains test suites to be executed

## How to use it?
1. Clone this repo using `git clone` command
2. Goto project directory

### Execute `UI` test cases
1. Run command `mvn --projects core,ui clean test` to execute test cases
2. Run command `mvn --projects ui allure:report` to generate HTML report

### Execute `API` test cases
1. Run command `mvn --projects core,api clean test` to execute test cases
2. Run command `mvn --projects api allure:report` to generate HTML report

## Reporting and logs
- `target/screenshots` directory: contains browser screenshots in case of test case failure
- `target/automation.out` file: contains detailed log for debugging purpose
- `target/site/allure-maven-plugin/index.html` directory: contains execution report in HTML format

## Author
- [Vikas Sanap](https://www.linkedin.com/in/vikassanap/)