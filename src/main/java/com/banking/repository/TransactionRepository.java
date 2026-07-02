package main.java.com.banking.repository;

import java.sql.*;

public class TransactionRepository {
    public void logTransaction(Connection conn, int fromId, int toId, double amount, String type) throws SQLException {
        String sql = "INSERT INTO transactions (from_account_id, to_account_id, amount, type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, fromId);
            pstmt.setInt(2, toId);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, type);
            pstmt.executeUpdate();
        }
    }
}