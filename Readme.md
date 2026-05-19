# 🔗 URL Shortener Service

A production-style backend application built using Spring Boot that allows users to shorten URLs, create custom aliases, track analytics, and manage their own links securely using JWT authentication.

The project focuses on backend engineering concepts such as authentication, caching, rate limiting, database optimization, Docker deployment, and REST API design.

---

# 🚀 Live Demo

## Swagger/OpenAPI Documentation

[Live Swagger Docs](https://urlshortener-fjvb.onrender.com/swagger-ui/index.html)

---

# ✨ Features

## 🔐 Authentication & Security

* User Registration & Login
* JWT-based Authentication
* Password hashing using BCrypt
* Protected APIs using Spring Security
* User-specific URL ownership

---

## 🔗 URL Shortening

* Generate short URLs using Base62 encoding
* Sequence-based unique key generation
* HTTP 302 redirection
* Custom alias support
* Expiry time for URLs

Example:

```text id="d3h5jp"
https://google.com
↓
https://urlshortener-fjvb.onrender.com/u/google
```

---

## 📊 Analytics

* Click count tracking
* URL creation timestamp
* Expiration tracking
* User-specific URL listing

---

## ⚡ Performance Optimizations

* Indexed database lookups
* In-memory caching using Spring Cache
* Fixed-window rate limiting
* Optimized read-heavy redirect flow

---

## 📦 API & Architecture

* RESTful API design
* Layered Architecture

  * Controller
  * Service
  * Repository
* DTO-based request/response handling
* Global exception handling
* Standardized API response wrapper

---

## 📖 API Documentation

* Swagger/OpenAPI integration
* Interactive API testing UI
* JWT authorization support inside Swagger

---

## 🐳 Deployment & DevOps

* Dockerized Spring Boot application
* Multi-stage Docker build
* Cloud deployment on Render
* Environment variable configuration
* PostgreSQL cloud integration

---

# 🛠 Tech Stack

| Category      | Technologies               |
| ------------- | -------------------------- |
| Language      | Java 17                    |
| Backend       | Spring Boot                |
| Security      | Spring Security, JWT       |
| Database      | PostgreSQL                 |
| ORM           | Spring Data JPA, Hibernate |
| Build Tool    | Maven                      |
| Documentation | Swagger / OpenAPI          |
| Caching       | Spring Cache               |
| Deployment    | Docker, Render             |

---

# 🧠 System Design Concepts Implemented

* Stateless JWT Authentication
* Read-heavy system optimization
* Database indexing
* Caching strategy
* Rate limiting
* URL expiration handling
* Sequence-based distributed-safe ID generation
* Layered backend architecture

---

# 📂 Project Structure

```text id="lcbjkn"
src/main/java/com/example/urlshortner
│
├── config
├── controller
├── dto
├── entity
├── exception
├── ratelimit
├── repository
├── security
├── service
└── util
```

---

# 🚀 Running Locally

## 1️⃣ Clone Repository

```bash id="s7zy6x"
git clone https://github.com/n200534/urlshortener-.git
```

---

## 2️⃣ Navigate To Project

```bash id="pqfdsp"
cd urlshortener-
```

---

## 3️⃣ Configure Environment Variables

Set:

```properties id="f5k17l"
DB_URL=
DB_USERNAME=
DB_PASSWORD=
BASE_URL=http://localhost:8080
```

---

## 4️⃣ Run Application

```bash id="n1r6v5"
./mvnw spring-boot:run
```

---

# 🐳 Run Using Docker

## Build & Start

```bash id="c4x3zh"
docker compose up --build
```

---

# 📌 Important API Endpoints

| Method | Endpoint                    | Description              |
| ------ | --------------------------- | ------------------------ |
| POST   | `/auth/register`            | Register user            |
| POST   | `/auth/login`               | Login & get JWT          |
| POST   | `/api/shorten`              | Create short URL         |
| GET    | `/u/{shortKey}`             | Redirect to original URL |
| GET    | `/api/analytics/{shortKey}` | Get URL analytics        |
| GET    | `/api/my-urls`              | Get user URLs            |

---

# 🔥 Example Shorten Request

```json id="hm2w9t"
{
  "longUrl": "https://google.com",
  "customAlias": "google",
  "expiryInMinutes": 60
}
```

---

# 📈 Future Improvements

* Redis distributed caching
* Refresh tokens
* Role-based authorization
* QR code generation
* Custom domain support
* Distributed rate limiting
* CI/CD pipeline

---

# 👨‍💻 Author

Akshay Kumar Amavarapu

* [GitHub Repository](https://github.com/n200534/urlshortener-)
* [Live API Docs](https://urlshortener-fjvb.onrender.com/swagger-ui/index.html)
