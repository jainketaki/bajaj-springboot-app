# Bajaj Finserv Health - Qualifier 1 (JAVA)

This project is a Spring Boot application built for the **Bajaj Finserv Health | Qualifier 1** competition.

## 🚀 Features
- On startup, the app:
  1. Generates a webhook by sending a POST request.
  2. Receives a SQL problem based on `regNo`.
  3. Selects the correct SQL query solution.
  4. Submits the solution to the webhook using JWT authentication.
- Minimal Bootstrap UI for presentability.

## 🛠️ Tech Stack
- Java 17  
- Spring Boot 3.5.5  
- Maven  
- Bootstrap 5  

## 📂 Project Structure
bfh-app/
├── src/
│ ├── main/java/... # Spring Boot source code
│ └── main/resources/
│ ├── static/ # Bootstrap landing page
│ └── application.properties
├── pom.xml
├── README.md

## ▶️ How to Run
1. Build the JAR:
   ```bash
   mvn clean package -DskipTests
Run the app:
2. Run the app:
java -jar target/bfh-app-0.0.1-SNAPSHOT.jar


3. Open http://localhost:8080
 in browser to see UI.

Submission Checklist

✅ Code pushed to GitHub

✅ Final JAR uploaded (raw link shared)

✅ Static Bootstrap landing page