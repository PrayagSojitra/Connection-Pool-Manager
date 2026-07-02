package main.java.com.banking.service;

import main.java.com.banking.infrastructure.pool.CustomConnectionPool;
import main.java.com.banking.repository.AccountRepository;
import main.java.com.banking.repository.TransactionRepository;
import main.java.com.banking.model.Account;

import java.sql.Connection;
import java.sql.SQLException;

public class BankingService {
    private final CustomConnectionPool pool;
    private final AccountRepository accountRepo;
    private final TransactionRepository transRepo;

    public BankingService(CustomConnectionPool pool) {
        this.pool = pool;
        this.accountRepo = new AccountRepository();
        this.transRepo = new TransactionRepository();
    }

    public void executeTransfer(int fromId, int toId, double amount) throws Exception {
        
        Connection conn = pool.getConnection();

        try {
            
            conn.setAutoCommit(false);

            Thread.sleep(2000); 

            Account fromAcc = accountRepo.getAccountById(conn, fromId);
            if (fromAcc == null || fromAcc.getBalance() < amount) {
                throw new Exception("Insufficient funds or invalid account: " + fromId);
            }

            // Debit
            accountRepo.updateBalance(conn, fromId, -amount);
            // Credit
            accountRepo.updateBalance(conn, toId, amount);
            // Log
            transRepo.logTransaction(conn, fromId, toId, amount, "TRANSFER");

            
            conn.commit();
            System.out.println("[SUCCESS] Transferred $" + amount + " from " + fromId + " to " + toId);

        } catch (Exception e) {
            
            if (conn != null) conn.rollback();
            System.err.println("[ROLLBACK] Transaction failed: " + e.getMessage());
            throw e;
        } finally {

            if (conn != null) {
                conn.setAutoCommit(true);
                pool.releaseConnection(conn);
            }
        }
    }
}