# English Center Management App

This is an application for managing an English center, written in Java using JavaFX and Maven.

## Building and Running the App

To build and run the app, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in your preferred IDE (such as IntelliJ IDEA or Eclipse).
3. Build the project using Maven by running the following command in the terminal:

    **mvn clean install**

4. Run the app by running the following command in the terminal:

    **mvn javafx:run**

## Creating a .env File

To create a .env file for the app, follow these steps:

1. Create a new file in the root directory of the project.
2. Name the file `.env`.
3. Add any environment variables you need to the file in the following format:

    **VARIABLE_NAME=variable_value**

    For example:

    **DB_URL=jdbc:mysql://localhost:3306/mydatabase**

    **DB_USER=root**

    **DB_PASSWORD=password123**

    Note that you should replace `VARIABLE_NAME` and `variable_value` with the actual name and value of the environment variable you want to set.
    You can then access these environment variables in your Java code using the `Env.get("VARIABLE_NAME")` method.
