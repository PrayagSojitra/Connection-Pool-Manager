package main.java.com.banking;

import main.java.com.banking.infrastructure.pool.CustomConnectionPool;
import main.java.com.banking.service.BankingService;
import main.java.com.banking.infrastructure.db.DBConfig;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=== STARTING DETAILED SYSTEM DEMONSTRATION ===");
            
            // 1. Initialize Pool with a SMALL size to force threads to wait
            // We use 2 connections so we can easily see the "Waiting" behavior.

            CustomConnectionPool pool = new CustomConnectionPool(DBConfig.MAX_POOL_SIZE);
            BankingService bankingService = new BankingService(pool);

            System.out.println("Configuration: Pool Size = " + DBConfig.MAX_POOL_SIZE);
            System.out.println("Action: Launching 6 concurrent transfer requests...\n");

            
            for (int i = 1; i <= 6; i++) {
                final int userId = i;
                Thread userThread = new Thread(() -> {
                    try {
                        System.out.println("[User-" + userId + "] Attempting to get connection...");
                        
                        
                        bankingService.executeTransfer(1, 2, 10.00);
                        
                        System.out.println("[User-" + userId + "] SUCCESS: Transfer complete.");
                    } catch (Exception e) {
                        System.err.println("[User-" + userId + "] FAILED: " + e.getMessage());
                    }
                });
                userThread.setName("Thread-User-" + userId);
                userThread.start();
            }

            System.out.println("\n--- Starting Real-Time Monitor ---");
            for (int i = 0; i < 12; i++) {
                
                pool.printDetailedStatus();
                
                
                Thread.sleep(1000); 
            }

            System.out.println("\n=== DEMONSTRATION COMPLETE ===");
            System.out.println("Final Database State can be checked in MySQL Workbench.");

        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}