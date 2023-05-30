import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ATM {
    private List<User> users;
    private User currentUser;
    
    public ATM() {
        users = new ArrayList<>();
        // Add some sample users for demonstration
        users.add(new User("12345", "0000", "Person1"));
        users.add(new User("98765", "1111", "Person2"));
        users.add(new User("13579", "2222", "Person3"));
    }
    
    public void start() {
        boolean quit = false;
        Scanner scanner = new Scanner(System.in);
        
        while (!quit) {
            System.out.println("Welcome to the ATM!");
            System.out.print("User ID: ");
            String userId = scanner.nextLine();
            
            System.out.print("User PIN: ");
            String pin = scanner.nextLine();
            
            currentUser = authenticateUser(userId, pin);
            
            if (currentUser != null) {
                System.out.println("Welcome, " + currentUser.getName() + "!");
                showMainMenu();
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        showTransactionHistory();
                        break;
                    case 2:
                        performWithdrawal(scanner);
                        break;
                    case 3:
                        performDeposit(scanner);
                        break;
                    case 4:
                        performTransfer(scanner);
                        break;
                    case 5:
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid user ID or PIN! Please try again.");
            }
        }
        
        System.out.println("Thank you for using the ATM. Goodbye!");
        scanner.close();
    }
    
    private User authenticateUser(String userId, String pin) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPin().equals(pin)) {
                return user;
            }
        }
        return null;
    }
    
    private void showMainMenu() {
        System.out.println("ATM Menu:");
        System.out.println("1. Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }
    
    private void showTransactionHistory() {
        List<Transaction> transactions = currentUser.getTransactions();
        System.out.println("Transaction History:");
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
    
    private void performWithdrawal(Scanner scanner) {
        System.out.print("Enter the withdrawal amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        currentUser.withdraw(amount);
    }
    
    private void performDeposit(Scanner scanner) {
        System.out.print("Enter the deposit amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        currentUser.deposit(amount);
    }
    
    private void performTransfer(Scanner scanner) {
        System.out.print("Enter the recipient's user ID: ");
        String recipientId = scanner.nextLine();
        
        System.out.print("Enter the transfer amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        
        User recipient = findUserById(recipientId);
        
        if (recipient != null) {
            currentUser.transfer(amount, recipient);
        } else {
            System.out.println("Recipient not found!");
        }
    }
    
    private User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}

class User {
    private String userId;
    private String pin;
    private String name;
    private List<Transaction> transactions;
    private Account account;
    
    public User(String userId, String pin, String name) {
        this.userId = userId;
        this.pin = pin;
        this.name = name;
        transactions = new ArrayList<>();
        account = new Account();
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getPin() {
        return pin;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    public void withdraw(double amount) {
        if (account.getBalance() >= amount) {
            account.withdraw(amount);
            Transaction transaction = new Transaction("Withdrawal", -amount);
            transactions.add(transaction);
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient balance!");
        }
    }
    
    public void deposit(double amount) {
        account.deposit(amount);
        Transaction transaction = new Transaction("Deposit", amount);
        transactions.add(transaction);
        System.out.println("Deposit successful.");
    }
    
    public void transfer(double amount, User recipient) {
        if (account.getBalance() >= amount) {
            account.withdraw(amount);
            recipient.account.deposit(amount);
            Transaction transaction = new Transaction("Transfer to " + recipient.getName(), -amount);
            transactions.add(transaction);
            recipient.transactions.add(new Transaction("Transfer from " + name, amount));
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient balance!");
        }
    }
}

class Transaction {
    private String type;
    private double amount;
    
    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }
    
    public String getType() {
        return type;
    }
    
    public double getAmount() {
        return amount;
    }
    
    @Override
    public String toString() {
        return type + ": " + amount;
    }
}

class Account {
    private double balance;
    
    public Account() {
        balance = 0.0;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void withdraw(double amount) {
        balance -= amount;
    }
    
    public void deposit(double amount) {
        balance += amount;
    }
}

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
