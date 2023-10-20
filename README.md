# Net Calculator Service

The Net Calculator Service is a Java application that calculates the net price for a given amount in different countries based on tax rates loaded from a CSV file.

## Prerequisites

Before you can use the Net Calculator Service, ensure you have the following prerequisites installed on your system:

- **Java Development Kit (JDK)**: Version 8 or later.
- **Apache Maven**: You'll need Maven for building the project.

## Getting Started

Follow these steps to set up, run, and test the Net Calculator Service:

1. **Download the Zip Archive**: Download the zip archive of the Net Calculator Service.

2. **Extract the Archive**: Extract the contents of the downloaded zip archive to a directory of your choice.

3. **Build the Project**: Open your terminal or command prompt and navigate to the directory where you extracted the files. Run the following command to build the project:

    `mvn clean install`

This command will compile the code, run tests, and package the project into a JAR file.

## Run the Application
Once the project is built, you can run the Net Calculator Service by running the Main.java or by executing the following command:

    `java -jar target/NetCalculator-1.0-SNAPSHOT.jar`
    
The service will calculate the net price for a given amount in different countries based on tax rates loaded from a CSV file.

## Configuration
The project uses a CSV file named tax_rates.csv(located under src/main/resources) to load tax rates for different countries. You can customize this file to include the tax rates for the countries you need. Ensure that the CSV file follows the required format (comma-separated values):

| CountryISOCode | TaxRate |
|----------------|---------|
| DE             | 0.19    |
| FR             | 0.20    |

## Running Test Cases
To run the test cases for the Net Calculator Service, run the NetPriceCalculatorTest.java from src/test/java or just use the following command:
    `mvn test`
There are 10 testcases which cover various positive and negative scenarios.

## Validation Checks
Appropriate validation check for countryISO, grossPrice and taxRates are implemented.

## Exception Handling
To maintain the application's simplicity, built-in exception methods were used instead of implementing custom exception handlers.