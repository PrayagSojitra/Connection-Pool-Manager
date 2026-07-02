package main.java.com.banking.model;

import java.time.LocalDateTime;

public class TransactionRecord {
    private int id;
    private int fromAccountId;
    private int toAccountId;
    private double amount;
    private String type;
    private LocalDateTime timestamp;

    public TransactionRecord(int id, int fromId, int toId, double amount, String type) {
        this.id = id;
        this.fromAccountId = fromId;
        this.toAccountId = toId;
        this.amount = amount;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getType() { return type; }

    @Override
    public String toString() {
        return String.format("Transaction #%d: %s of $%.2f from %d to %d", 
                              id, type, amount, fromAccountId, toAccountId);
    }
}