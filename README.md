# 🚚 Carrefour Delivery Kata

This project is a **Clean Architecture implementation** of the *Carrefour Java Kata* exercise.  
It provides a simple Spring Boot application that allows customers to **track their delivery status** and **update their delivery details** before the delivery becomes `READY`.

---

## 🧭 Overview

This application exposes REST APIs to:

- Retrieve a customer delivery by its ID.
- Update a delivery’s address or time slot **only when the delivery state is not yet `READY`**.

It follows a **Clean Architecture (a.k.a. Onion Architecture)** pattern to ensure a clear separation of concerns and high testability.

---

## 🏗️ Project Architecture

The project is divided into four main packages plus an entry point:

fr.kata.delivery
- adapters: Framework-dependent layer (Controllers + Persistence)
- application: Use cases (business logic orchestrators)
- config: Spring Boot configuration and bean wiring
- domain: Pure domain model and business rules
- KataDeliveryApplication.java:Main Spring Boot entry point


### 1. **Domain**
- Contains pure business logic (entities, value objects, enums, and domain services).
- No dependency on Spring, frameworks, or external libraries.
- Defines business rules such as:
    - Possible delivery states: `ACCEPTED`, `READY`, `DELAYED`, `DELIVERING`, `DELIVERED`.
    - Domain rules preventing updates once the delivery is `READY`.

### 2. **Application**
- Contains **use cases**, like:
    - Viewing a delivery’s status.
    - Updating a delivery’s details when allowed.
- Depends only on the **Domain layer**.
- Defines **ports (interfaces)** for persistence operations (e.g., `DeliveryRepository`).

### 3. **Adapters**
- Implements external concerns:
    - **Persistence adapter**: JPA repository using **H2 in-memory database**.
    - **Web adapter**: Spring Boot REST controller managing HTTP requests and responses.
- Converts between domain and external representations (DTOs, entities).

### 4. **Config**
- Spring Boot configuration layer.
- Instantiates and wires the application’s use cases and their adapters.
- Ensures that application and domain layers remain framework-independent.

### 5. **Main Application**
- The entry point (`KataDeliveryApplication`) that boots the Spring context.
- Located in the root package to enable component scanning.

---

## ⚙️ Technical Stack

| Component | Technology |
|------------|-------------|
| Language | Java 21 |
| Framework | Spring Boot |
| Build Tool | Maven |
| Database | H2 (in-memory) |
| Documentation | OpenAPI / Swagger UI |

---

## 🚀 Running the Application

### Prerequisites

Ensure that the following are installed on your system:
- **JDK 21**
- **Maven 3.9+**

### Steps

**Clone the repository**
```bash
   git clone <your-repo-url>
   cd carrefour-delivery-kata
```

**Build the project**
```bash
   mvn clean install
```

**Run the application**
```bash
   mvn spring-boot:run
```

## 🔍 API Documentation

The API is documented using **OpenAPI (Swagger UI)**.  
Once the application is running, you can access the documentation here:

[Link to swagger](http://localhost:8080/swagger-ui/index.html)

---

## 🧪 Testing

- Unit tests cover the **domain logic** and **application use cases**.
- Tests can be run using:
```bash
  mvn test
```

## 💡 Design Decisions

- **Clean Architecture (Onion)**: The application and domain layers are fully decoupled from Spring Boot.
- **Manual Bean Wiring**: Use cases are instantiated in the `config` package to avoid framework pollution in core logic.
- **Persistence Isolation**: The `DeliveryRepository` port abstracts away the storage implementation.
- **Domain Integrity**: Updates are validated by domain rules, ensuring consistent delivery states.

---

## 🌟 Possible Improvements

If more time were available, the following enhancements could be added:

- Add **event-driven notifications** (e.g., Kafka or WebSocket) when delivery status changes.
- Improve **error handling** and define global exception mappers.
- Add **validation annotations** for incoming requests.
- Extend API with additional customer operations (e.g., cancel a delivery).
- Add **real persistence** using PostgreSQL or MySQL.
- Containerize with **Docker**.

---

## 👨‍💻 Author

Developed by **Alex DA SILVA** as part of the Carrefour Java Kata exercise.

