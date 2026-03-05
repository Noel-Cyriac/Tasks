# Task 1
This is a **Spring Boot REST API** project that provides basic arithmetic operations.
It exposes endpoints to perform mathematical calculations such as addition, subtraction, multiplication, and division.

---
## Table of Contents

- [Features](#features)  
- [Tech Stack](#tech-stack)  
- [Getting Started](#getting-started)  
- [API Endpoints](#api-endpoints) 
- [Project Structure](#project-structure)  

---

## Features

- **User Authentication**
  - JWT-based access and refresh tokens
  - Login endpoint to obtain tokens
  - Token refresh endpoint to get a new access token
- **Arithmetic Operations**
  - Add, Subtract, Multiply, Divide
- **Security**
  - Spring Security integration
  - Passwords encoded with `BCryptPasswordEncoder`
  - Stateless session management

---
## Tech Stack  
- Java 17+  
- Spring Boot 3+  
- Spring Security  
- JWT (JSON Web Tokens)  
- Maven  
- JDK compatible with Jakarta EE (Servlet API)

---
## Getting Started
### Prerequisites  
- JDK 17 or higher  
- Maven  
- Postman or any REST client (for testing API endpoints)
### Installation
1. Clone the repository:
```bash
git clone https://github.com/Innovatureai/INV_RR_26_Noel-Cyriac.git
git checkout task1-dev
cd backend-assignment/Task1
```
---
## API Endpoints
| Method | Endpoint        | Description                              |
| ------ | --------------- | ---------------------------------------- |
| POST   | `/auth/login`   | Login and get JWT tokens                 |
| POST   | `/auth/refresh` | Refresh access token using refresh token |
| GET    | `/api/add`      | Perform addition operation               |
| GET    | `/api/subtract` | Perform subtraction operation            |
| GET    | `/api/multiply` | Perform multiplication operation         |
| GET    | `/api/divide`   | Perform division operation               |


---  
## Project Structure
```
src/main/java/com/nc/Task1
 ├── config
 │   └── BeanConfig.java
 │   └── SecurityConfig.java
 ├── controller
 │   └── ArithmeticController.java
 │   └── AuthController.java
 ├── security
 │   └── JwtFilter.java
 │   └── JwtUtil.java
 ├── service
 │   └── ArithmeticService.java
 │   └── CustomUserDetailsService.java
 └── Task1Application.java  ← (**Run this file to start the application**)
src/main/resources
 └── application.properties
.gitignore
pom.xml
 ```


