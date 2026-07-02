package main.java.com.banking.infrastructure.pool;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection() throws Exception;
    void releaseConnection(Connection connection);
    void shutdown() throws Exception;
}