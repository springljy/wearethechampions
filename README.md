### Project done by: Spring Lim Jiaying 
### Email: springljy12@gmail.com
### Contact: 91838300

# We Are The Champions - GovTech TAP 2025 Take-Home Assessment

This project is a full-stack web application built using React for the frontend and Spring Boot for the backend. It is designed for managing teams and matches, with audit logs and rankings functionality.

## Prerequisites

Before running the project, ensure the following are installed on your machine:
- [Node.js](https://nodejs.org/) (version 14.x or higher)
- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (version 11 or higher)
- [Maven](https://maven.apache.org/) (for building the Spring Boot backend)
- [MySQL](https://www.mysql.com/) (or compatible database)

## Project Structure

- `frontend/` - React-based frontend.
- `backend/` - Spring Boot backend.
- `application.properties` - Spring Boot configuration for database connection.

## Database Setup

1. Open your MySQL client and run the following command to create the required database:

   ```sql
   CREATE DATABASE wearethechampions;
   ```

2. Update the database credentials in the `application.properties` file located in the `backend/src/main/resources` folder:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/wearethechampions
   spring.datasource.username=root
   spring.datasource.password=password
   ```

   Replace `root` and `password` with your actual MySQL username and password.

## Running the Application

1. **Backend (Spring Boot):**

   - Navigate to the `backend` directory:
     ```bash
     cd backend
     ```

   - Run the Spring Boot backend using Maven:
     ```bash
     mvn spring-boot:run
     ```

2. **Frontend (React):**

   - Navigate to the `frontend` directory:
     ```bash
     cd frontend
     ```

   - Install the dependencies and start the React development server:
     ```bash
     npm install && npm start
     ```

3. Once both servers are running:
   - The backend will be running at: `http://localhost:8080`
   - The frontend will be running at: `http://localhost:3000`

## Accessing the Application

- Open your browser and navigate to `http://localhost:3000` to access the application.

## Technologies Used

- **Frontend**: React
- **Backend**: Spring Boot
- **Database**: MySQL
- **Build Tools**: Maven for Spring Boot, npm for React

## Assumptions
- I assumed that teams cannot be deleted if matches exist, as the presence of matches implies the existence of teams.

You should now be able to run the project locally and explore the full functionality of managing teams, matches, and audit logs. If you encounter any issues, feel free to reach out at springljy12@gmail.com. Thank you.
