package main.java.com.banking.repository;

import main.java.com.banking.model.Account;
import java.sql.*;

public class AccountRepository {

    // Note: We pass the connection as a parameter so the Service Layer can control the transaction.
    public void updateBalance(Connection conn, int accountId, double amount) throws SQLException {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, accountId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Account update failed, no rows affected.");
            }
        }
    }

    public Account getAccountById(Connection conn, int accountId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(rs.getInt("id"), rs.getString("owner_name"), rs.getDouble("balance"));
                }
            }
        }
        return null;
    }
}
