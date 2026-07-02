package main.java.com.banking.model;

public class Account {
    private int id;
    private String ownerName;
    private double balance;

    public Account(int id, String ownerName, double balance) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    // Getters
    public int getId() { return id; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() { return balance; }

    @Override
    public String toString() {
        return String.format("Account[ID=%d, Owner=%s, Balance=%.2f]", id, ownerName, balance);
    }
}
