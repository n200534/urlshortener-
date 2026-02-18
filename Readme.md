
# 🔗 URL Shortener Service

A scalable and production-style URL shortening service built using **Spring Boot** and **PostgreSQL**.

Supports custom aliases, expiration logic, click analytics, and structured API responses.

Designed with clean architecture and system design principles suitable for SDE-1 backend roles.

---

## 🚀 Features

* 🔗 Shorten long URLs
* 🏷 Custom alias support
* ⏳ Expiration time support
* 📊 Click analytics tracking
* ⚡ Indexed database lookups for fast redirects
* 🧠 Sequence-based unique ID generation (collision-free)
* 🧱 Layered architecture (Controller → Service → Repository)
* 🛡 Global exception handling
* 📦 Standardized API response wrapper
* 🗄 PostgreSQL persistence

---

## 🏗 Architecture Overview

### High-Level Architecture

```
Client
   ↓
Spring Boot Application
   ↓
PostgreSQL Database
```

---

### Redirect Flow (Read-Heavy Optimized)

```
GET /u/{shortKey}
        ↓
Find by indexed short_key
        ↓
Increment click count
        ↓
HTTP 302 Redirect to original URL
```

The `short_key` column is indexed to optimize lookup performance for redirect operations.

---

## 🧠 Design Decisions

### 1️⃣ Unique Short Key Generation

* Uses PostgreSQL sequence for globally unique ID generation.
* Encodes numeric ID using Base62.
* Guarantees no collisions.
* Avoids hash collision risks.

---

### 2️⃣ Custom Alias Support

* Optional custom short key.
* Enforced uniqueness via database constraint.
* Returns conflict if alias already exists.

---

### 3️⃣ Expiration Logic

* Optional expiry time per URL.
* Expired URLs are blocked at redirect time.
* Business validation handled in service layer.

---

### 4️⃣ Analytics Tracking

* Tracks total redirect count.
* Analytics endpoint provides usage insights.

---

## 📊 API Endpoints

---

### 🔹 Create Short URL

**POST** `/api/shorten`

Request:

```json
{
  "longUrl": "https://example.com",
  "customAlias": "myalias",
  "expiryInMinutes": 60
}
```

Response:

```json
{
  "success": true,
  "message": "URL shortened successfully",
  "data": "http://localhost:8080/u/myalias"
}
```

---

### 🔹 Redirect

**GET** `/u/{shortKey}`

Returns HTTP 302 redirect to the original URL.

---

### 🔹 Analytics

**GET** `/api/analytics/{shortKey}`

Response:

```json
{
  "shortKey": "abc123",
  "longUrl": "https://example.com",
  "clickCount": 15,
  "createdAt": "2026-02-18T09:00:00",
  "expiresAt": "2026-02-18T10:00:00"
}
```

---

## 🗄 Database Schema

Table: `url_mapping`

| Column      | Type      | Description      |
| ----------- | --------- | ---------------- |
| id          | BIGINT    | Primary Key      |
| long_url    | TEXT      | Original URL     |
| short_key   | VARCHAR   | Unique short key |
| created_at  | TIMESTAMP | Creation time    |
| expires_at  | TIMESTAMP | Expiration time  |
| click_count | BIGINT    | Redirect counter |

---

## 🛠 Tech Stack

* Java 17
* Spring Boot 3
* Spring Data JPA
* PostgreSQL
* Hibernate
* Lombok
* Maven

---

## 🧪 How to Run Locally

1. Clone the repository
2. Start PostgreSQL
3. Update `application.yml` with DB credentials
4. Run:

```bash
mvn spring-boot:run
```

Application runs at:

```
http://localhost:8080
```

---

## 📈 Scalability Considerations

* Indexed lookup for fast redirect queries
* Read-heavy system optimization awareness
* Database sequence ensures global uniqueness
* Designed for horizontal scaling with load balancer support
* Ready to integrate caching layer (e.g., Redis)

---

## 🔮 Future Enhancements

* Add Redis caching for redirect optimization
* Add rate limiting
* Add Swagger / OpenAPI documentation
* Docker containerization
* Cloud deployment
* Monitoring & logging improvements

---

