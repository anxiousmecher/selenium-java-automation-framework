Overview

This repository contains a Selenium Automation Framework built using Java, Maven, and TestNG.
The framework supports parallel execution, rich reporting, centralized logging, and CI/CD integration using Jenkins.

It is designed to be scalable, maintainable, and suitable for both local and continuous integration executions.

Technology Stack

Java (JDK 11 or higher)

Selenium WebDriver

Maven

TestNG

Jenkins

Extent Reports

Log4j2

Apache PDFBox

Git

Project Structure

selenium-java-automation
|
|-- Jenkinsfile
|-- pom.xml
|-- testng.xml
|-- README.txt
|
|-- src
| |-- main
| | |-- java
| | | |-- base
| | | |-- config
| | | |-- utilities
| | |
| | |-- resources
| | |-- log4j2.xml
| |
| |-- test
| |-- java
| |-- tests
| |-- listeners
|
|-- reports
|-- logs
|-- target
|-- test-output

Prerequisites

Ensure the following software is installed on your local machine or Jenkins agent:

Java JDK 11 or above

Maven 3.6 or above

Google Chrome / Firefox browser

Compatible WebDriver binaries

Jenkins (for CI/CD execution)

Getting Started

Clone the repository

git clone https://github.com/your-org/selenium-java-automation.git

cd selenium-java-automation

Build the project

mvn clean compile

Test Execution

Run all tests:

mvn test

Run tests using TestNG suite file:

mvn test -Dsurefire.suiteXmlFiles=testng.xml

Run tests in parallel:

Parallel execution is controlled using the testng.xml file.

Example:
<suite name="Parallel Suite" parallel="tests" thread-count="5">

Browser Configuration

Browser can be passed as a Maven parameter.

Example:
mvn test -Dbrowser=chrome

Supported browsers:

Chrome

Firefox

Edge (optional)

Reporting and Logging

Extent Reports

Extent Reports is used to generate detailed HTML execution reports.

Features:

Step-level execution logging

Pass / Fail / Skip status

Screenshot capture on failure

Execution timestamps

Environment details

Report Location:
reports/extent-report.html

Logging - Log4j2

Log4j2 is used for centralized logging across the framework.

Logging Features:

Console and file logging

Multiple log levels (INFO, DEBUG, ERROR)

Class-level logging

Configuration File:
src/main/resources/log4j2.xml

Log Output Location:
logs/application.log

PDF Reporting - Apache PDFBox

Apache PDFBox is used to generate PDF execution reports.

PDF Report Features:

Execution summary

Test case status

Execution timestamps

Screenshot references (if enabled)

PDF Report Location:
reports/test-execution-report.pdf

Reporting Flow

Log4j2 captures execution logs during test run

Extent Reports records test steps and execution results

Apache PDFBox generates consolidated PDF report after execution

Jenkins CI/CD Integration

This project includes a Jenkinsfile for pipeline execution.

Pipeline stages include:

Source code checkout

Maven build

Selenium test execution (parallel)

Report generation

Artifact archival

Workspace cleanup

Archived Artifacts:

Extent HTML reports

TestNG reports

Execution logs

PDF execution reports

Artifacts and Reports in Jenkins

After pipeline execution:

Navigate to the Jenkins build

Open Artifacts section

Download reports and logs

Best Practices

Follow Page Object Model (POM) design pattern

Keep test cases independent

Avoid hard-coded values

Use configuration files for environment data

Use Extent Reports for test-level logging

Use Log4j2 for framework-level logs

Contributing

Fork the repository

Create a feature branch

Commit changes

Raise a pull request


Just tell me 👍
