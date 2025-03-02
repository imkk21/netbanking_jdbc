import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BankUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField targetUserField;
    private JTextField transferAmountField;
    private int loggedInUserId;

    public BankUI() {
        frame = new JFrame("Net Banking Application");
        panel = new JPanel();

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        panel.add(passwordField);
        
// checks login credentials and creats the session 
        JButton loginButton = new JButton("Login");
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e) {            //actionPerformed is overwritten
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (loginUser(username, password)) {   //calls the loginuser method which is below this code
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    showUserOptions();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password!");
                }
            }
        });

        //frame design
        frame.add(panel);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // options for user
    private void showUserOptions() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();

        //balance button
        JButton balanceButton = new JButton("Check Balance");
        panel.add(balanceButton);
        balanceButton.addActionListener(new ActionListener() {  
           
            public void actionPerformed(ActionEvent e) {         //actionPerformed is overwritten
                double balance = BankOperations.getBalance(loggedInUserId);
                JOptionPane.showMessageDialog(frame, "Your balance is: " + balance);
            }
        });

        // transferred amount details
        targetUserField = new JTextField(20);
        transferAmountField = new JTextField(20);
        panel.add(new JLabel("Transfer to User ID:"));
        panel.add(targetUserField);
        panel.add(new JLabel("Amount:"));
        panel.add(transferAmountField);


        JButton transferButton = new JButton("Transfer Funds");
        panel.add(transferButton);
        transferButton.addActionListener(new ActionListener() {
         
            public void actionPerformed(ActionEvent e) {             //actionPerformed is overwritten
                int targetId = Integer.parseInt(targetUserField.getText());
                double amount = Double.parseDouble(transferAmountField.getText());
                if (BankOperations.transferFunds(loggedInUserId, targetId, amount)) {
                    JOptionPane.showMessageDialog(frame, "Transfer successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Transfer failed!");
                }
            }
        });

        // Transaction History 
        JButton historyButton = new JButton("View Transaction History");
        panel.add(historyButton);
        historyButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(ActionEvent e) {             //actionPerformed is overwritten
                List<String> transactions = BankOperations.getTransactionHistory(loggedInUserId);
                if (transactions.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No transactions found.");
                } else {
                    StringBuilder history = new StringBuilder("Transaction History:\n");
                    for (String transaction : transactions) {
                        history.append(transaction).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, history.toString());
                }
            }
        });

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
      
            public void actionPerformed(ActionEvent e) {                 //actionPerformed is overwritten
                frame.dispose(); // Close current frame
                new BankUI(); // Start again
            }
        });

        // update the panel to show new components
        panel.revalidate();
        panel.repaint();
    }

    // User login method
    private boolean loginUser(String username, String password) { 

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT userId FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password); 
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loggedInUserId = rs.getInt("userId");
                return true; // if user is present
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // if user is not present
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankUI::new);
    }
}
