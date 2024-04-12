#  trace-hub

This repository integrates the implementation of a case study from the book "Object-Oriented and Classical Software Engineering" by Stephen R. Schach called MSG-Foundation.

## Index

1. [Description](#description)
2. [Applications](#applications)
2. [Prerequisites](#prerequisites)
3. [Usage](#usage)


## Description

The applications created in this implementation were developed using the Java programming language, mainly taking advantage of the Spring Boot development framework as well as the Camunda 7 platform for business process management.

[Java](https://www.java.com/es/) is a versatile and widely used programming language known for its platform independence, robustness, and scalability. With its rich ecosystem of libraries and frameworks, Java enables developers to build complex enterprise-grade applications efficiently and reliably.

[Spring initializr](https://start.spring.io/) is a tool that streamlines the creation of Spring Boot projects by integrating various dependencies such as Spring Data JPA, Spring Web, Lombok, Thymeleaf, and DevTools. Spring Boot improves the development process by providing out-of-the-box solutions for common challenges, allowing developers to focus on business logic rather than infrastructure configuration.

In addition, [Camunda Platform 7](https://camunda.com/platform-7/) has been used for the design and implementation of business processes. Camunda is an open source workflow and decision automation platform based on the BPMN (Business Process Model and Notation) and DMN (Decision Model and Notation) standards. It offers powerful workflow management capabilities, allowing organizations to effectively model, automate and optimize business processes. Camunda's flexible and scalable architecture makes it suitable for a wide range of use cases, from automating simple tasks to complex enterprise workflows.

## Applications

1. **MSGF-BPM-Engine**: program that integrates the Camunda process engine in its version 7 in a Spring Boot project persisting the whole schema to a PostgreSQL database. [Repository Link](https://github.com/BPMN-sw-evol/MSGF-BPM-Engine)

2. **MSGF-CreditRequest**: program that manages all credit requests from customers. There you can create a new request and follow up on it. [Repository Link](https://github.com/BPMN-sw-evol/MSGF-CreditRequest)

3. **MSGF-CentralSys**: program that manages all credit requests from customers at the organization level. This is where the organization's employees follow up on their assigned tasks. [Repository Link](https://github.com/BPMN-sw-evol/MSGF-CentralSys)

4. **MSGF-Treasury**: program that manages the financial movements when a credit request is approved by the treasury office. [Repository Link](https://github.com/BPMN-sw-evol/MSGF-Treasury)

5. **annotations**: as part of the business process variable traceability project, some customized annotations were implemented in order to identify how these variables are traced in the source code. Here is the definition of the annotations using a Java project. [Repository Link](https://github.com/BPMN-sw-evol/Annotations)

## Prerequisites

To use this program you need the following:

1. **Version control system**: Install GIT from the [GIT official website](https://git-scm.com/downloads).

2. **IntelliJ IDEA**: To run and/or modify the project, you can download it from the [IntelliJ official website](https://www.jetbrains.com/es-es/idea/download/?section=windows).

3. **Docker and Docker-Compose**: To use Docker correctly follow the steps below:
   - Step 1: Install Docker from the official website.(If you already have Docker Desktop installed, skip this step).
   - Step 2: Create a Docker Hub account from the official website, and log in to Docker Desktop. (If you already have an account, log in directly to Docker Desktop) WSL2: According to your need: Use the following command to install WSL2 wsl --list --o to know the available distributions. wsl --install -d "distribution-version" to install WSL2 with a specific distribution. Use the following command for upgrade to WSL2 wsl --version if you want to know the installed WSL version wsl --set-default-version 2 to change the version to WSL2.

## Usage

To use this program you must:

1. Verify that the Docker daemon is running.

2. Open a terminal in the folder where you want to download the program and clone it with:

   ```
   https://github.com/BPMN-sw-evol/trace-hub
   ```

3. You should run the build-and-run.sh file as follows (if you are on a Windows operating system use Git Bash):

   ```
   ./build-and-run.sh
   ```

4. Wait a moment while the images and containers are built and the services are launched. This depends on your computing power.
   
5. You are done! After that you can interact with the MSG-Foundation systems.
   -   MSGF-BPM-Engine works on port 9000.
   -   MSGF-CreditRequest works on port 9001.
   -   MSGF-CentralSys works on port 9002.
   -   MSGF-Treasury works on port 9004.
