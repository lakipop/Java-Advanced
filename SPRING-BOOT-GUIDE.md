# 🍃 Spring Boot CRUD Application - Complete Guide

## Overview
This is a complete **Spring Boot 3.5.0** REST API application with **CRUD operations**, **JPA/Hibernate**, and **MySQL database** integration.

**Original Source:** Spring CRUD.rar

---

## 📊 Project Details

### Technology Stack
| Technology | Version | Purpose |
|------------|---------|---------|
| **Spring Boot** | 3.5.0 | Application framework |
| **Java** | 21 | Programming language |
| **Spring Data JPA** | 3.5.0 | Database operations |
| **Hibernate** | (included) | ORM framework |
| **MySQL** | 8.0+ | Database |
| **Lombok** | (latest) | Reduce boilerplate code |
| **Maven** | 3.8+ | Build tool |

---

## 🗂️ Project Structure

```
Spring CRUD/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── Controller/
│   │   │   │   └── UserController.java        # REST endpoints
│   │   │   ├── Models/
│   │   │   │   └── User.java                   # Entity class
│   │   │   ├── Repo/
│   │   │   │   └── UserRepository.java         # JPA repository
│   │   │   ├── Service/
│   │   │   │   └── userService.java            # Business logic
│   │   │   ├── DTO/
│   │   │   │   └── userDTO.java                # Data Transfer Object
│   │   │   └── DemoApplication.java            # Main application
│   │   └── resources/
│   │       └── application.properties          # Configuration
│   └── test/                                   # Test files
├── pom.xml                                     # Maven dependencies
└── README.md                                   # This file
```

---

## 📝 Key Components Explained

### 1. **User Entity** (`Models/User.java`)
```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private int id;
    private String name;
    private int age;
}
```

**Annotations:**
- `@Entity` - JPA entity, maps to database table
- `@Data` - Lombok: generates getters, setters, toString
- `@AllArgsConstructor` - Lombok: creates constructor with all fields
- `@NoArgsConstructor` - Lombok: creates no-arg constructor
- `@Id` - Primary key field

### 2. **REST Controller** (`Controller/UserController.java`)
```java
@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class UserController {
    @Autowired
    private userService userService;
    
    // Endpoints defined here
}
```

**Annotations:**
- `@RestController` - Marks class as REST controller
- `@CrossOrigin` - Enables CORS for frontend access
- `@RequestMapping` - Base URL path for all endpoints
- `@Autowired` - Dependency injection

---

## 🔌 API Endpoints

### Base URL
```
http://localhost:8080/api
```

### Available Endpoints

#### **1. Get All Users**
```http
GET /api/allusers
```
**Response:** List of User objects
```json
[
  {"id": 1, "name": "John Doe", "age": 25},
  {"id": 2, "name": "Jane Smith", "age": 30}
]
```

#### **2. Get All Users (DTO)**
```http
GET /api/alluserDTO
```
**Response:** List of userDTO objects

#### **3. Get User by ID**
```http
GET /api/findbyid/{id}
```
**Example:** `GET /api/findbyid/1`
**Response:** Single User object

#### **4. Get User by Name**
```http
GET /api/getbyname/name?name=John
```
**Response:** Single User object

#### **5. Add New User**
```http
POST /api/adduser
Content-Type: application/json

{
  "id": 3,
  "name": "Alice Johnson",
  "age": 28
}
```
**Response:** userDTO object

#### **6. Update User**
```http
PUT /api/updateuser/{id}
Content-Type: application/json

{
  "name": "Updated Name",
  "age": 35
}
```
**Response:** Updated User object

#### **7. Delete User**
```http
DELETE /api/removeuser/{id}
```
**Response:** Confirmation message (String)

---

## ⚙️ Setup Instructions

### Prerequisites
1. ✅ **JDK 21** installed
2. ✅ **Maven** installed
3. ✅ **MySQL** server running
4. ✅ **IDE** (IntelliJ IDEA recommended)

### Step 1: Database Setup
```sql
-- Create database
CREATE DATABASE springcrud_db;

-- Create user table (auto-created by Hibernate)
-- Or manually:
CREATE TABLE user (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    age INT
);

-- Insert sample data
INSERT INTO user (id, name, age) VALUES
(1, 'John Doe', 25),
(2, 'Jane Smith', 30),
(3, 'Bob Johnson', 35);
```

