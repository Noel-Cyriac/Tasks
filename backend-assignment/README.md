# Task 2 

This is a **Spring Boot** application that provides user authentication with JWT, file management (upload, download, list, delete), and task management (CRUD operations). The project demonstrates secure REST APIs and JWT token handling.

---

## Table of Contents

- [Features](#features)  
- [Tech Stack](#tech-stack)  
- [Getting Started](#getting-started)  
- [API Endpoints](#api-endpoints) 
- [Project Structure](#project-structure)  

---

## Features

1. **User Authentication**
   - Register with username and password
   - Login to receive access and refresh JWT tokens
   - Token refresh endpoint
   - Logout invalidates access and refresh tokens

2. **File Management**
   - Upload PDF, PNG, JPG files (max 5MB)
   - List all uploaded files for authenticated user
   - Download a file
   - Delete a file

3. **Task Management**
   - Create, read, update, delete tasks
   - Each task belongs to a user
   - Users can only access their own tasks

---

## Tech Stack

- **Spring Boot 3.x**  
- **Spring Security** (JWT authentication)  
- **Spring Data JPA**  
- **Hibernate**  
- **MySQL** (depending on configuration)  
- **Lombok**  
- **Jakarta Validation**  

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3+
- Database (MySQL)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/Innovatureai/INV_RR_26_Noel-Cyriac.git
git checkout task2-dev
cd backend-assignment/Task2
```
## API Endpoints

### Authentication
| Method | Endpoint             | Description                              |
| ------ | -------------------- | ---------------------------------------- |
| POST   | `/api/auth/register` | Register a new user                      |
| POST   | `/api/auth/login`    | Login and get JWT tokens                 |
| POST   | `/api/auth/refresh`  | Refresh access token using refresh token |
| POST   | `/api/auth/logout`   | Logout user, invalidate tokens           |

### Tasks
| Method | Endpoint          | Description                          |
| ------ | ----------------- | ------------------------------------ |
| POST   | `/api/tasks`      | Create a new task                    |
| GET    | `/api/tasks`      | List all tasks of authenticated user |
| PUT    | `/api/tasks/{id}` | Update a task by ID                  |
| DELETE | `/api/tasks/{id}` | Delete a task by ID                  |

### Files
| Method | Endpoint                   | Description                          |
| ------ | -------------------------- | ------------------------------------ |
| POST   | `/api/files/upload`        | Upload a file (PDF/PNG/JPG)          |
| GET    | `/api/files/list`          | List all files of authenticated user |
| GET    | `/api/files/download/{id}` | Download file by ID                  |
| DELETE | `/api/files/{id}`          | Delete file by ID                    |

## Project Structure
```
src/main/java/com/nc/Task2
├── config
│   └── SecurityConfig.java
├── controller
│   ├── AuthController.java
│   ├── FileController.java
│   └── TaskController.java
├── dto
│   ├── FileResponse.java
│   ├── RegisterRequest.java
│   └── TaskResponse.java
├── entity
│   ├── FileEntity.java
│   ├── InvalidatedToken.java
│   ├── RefreshToken.java
│   ├── Task.java
│   └── User.java
├── exception
│   ├── FileAccessDeniedException.java
│   ├── FileNotFoundException.java
│   ├── FileTypeNotAllowedException.java
│   ├── GlobalExceptionHandler.java
│   ├── TaskAccessDeniedException.java
│   └── TaskNotFoundException.java
├── jwt
│   ├── JwtFilter.java
│   └── JwtService.java
├── repository
│   ├── FileRepository.java
│   ├── InvalidatedTokenRepository.java
│   ├── RefreshTokenRepository.java
│   ├── TaskRepository.java
│   └── UserRepository.java
└── service
    ├── AuthService.java
    ├── FileService.java
    └── TaskService.java
└── Task2Application.java (run this file)
src/main/resources
└── application.properties
.gitignore
pom.xml
```
