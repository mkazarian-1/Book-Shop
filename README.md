# Book Shop API

## 📌 Introduction
The **Book Shop API** is a RESTful web service designed to manage books, categories, orders, and shopping carts efficiently. This API serves as the backend for an online bookstore, enabling users to browse books, manage their shopping carts, and place orders. Admins have additional privileges to manage books, categories, and orders.

## 🚀 Features
- **User Authentication & Authorization**: Secured endpoints with role-based access using Spring Security.
- **Book Management**: CRUD operations for books with pagination and sorting.
- **Category Management**: Manage book categories and retrieve books under a category.
- **Shopping Cart**: Add, update, and remove books from the cart.
- **Order Processing**: Users can place orders and view order history, while admins can manage all orders.
- **API Documentation**: Integrated Swagger UI for testing and exploring the API.

## 🛠️ Tech Stack
- **Spring Boot** – Main framework for building the API.
- **Spring Security** – User authentication and authorization.
- **Spring Data JPA** – Database interactions using Hibernate.
- **MySQL** – Relational database for storing books, orders, and users.
- **Swagger** – API documentation and testing.
- **Lombok** – Reduces boilerplate code for entities and services.
- **Liquibase** – Database schema version control.
- **Docker** Containerization tool for easy deployment.

## 📑 Endpoints Overview

### 📚 Book Controller
| Method | Endpoint | Description | Required Role |
|--------|----------|-------------|---------------|
| GET | `/books` | Get all books (pagination & sorting) | USER |
| GET | `/books/{id}` | Get book by ID | USER |
| GET | `/books/search` | Search books by parameters | USER |
| POST | `/books` | Add a new book | ADMIN |
| PUT | `/books/{id}` | Update a book | ADMIN |
| DELETE | `/books/{id}` | Delete a book | ADMIN |

### 🏷️ Category Controller
| Method | Endpoint | Description | Required Role |
|--------|----------|-------------|---------------|
| GET | `/categories` | Get all categories (pagination & sorting) | USER |
| GET | `/categories/{id}/book` | Get books in a category | USER |
| GET | `/categories/{id}` | Get category by ID | USER |
| POST | `/categories` | Add a new category | ADMIN |
| PUT | `/categories/{id}` | Update a category | ADMIN |
| DELETE | `/categories/{id}` | Delete a category | ADMIN |

### 🛒 Shopping Cart Controller
| Method | Endpoint | Description | Required Role |
|--------|----------|-------------|---------------|
| GET | `/cart` | Get shopping cart | USER |
| POST | `/cart` | Add book to cart | USER |
| PUT | `/cart/items/{cartItemId}` | Update cart item quantity | USER |
| DELETE | `/cart/items/{cartItemId}` | Remove item from cart | USER |

### 📦 Order Controller
| Method | Endpoint | Description | Required Role |
|--------|----------|-------------|---------------|
| POST | `/orders` | Create an order from the cart | USER |
| GET | `/orders` | Get order history | USER |
| GET | `/orders/{orderId}/items` | Get items in an order | USER |
| GET | `/orders/{orderId}/items/{itemId}` | Get a specific item in an order | USER |
| PATCH | `/orders/{id}` | Update order status | ADMIN |
| GET | `/orders/all` | Get all orders | ADMIN |

## 🛠️ Setup & Installation
### Prerequisites
- Java 21
- Maven 3.3.3
- MySQL 8.3.0 (installed and running)

### Steps
1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-repo/Book-Shop.git
   cd book-shop
   ```
2. **Configure the Database**
   Update `application.properties` with your MySQL credentials:
   ```properties
    spring.datasource.url=jdbc:h2:mem:///book_store
    spring.datasource.username=sa
    spring.datasource.password=password
    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    
    jwt.secret="someRandomSecretKeyThatIsAtLeast32BytesLong"
    jwt.expiration=1000000
   ```
3. **Build and Run the Application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. **Access Swagger UI**
   Open [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to explore and test the API.

## 🐳 Running the Project with Docker

Make sure you have **Docker** and **Docker Compose** installed.

1. **Create docker image:**

   ```bash
   git clone https://github.com/mkazarian-1/Book-Shop.git
   cd book-shop
   ```

2. **Set Up Environment Variables**
   Create a `.env` file in the root directory of the project and specify the following variables:
   ```env
   # Database Configuration
   MYSQL_DATABASE=book_shop
   MYSQL_USER=your_username
   MYSQL_PASSWORD=your_password
   MYSQL_ROOT_PASSWORD=your_root_password

   # Spring Boot Application Configuration
   SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/book_shop
   SPRING_DATASOURCE_USERNAME=your_username
   SPRING_DATASOURCE_PASSWORD=your_password
   JWT_SECRET=your_random_secret_key_at_least_32_bytes_long
   JWT_EXPIRATION=1000000

   MYSQLDB_USER=root
   MYSQLDB_PASSWORD=root
   MYSQLDB_DATABASE=book_shop
   MYSQLDB_LOCAL_PORT=3307
   MYSQLDB_DOCKER_PORT=3306
   
   SPRING_LOCAL_PORT=8088
   SPRING_DOCKER_PORT=8080
   DEBUG_PORT=5005
   ```

3. **Build and start the services:**

   ```bash
   docker-compose up --build -d
   ```

4. **Verify the containers are running:**

   ```bash
   docker ps
   ```
   
5. **Access the API at:**

   ```
   http://localhost:8080/swagger-ui/index.html
   ```
