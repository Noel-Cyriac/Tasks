# Task 2 

This is a **Spring Boot** application that provides user authentication with JWT, task management (CRUD operations), and file management (upload, download, list, delete). The project demonstrates secure REST APIs and JWT token handling.

---

## Table of Contents

- [Features](#features)  
- [Tech Stack](#tech-stack)  
- [Getting Started](#getting-started)  
- [API Endpoints](#api-endpoints)
- [API Usage Examples](#api-usage-examples)
- [Project Structure](#project-structure)  

---

## Features

1. **User Authentication**
   - Register with username and password
   - Login to receive access and refresh JWT tokens
   - Token refresh endpoint
   - Logout invalidates access and refresh tokens

2. **Task Management**
   - Create, read, update, delete tasks
   - Each task belongs to a user
   - Users can only access their own tasks
     
3. **File Management**
   - Upload PDF, PNG, JPG files (max 5MB)
   - List all uploaded files for authenticated user
   - Download a file
   - Delete a file

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
- Postman or any REST client (for testing API endpoints)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/Innovatureai/INV_RR_26_Noel-Cyriac.git
git checkout task2-dev
cd backend-assignment/Task2
```
---
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

---

## API Usage Examples

### Authentication APIs

<details>
<summary><b>1. Register User</b></summary>
<br>
Creates a new user account.

**Endpoint**

```
POST /api/auth/register
```

**Request Body**

```json
{
  "username": "john",
  "password": "password123",
  "confirmPassword": "password123"
}
```

**Response**

```json
"User registered successfully"
```

</details>

<br>

<details>
<summary><b>2. Login</b></summary>
<br>
   
Authenticates user and returns **access token** and **refresh token**.

**Endpoint**

```
POST /api/auth/login
```

**Request Body**

```json
{
  "username": "john",
  "password": "password123"
}
```

**Response**

```json
{
  "accessToken": "jwt-access-token",
  "refreshToken": "jwt-refresh-token"
}
```

</details>

<br>

<details>
<summary><b>3. Refresh Access Token</b></summary>
<br>
   
Generates a new **access token** using a valid refresh token.

**Endpoint**

```
POST /api/auth/refresh
```

**Request Body**

```json
{
  "refreshToken": "jwt-refresh-token"
}
```

**Response**

```json
{
  "accessToken": "new-jwt-access-token"
}
```

</details>

<br>

<details>
<summary><b>4. Logout</b></summary>
<br>
   
Logs out the user by invalidating the access token and deleting the refresh token.

**Endpoint**

```
POST /api/auth/logout
```

**Headers**

```
Authorization: Bearer <access_token>
```

**Response**

```json
"Logged out successfully"
```

</details>

---

### Task APIs

All task endpoints require:

```
Authorization: Bearer <access_token>
```

<details>
<summary><b>5. Create Task</b></summary>
<br>
   
Creates a new task for the authenticated user.

**Endpoint**

```
POST /api/tasks
```

**Request Body**

```json
{
  "title": "Complete Spring Boot project",
  "description": "Finish API implementation",
  "completed": false
}
```

**Response**

```json
{
  "id": 1,
  "title": "Complete Spring Boot project",
  "description": "Finish API implementation",
  "completed": false,
  "userId": 1
}
```

</details>

<br>

<details>
<summary><b>6. Get All Tasks</b></summary>
<br>
   
Retrieves all tasks belonging to the authenticated user.

**Endpoint**

```
GET /api/tasks
```

**Response**

```json
[
  {
    "id": 1,
    "title": "Complete Spring Boot project",
    "description": "Finish API implementation",
    "completed": false,
    "userId": 1
  }
]
```

</details>

<br>

<details>
<summary><b>7. Update Task</b></summary>
<br>

Updates a task owned by the authenticated user.

Fields can be **updated individually** — you may send **any combination of fields** (`title`, `description`, `completed`).  
You do **not need to include fields that are not being updated**. `id` and `userId` cannot be modified.

**Endpoint**

```
PUT /api/tasks/{id}
```

**Example:** *Update all fields*

**Request Body**

```json
{
  "title": "Updated title",
  "description": "Updated description",
  "completed": true
}
```

**Example:** *Update only one field*

**Request Body**

```json
{
  "completed": true
}
```

**Response**

```json
{
  "id": 1,
  "title": "Updated title",
  "description": "Updated description",
  "completed": true,
  "userId": 1
}
```

</details>
</details>

<br>

<details>
<summary><b>8. Delete Task</b></summary>
<br>
   
Deletes a task owned by the authenticated user.

**Endpoint**

```
DELETE /api/tasks/{id}
```

**Response**

```json
"Task deleted successfully"
```

</details>

---

### File APIs

All file endpoints require:

```
Authorization: Bearer <access_token>
```

Allowed file types:

```
PDF
PNG
JPG
```

Maximum file size:

```
5 MB
```

<details>
<summary><b>9. Upload File</b></summary>
<br>
   
Uploads a file for the authenticated user.

**Endpoint**

```
POST /api/files/upload
```

**Form Data**

```
file: <select file>
```

**Response**

```json
"File uploaded successfully: <generated_filename>"
```

</details>

<br>

<details>
<summary><b>10. List Files</b></summary>
<br>
   
Lists all files uploaded by the authenticated user.

**Endpoint**

```
GET /api/files/list
```

**Response**

```json
[
  {
    "id": 1,
    "fileName": "document.pdf",
    "filePath": "/uploads/uuid_document.pdf",
    "fileType": "application/pdf",
    "uploadTime": "2026-03-05T10:20:30Z",
    "userId": 1
  }
]
```

</details>

<br>

<details>
<summary><b>11. Download File</b></summary>
<br>
   
Downloads a file owned by the authenticated user.

**Endpoint**

```
GET /api/files/download/{id}
```

**Response**

File download with proper **Content-Type** and **Content-Disposition** headers.

</details>

<br>

<details>
<summary><b>12. Delete File</b></summary>
<br>
   
Deletes a file owned by the authenticated user.

**Endpoint**

```
DELETE /api/files/{id}
```

**Response**

```json
"File deleted successfully"
```

</details>

***

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
└── Task2Application.java ← (**Run this file to start the application**)
src/main/resources
└── application.properties
.gitignore
pom.xml
```
