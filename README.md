
# E-Commerce REST API

A powerful and production-ready **E-Commerce Backend API** built using **Spring Boot**, featuring full user authentication, role-based authorization, product and order management, wishlist, reviews, and token blacklisting via Redis.

---

## Features

### Authentication & Authorization
- Register/Login with JWT
- Access & Refresh Tokens
- Role-Based Access Control (RBAC) for `ADMIN` and `USER`
- Redis-based token blacklisting for secure logout
- Password encryption using BCrypt
- Request validation using `@Valid`

---

### Product & Category Management
- Create, update, delete, and fetch products  
- Support for categories and subcategories  
- Link/unlink products with categories  
- Pagination-ready & scalable structure  
- Stock quantity management

---

### Cart & Order Handling
- Add/remove/modify cart items
- Create orders from cart
- Admin can view all orders
- Users can track their orders

---

### Reviews
- Add review to a product
- View your reviews or for specific products
- Admin can access/delete all reviews

---

### Wishlist
- Add/remove products from wishlist
- View your wishlist

---

### User Profile
- View your account data
- Change password or name
- Delete your account
- Admin can manage/delete users

---

## Redis Integration

- ✅ Token blacklisting (logout-safe)
- ✅ High-performance token tracking
- ✅ Ready for caching use cases

---

## Project Structure

```
src/
├── annotations/          # Custom annotations
├── controller/           # REST controllers for each resource
├── exception/            # Global & custom exception handling
├── model/
│   ├── dto/              # Request, Response & Slim DTOs
│   ├── entity/           # JPA Entities
│   └── mapper/           # Mapping logic between Entity & DTO
├── repository/           # Spring Data Repositories
├── security/             # JWT, filters, config, Redis
└── services/             # Business logic layer
```

---

## Tech Stack

| Layer        | Technology                |
|--------------|---------------------------|
| Language     | Java 21                   |
| Framework    | Spring Boot               |
| ORM          | Hibernate / JPA           |
| Security     | Spring Security + JWT     |
| Token Store  | Redis 🟥                  |
| DB           | MySQL / PostgreSQL        |
| Build Tool   | Maven                     |
| Docs         | Swagger / SpringDoc       |
| Validation   | Javax Validation API      |
| Logging      | SLF4J + Logback           |

---

## API Overview

| Resource   | Methods Covered                           |
|------------|--------------------------------------------|
| Auth       | `register`, `login`, `refresh`             |
| Product    | `CRUD`, link/unlink category               |
| Category   | `CRUD`, link products                      |
| Cart       | add, remove, modify item quantity          |
| Order      | create, view user/admin orders             |
| Review     | add, delete, get by product/user           |
| Wishlist   | add, remove, get                           |
| User       | update name/pass, delete, get info         |

Full Swagger documentation available at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Getting Started

```bash
git clone https://github.com/your-username/ecommerce-api.git
cd ecommerce-api
./mvnw spring-boot:run
```

1. Set DB creds & Redis config in `application.properties`
2. Start Redis server locally or connect to a cloud instance

---

## Roles

| Role    | Capabilities                                                                 |
|---------|------------------------------------------------------------------------------|
| User    | Browse, order, review, wishlist, update profile                              |
| Admin   | Manage products, users, categories, orders, reviews                          |

---

## Future Enhancements

- Payment Gateway integration (Stripe, PayPal)
- PDF invoice generator
- Email notifications
- Admin Dashboard (React/Angular frontend)
- Product filtering, search, and pagination
- Caching layer (Redis extension)

