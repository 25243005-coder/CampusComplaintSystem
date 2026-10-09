# Campus Service & Complaint Management System

## Project Overview

The Campus Service & Complaint Management System is a JavaFX desktop application developed to help students submit campus-related complaints and track their status. Administrators can view complaints, update their progress, and provide responses.

## Technologies Used

* Java
* JavaFX
* MySQL
* JDBC
* Visual Studio Code

## Features

### Student Module

* Student registration and login
* Raise complaints with category, priority, and description
* View previously submitted complaints
* Track complaint status
* View administrator responses

### Admin Module

* Administrator login
* View and manage student complaints
* Update complaint status
* Provide responses to complaints

## Database

The application uses MySQL with the following tables:

* `users`
* `complaints`
* `categories`
* `complaint_updates`
* `departments`

## Project Structure

* `src/` – Java source files
* `lib/` – MySQL JDBC connector

## How to Run

1. Install JDK 21 and a compatible JavaFX runtime.
2. Install MySQL Server.
3. Create and configure the `campus_complaint_system` database and required tables.
4. Configure the database connection in `DBConnection.java`.
5. Add the MySQL Connector/J JAR to the classpath.
6. Compile and run the application using the configured JavaFX environment.

## Disclaimer

This project is developed for educational purposes as an academic mini-project.
