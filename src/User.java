public class User {
    private int userId;
    private String username;
    private String password;
    private String name;
    private int accountNumber;
    private double balance;

    // Constructor
    public User(int userId, String username, String password, String name, int accountNumber, double balance) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAccountNumber() { return accountNumber; }
    public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
