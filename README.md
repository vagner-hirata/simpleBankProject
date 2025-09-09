# Simple Bank System API

An API for creating and transfering money between accounts.

## About the project

This simple bank system API was createad with the intent to learn and understand ACID principles while practicing good habits with SOLID, OOP(Object Oriented Programming) and Spring Boot.
It has endpoints for all CRUD operations.

This project has consolidated my understanding on:
-  Which types to use when creating money variables.
-  Creating controllers, services and repositories based on MVC standard.
-  Entity mapping with Spring Data JPA
-  Treating exception and validating data.
-  What is Atomicity from ACID principles.

## Functionalities
- **Registering new bank accounts:** Allow the creation of new bank accounts.
- **CRUD of Bank Account:**
  - Create a new bank account with client name, balance, account type and account number.
  - Show a bank account by its account number.
  - Make transfers between accounts
  - Create transfer history when making a transfer or deposit.

## Technologies and tools

The following technologies were used when creating this project:

- **Backend**: Java 21, Spring boot 3.5, Spring web, Spring validation.
- **Data persistence:** PostgreSQL, Spring Data JPA.
- **Build and Dependency:** Maven 3.9
- **Support tools:** Insomnia
