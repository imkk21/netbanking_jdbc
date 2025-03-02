import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankOperations {
    
    // Register new user
    public static boolean registerUser(String username, String password, String name, int accountNumber, double initialBalance) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (username, password, name, accountNumber, balance) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password); // Use hashed password in production
            stmt.setString(3, name);
            stmt.setInt(4, accountNumber);
            stmt.setDouble(5, initialBalance);
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Returns true if the user was registered successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update balance for a user
    public static boolean updateBalance(int userId, double newBalance) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE users SET balance = ? WHERE userId = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, userId);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Returns true if the balance was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get user balance
    public static double getBalance(int userId) {
        double balance = 0.0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT balance FROM users WHERE userId = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                balance = rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    // Fund transfer between users
    public static boolean transferFunds(int senderId, int targetId, double amount) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false); // Start transaction
            
            // Update sender balance
            if (!updateBalance(senderId, getBalance(senderId) - amount)) {
                connection.rollback();
                return false; // Rollback if balance update fails
            }
            
            // Update target balance
            if (!updateTargetBalance(targetId, getBalance(targetId) + amount)) {
                connection.rollback();
                return false; // Rollback if target balance update fails
            }
            
            // Save transactions
            saveTransaction(senderId, amount, "Debit");
            saveTransaction(targetId, amount, "Credit");
            
            connection.commit(); // Commit transaction
            return true; // Transfer successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update target balance
    public static boolean updateTargetBalance(int targetUserId, double newBalance) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE users SET balance = ? WHERE userId = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, targetUserId);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Returns true if the target balance was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Save transaction details
    public static boolean saveTransaction(int userId, double amount, String transactionType) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO transactions (userId, amount, transactionType) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setDouble(2, amount);
            stmt.setString(3, transactionType);
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Returns true if the transaction was saved
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get transaction history for a user
    public static List<String> getTransactionHistory(int userId) {
        List<String> transactions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM transactions WHERE userId = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String transaction = "Transaction ID: " + rs.getInt("transactionId") +
                                     ", Amount: " + rs.getDouble("amount") +
                                     ", Type: " + rs.getString("transactionType") +
                                     ", Date: " + rs.getTimestamp("timestamp");
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
