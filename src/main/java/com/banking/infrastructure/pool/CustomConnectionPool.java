package main.java.com.banking.infrastructure.pool;

import main.java.com.banking.infrastructure.db.DBConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomConnectionPool {
    private final BlockingQueue<Connection> pool;
    private final AtomicInteger activeConnections = new AtomicInteger(0);
    private final AtomicInteger waitingThreads = new AtomicInteger(0);

    
    public CustomConnectionPool(int size) throws SQLException {
        this.pool = new LinkedBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            pool.add(createNewConnection());
        }
    }

    private Connection createNewConnection() throws SQLException {
        return DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
    }



    public Connection getConnection() throws InterruptedException {
    // Increment waiting count before we try to take from the queue
    waitingThreads.incrementAndGet();
    
    System.out.println(Thread.currentThread().getName() + " is WAITING for a connection...");
    
    Connection conn = pool.take(); // This blocks the thread
    
    // Decrement once we have the connection
    waitingThreads.decrementAndGet();
    activeConnections.incrementAndGet();
    
    return conn;
}

    public void releaseConnection(Connection conn) {
        if (conn != null) {
            activeConnections.decrementAndGet();
            pool.offer(conn); // Returns it to the pool
        }
    }

    
    public void printDetailedStatus() {
    System.out.println("\n--- POOL STATUS REPORT ---");
    System.out.println("Available (Idle): " + pool.size());
    System.out.println("Busy (Active):    " + activeConnections.get());
    System.out.println("Threads Waiting:  " + waitingThreads.get());
    System.out.println("Total:  " + (pool.size() + activeConnections.get()));
    System.out.println("--------------------------\n");
    }
}
