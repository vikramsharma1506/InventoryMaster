# InventoryMaster

A console-based Inventory Management System built with **Java 17**,**Maven**, **Lombok**, and **MySQL (JDBC)**.
Efficiently managed inventory, with full CRUD operations, ensuring seamless stock control and enhancing business processes.

---

## Project Structure

```
InventoryMaster/
├── pom.xml
└── src/
    ├── main/java/org/inventorymaster/
    │   ├── App.java                                  ← Main entry point
    │   ├── model/
    │   │   └── Product.java
    │   ├── repository/
    │   │   ├── ProductRepository.java                ← Interface
    │   │   ├── collection/
    │   │   │   └── ProductRepositoryImpl.java        
    │   │   └── mysql/
    │   │       └── ProductRepositoryImpl.java        ← MySQL via JDBC
    │   └── services/
    │       ├── ProductService.java
    │       └── ProductServiceImpl.java
    └── test/java/org/inventorymaster/
        └── ProductRepositoryTest.java
```

---

## Prerequisites

| Tool        | Version  |
|-------------|----------|
| Java (JDK)  | 17+      |
| Maven       | 3.8+     |
| MySQL       | 8.x      |

---

## Step 1 — MySQL Database Setup

Open MySQL Workbench or your terminal and run:

```sql
CREATE DATABASE IF NOT EXISTS inventory_master;
USE inventory_master;

CREATE TABLE IF NOT EXISTS product (
    id    INT PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    price INT          NOT NULL,
    stock INT          NOT NULL,
    unit  VARCHAR(50)  NOT NULL
);
```

---

## Step 2 — Configure Database Credentials

Open this file:

```
src/main/java/org/inventorymaster/repository/mysql/ProductRepositoryImpl.java
```

Update the constants at the top:

```java
private static final String DB_URL      = "jdbc:mysql://localhost:3306/inventory_master?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
private static final String DB_USER     = "root";
private static final String DB_PASSWORD = "your_password_here";   // ← change this
```

---

## Step 3 — Build & Run

```bash
# Build the project
mvn clean package

# Run the application
java -jar target/InventoryMaster-1.0-SNAPSHOT.jar
```

Or run directly from your IDE (IntelliJ / Eclipse / VS Code) by running `App.java`.

---

## Features

| # | Feature              |
|---|----------------------|
| 1 | Add Product          |
| 2 | Update Product       |
| 3 | Delete Product       |
| 4 | Find Product by ID   |
| 5 | Display All Products |
| 6 | Exit                 |
