# MSGF-Treasury
This repository contains the financial movement information system of the MSGF organization

## Index

1. [Description](#description)
2. [Prerequisites](#prerequisites)
3. [Usage](#usage)


## Description

[Spring initializr](https://start.spring.io/) is a tool that facilitates the creation of Spring Boot projects that integrate several dependencies as Spring Data JPA, Spring Web, Lombok, Thymeleaf, DevTools. Spring Boot is a development framework that simplifies the creation of Web applications in Java.

This module is used exclusively to manage the financial movements of the MSGF organization. 


## Prerequisites

To use this program you need the following:

1. **Version control system**: Install GIT from the [GIT official website](https://git-scm.com/downloads).

2. **IntelliJ IDEA**: To run and/or modify the project, you can download it from the [IntelliJ official website](https://www.jetbrains.com/es-es/idea/download/?section=windows).

3. **Java 17 or higher**: You can get help to download and install the java version by following [this link](https://www.youtube.com/watch?v=oAin-q1oTDw&pp=ygUXY29tbyBjb25maWd1cmFyIGphdmEgMTc%3D)

4. **Maven 3.9**: You can get help to download and install the maven version by following [this link](https://www.youtube.com/watch?v=1QfiyR_PWxU&pp=ygUSaW5zdGFsYXIgbWF2ZW4gMy45)

5. **PostgreSQL with PgAdmin**: you can download to install PostgreSQL and pgAdmin for the database manage [official website](https://www.postgresql.org/download/)
6. **Email Sender Configuration**: To send email notifications to people who request credits.

## Usage

To use this program in your local machine you must:

1. Open a terminal in the folder where you want to download the program and clone it with:

   ```
   git clone https://github.com/BPMN-sw-evol/MSGF-Treasury.git
   ```
2. Create a .env file with the configuration info:
     1. Database connection info (URL, USERNAME, PASSWORD).
     2. Email sender info (ADDRESS, APPLICATION PASSWORD).
      ```
      DB_URL=jdbc:postgresql://localhost:5432/credit_request
      DB_USERNAME=postgres
      DB_PASSWORD=admin
   
      EMAIL_ADDRESS=your-email@example.com
      EMAIL_PASSWORD=your-application-password
      ```
   Remember that you must have the "credit_request" database created on your PostgreSQL server and configurate the mail sender. 

3. Open the **MSGF-Treasury** folder and find the **Application** file containing the main method and run it with the start option (upper green triangle).

4. The program works on port 9004.