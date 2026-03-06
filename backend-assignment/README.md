# Task 1
This is a **Spring Boot REST API** project that provides basic arithmetic operations.
It exposes endpoints to perform mathematical calculations such as addition, subtraction, multiplication, and division.

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

## API Usage Examples
### Authentication APIs
<details>
<summary><b>1. Login</b></summary>
<br>
Authenticate the user and receive <b>access token</b> and <b>refresh token</b>.

**Endpoint**

```
POST /auth/login
```

**Request Body**

```json
{
  "username": "admin",
  "password": "password"
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


<details>
<summary><b>2. Refresh Access Token</b></summary>
<br>
Generate a new <b>access token</b> using a <b>refresh token</b>.

**Endpoint**

```
POST /auth/refresh
```

**Request Body**

```json
{
  "refreshToken": "your-refresh-token"
}
```

**Response**

```json
{
  "accessToken": "new-access-token"
}
```

</details>

---

### Arithmetic APIs

These APIs require a **JWT Access Token**.

Include the token in the request header:

```
Authorization: Bearer <access_token>
```

<details>
<summary><b>3. Addition</b></summary>
<br>
  
**Endpoint**

```
GET /api/add?a=5&b=3
```

**Response**

```
8
```

</details>

<details>
<summary><b>4. Subtraction</b></summary>
<br>
  
**Endpoint**

```
GET /api/subtract?a=10&b=4
```

**Response**

```
6
```

</details>

<details>
<summary><b>5. Multiplication</b></summary>
<br>
  
**Endpoint**

```
GET /api/multiply?a=6&b=7
```

**Response**

```
42
```

</details>

<details>
<summary><b>6. Division</b></summary>
<br>
  
**Endpoint**

```
GET /api/divide?a=20&b=5
```

**Response**

```
4
```

</details>

---

### Notes

- All `/api/*` endpoints require authentication using a **JWT access token**.
- The token must be sent in the `Authorization` header.
- If the access token expires, obtain a new one using the `/auth/refresh` endpoint.
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


