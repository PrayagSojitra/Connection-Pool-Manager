package main.java.com.banking.infrastructure.pool;

public class PoolMetrics {
    public final int activeConnections;
    public final int idleConnections;
    public final int totalSize;

    public PoolMetrics(int active, int idle, int total) {
        this.activeConnections = active;
        this.idleConnections = idle;
        this.totalSize = total;
    }

    @Override
    public String toString() {
        return String.format("[METRICS] Active: %d | Idle: %d | Total: %d", 
                              activeConnections, idleConnections, totalSize);
    }
}