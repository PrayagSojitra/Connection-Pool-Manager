# Custom JDBC Connection Pool & Banking Ledger System

A high-performance Java backend infrastructure project demonstrating a custom database connection pool manager and transactional integrity in a concurrent banking environment.

## 🚀 The Engineering Problem
In high-load backend systems, opening a new database connection for every request is expensive and doesn't scale. This project solves that by implementing a **thread-safe, bounded connection pool** that reuses existing connections, manages contention, and ensures data consistency (ACID) during complex financial transactions.

## 🛠️ Key Technical Features
- **Custom Connection Pool**: Built using `BlockingQueue` to handle thread synchronization and resource reuse.
- **Transaction Management**: Manual JDBC control using `setAutoCommit(false)`, `commit()`, and `rollback()` to ensure atomicity.
- **Concurrency Control**: Designed to handle multiple simultaneous requests by "throttling" database access via the pool.
- **Observability**: Real-time monitoring of Active vs. Idle connections and thread waiting counts.
- **Layered Architecture**: Clean separation between Infrastructure, Repository, Service, and Model layers.

## 🏗️ System Architecture
```text
[Client/Main] -> [BankingService] -> [AccountRepository] -> [MySQL]
                       |                    |
                [CustomConnectionPool] <----+ (Borrow/Release)

```

## 📋 Project Structure
- **infrastructure.pool**: Core logic for the resource manager and pool metrics.
- **service**: Business logic layer where transaction boundaries are defined.
- **repository**: Data access layer executing raw SQL queries.
- **exceptions**: Custom business and infrastructure error handling.
- **model**: POJOs representing the database schema.

## 🚦 Setup & Installation
- **Database**: Run the `resources/db-setup.sql` in MySQL.
- **Driver**: Ensure `mysql-connector-java.jar` is in the `lib/` folder.
- **Configuration**: Update `infrastructure/db/DBConfig.java` with your MySQL credentials.
- **Compile**: `javac -d bin -cp "lib/*;src" src/main/java/com/banking/Main.jav`
- **Run**: `java -cp "bin;lib/*" main.java.com.banking.Main`

## 📊 Observability Metrics
### The system tracks:
- **Active Connections**: Connections currently used by a transaction.
- **Idle Connections**: Connections waiting in the pool.
- **Thread Waiting**: Threads blocked because the pool is exhausted.

