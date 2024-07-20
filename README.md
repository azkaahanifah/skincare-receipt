# Skincare Receipt

This project is designed for Java Spring Boot - CoHort 1.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)

## Introduction
Basic simple CRUD for Skincare Receipt project.

## Features
- CRUD Category
- CRUD Receipt

## Requirements
List the requirements for the project:
- Java 11 or higher
- Maven 3.6 or higher
- Spring Boot 3.3.1 or higher
- Any other dependencies or tools required

## Installation
Steps to install the project:

1. **Clone the repository:**
    ```bash
    git clone https://github.com/azkaahanifah/skincare-receipt.git
    ```
2. **Navigate to the project directory:**
    ```bash
    cd your-repository
    ```
3. **Build the project using Maven:**
    ```bash
    mvn clean install
    ```

## Configuration
Explain any configuration required for the project:

1. **Database Configuration:**
    - Update `application.properties` or `application.yml` with your database configurations:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabase
      spring.datasource.username=yourusername
      spring.datasource.password=yourpassword
      ```

2. **Other Configurations:**
    - Any other configurations like API keys, environment variables, etc.

## Usage
Instructions on how to run the project:

1. **Run the Spring Boot application:**
    ```bash
    mvn spring-boot:run
    ```

2. **Access the application:**
    - Open your browser and go to `http://localhost:8080`