### Step 2: Configure Application
Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/springcrud_db
spring.datasource.username=root
spring.datasource.password=your_password

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Server Configuration
server.port=8080
```

### Step 3: Build and Run
```bash
# Navigate to project directory
cd "Spring CRUD/Spring CRUD"

# Build with Maven
mvn clean install

# Run application
mvn spring-boot:run

# Or run from IDE:
# Right-click DemoApplication.java → Run
```

### Step 4: Test Endpoints
```bash
# Using curl
curl http://localhost:8080/api/allusers

# Using Postman
# Import endpoints from documentation above
```

---

## 🧪 Testing with cURL

### Get All Users
```bash
curl http://localhost:8080/api/allusers
```

### Get User by ID
```bash
curl http://localhost:8080/api/findbyid/1
```

### Add New User
```bash
curl -X POST http://localhost:8080/api/adduser \
  -H "Content-Type: application/json" \
  -d '{"id":4,"name":"Alice","age":28}'
```

### Update User
```bash
curl -X PUT http://localhost:8080/api/updateuser/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Updated John","age":26}'
```

### Delete User
```bash
curl -X DELETE http://localhost:8080/api/removeuser/4
```

---

## 📚 Key Concepts Demonstrated

### 1. **Spring Boot Architecture**
- ✅ MVC pattern (Model-View-Controller)
- ✅ Layered architecture (Controller → Service → Repository)
- ✅ Dependency Injection
- ✅ IoC (Inversion of Control)

### 2. **Spring Data JPA**
- ✅ Entity mapping with annotations
- ✅ JpaRepository for CRUD operations
- ✅ Custom query methods
- ✅ Hibernate ORM integration

### 3. **RESTful API Design**
- ✅ HTTP methods (GET, POST, PUT, DELETE)
- ✅ Path variables and request parameters
- ✅ JSON request/response
- ✅ CORS configuration

### 4. **Lombok**
- ✅ `@Data` - Getters/Setters
- ✅ `@AllArgsConstructor` - Full constructor
- ✅ `@NoArgsConstructor` - Empty constructor
- ✅ Reduced boilerplate code

### 5. **DTO Pattern**
- ✅ Data Transfer Objects
- ✅ Separation of concerns
- ✅ API response customization

---

## 🔍 Dependencies (pom.xml)

```xml
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- MySQL Driver -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

---

## 🚀 Advanced Features

### Custom Query Methods
Add to `UserRepository.java`:
```java
public interface UserRepository extends JpaRepository<User, Integer> {
    // Find by name
    User findByName(String name);
    
    // Find by age greater than
    List<User> findByAgeGreaterThan(int age);
    
    // Custom JPQL query
    @Query("SELECT u FROM User u WHERE u.age BETWEEN :min AND :max")
    List<User> findByAgeBetween(@Param("min") int min, @Param("max") int max);
}
```

### Exception Handling
Add global exception handler:
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
```

---

## 📖 Learning Resources

### Official Documentation
- **Spring Boot:** https://spring.io/projects/spring-boot
- **Spring Data JPA:** https://spring.io/projects/spring-data-jpa
- **Lombok:** https://projectlombok.org/

### Tutorials
- Spring Boot REST API Tutorial
- JPA/Hibernate Beginner Guide
- Maven Project Structure

---

## ✅ Checklist for Students

- [ ] Understand MVC architecture
- [ ] Learn Spring annotations (`@RestController`, `@Service`, `@Repository`)
- [ ] Master JPA entity mapping
- [ ] Practice REST API design
- [ ] Test with Postman/cURL
- [ ] Understand dependency injection
- [ ] Learn Lombok annotations
- [ ] Explore custom queries

---

## 🎓 Assignment Ideas

1. **Add validation** - Use `@Valid` and `@NotNull` annotations
2. **Implement pagination** - Add `Pageable` support
3. **Add authentication** - Integrate Spring Security
4. **Create DTOs** - Separate request/response models
5. **Add logging** - Use SLF4J logger
6. **Write tests** - JUnit + MockMvc tests

---

## 🏆 Best Practices Demonstrated

✅ **Layered Architecture** - Clean separation of concerns  
✅ **Dependency Injection** - Loose coupling  
✅ **RESTful Design** - Standard HTTP methods  
✅ **Entity Mapping** - JPA annotations  
✅ **Code Reusability** - Service layer abstraction  
✅ **Lombok Integration** - Reduced boilerplate  

---

*Spring Boot Version: 3.5.0*  
*Java Version: 21*  
*Last Updated: Current Session*  
*Source: Spring CRUD.rar (extracted)*
