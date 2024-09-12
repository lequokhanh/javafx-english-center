# English Center Management App

This application streamlines English center operations, enhances student experience, and improves teacher-student communication. It's written in Java using JavaFX and Maven.

## Table of Contents

- [Project Overview](#project-overview)
    - [Introduction](#introduction)
    - [Research Problems](#research-problems)
    - [Objectives](#objectives)
    - [Scope and Limitations](#scope-and-limitations)
    - [Development Tools](#development-tools)
- [Requirement Specification](#requirement-specification)
    - [Survey of the Organization](#survey-of-the-organization)
    - [Survey of Business Processes and Major Activities](#survey-of-business-processes-and-major-activities)
    - [Software Requirements](#software-requirements)
- [Analysis and Design](#analysis-and-design)
- [Implementation and Testing](#implementation-and-testing)
    - [Implementation Environment](#implementation-environment)
    - [User Interface](#user-interface)
- [Useful Links](#useful-links)
- [Contribution](#contribution)
- [Contact](#contact)
- [Building and Running the App](#building-and-running-the-app)
- [Creating a .env File](#creating-a-env-file)

## 📄 Project Overview

This project, developed collaboratively with classmates, provided practical experience in system analysis and design. 

### Introduction

- Many English centers struggle with manual or inefficient management processes, hindering their growth and performance. 
- This application addresses these challenges with a user-friendly interface and robust features, improving efficiency and streamlining operations.

### Research Problems

Traditional management methods often lack remote collaboration, pose security risks, and are not automation-friendly. Data management becomes increasingly difficult as English centers grow, leading to disorganization and communication issues.  This application provides a solution with automation, a user-friendly UI, and diverse functionality.

### Objectives

- Faster database searches.
- Improved online communication between teachers and students.
- Key features include:
    - Enrollment and placement test management.
    - Student/Teacher management.
    - User management.
    - Academic and classroom management.
    - Academic results and financial management.
    - Analysis/reporting.
    - Data recovery and backup.

### Scope and Limitations

Due to time and resource constraints, the project focuses on:
- User role assignment.
- Class and lesson management.
- User management.
- Course and chapter management.
- Schedule generation.
- Attendance tracking.
- Classroom management.
- Analysis/reporting.

### Development Tools

- Visual Studio Code/ IntelliJ IDEA.
- Java.
- Oracle database.
- Figma.
- StarUML.

## 🔎 Requirement Specification

- ### Survey of the Organization
    - Overview of the current organizational situation.

- ### Survey of Business Processes and Major Activities
    Details the processes and activities for:
    - **Board of Directors:** Goal setting, board meetings, decision-making, financial oversight.
    - **Business Department:** Student enrollment, student database management, marketing, promotion, customer relationship management.
    - **Accounting-Finance Department:** Financial operations, record keeping, financial health.
    - **Human Resources Department:** Employee recruitment, development, performance management, compliance, work culture.
    - **Academic Department:** Managing teaching documents, providing an effective learning environment.

- ### Software Requirements
    - **Functional Requirements:**
        - Data storage for accounts, students, rooms, courses, chapters, classes, lessons, attendance, materials.
        - Search functionality for classes, rooms, courses, users, chapters, lessons.
        - Statistical reporting with graphs and exportable class reports.
    - **Non-Functional Requirements:**
        - Usability, reliability, performance, security, maintainability.

## 🔎 Analysis and Design

Diagrams are available for:
- Use-case Diagram: [link](1.%20Use-case/) 
- Use-case Specification and Activity Diagram: [link](2.%20Use-case%20Specification%20and%20Activity%20Diagram/) 
- Sequence Diagram and Class Diagram: [link](3.%20Sequence%20Diagram%20and%20Class%20Diagram/) 
- Class Diagram and Entity Class Diagram: [link](4.%20Class%20Diagram%20and%20Entity%20Class%20Diagram/) 
- State Diagram: [link](5.%20State%20Diagram/) 
- Mapping to Relational Model from Entity Class Diagram: [link](6.%20Mapping%20to%20Relational%20Model%20from%20Entity%20Class%20Diagram/) 

## 🛠 Implementation and Testing

- ### Implementation Environment
    - Operating Systems: Windows 11, MacOS Ventura.
    - Database: Oracle database.
    - IDE: IntelliJ IDEA.
    - Programming Language: Java.
    - Libraries: JavaFX, Lombok, PDFBox.

- ### User Interface
    Screenshots and explanations: [link](7.%20User%20Interface/)

## 📜 Useful Links

- Figma design: [link](https://www.figma.com/file/pM0MNgqKj6pD2Acfm1y7B4/Remove-BG-(Community)?type=design&node-id=0%3A1&mode=design&t=QLmURxCytRMBPhed-1)

## 🤝 Contribution

- Le Quoc Khanh: Main developer, System architect, Project manager.
- Le Gia Kiet: System analyst, Designer, Project sub-manager.
- Ninh Thien Bao: Developer, Database administrator, Reporter.
- Nguyen Thi Thuy: Reporter, Tester.

## 📧 Contact

g.kietle@gmail.com, lequockhanhkt03@gmail.com

## Building and Running the App

1. Clone the repository: `git clone <repository_url>`
2. Open the project in your IDE.
3. Build: `mvn clean install`
4. Run: `mvn javafx:run`

## Creating a .env File

1. Create a file named `.env` in the project root directory.
2. Add environment variables (replace placeholders with your values):
    ```
    DB_URL=jdbc:oracle:thin:@localhost:1521:orcl 
    DB_USER=c##center_name 
    DB_PASSWORD=password123 
    MATERIAL_PATH=D:\\Materials 
    ```
3. Access environment variables in your Java code using `Env.get("VARIABLE_NAME")`.
