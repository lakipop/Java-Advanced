/**
 * BankingSynchronizationDemo - Advanced Thread Synchronization
 * 
 * This program demonstrates:
 * - Synchronized methods
 * - wait() and notify() for inter-thread communication
 * - Producer-Consumer pattern
 * - Thread coordination in banking scenario
 * - Avoiding deadlock and race conditions
 * 
 * Original: MyBankingDemo practical
 * 
 * Scenario: Multiple customers depositing and withdrawing from shared account
 * 
 * @author BICT Advanced Java Course
 * @version 1.0
 */

/**
 * Account - Shared resource representing bank account
 */
class Account {
    private double balance = 0.0;
    
    public synchronized double getBalance() {
        return balance;
    }
    
    public synchronized void setBalance(double balance) {
        this.balance = balance;
    }
    
    /**
     * Display current balance with formatting
     */
    public synchronized void displayBalance() {
        System.out.printf("💰 Current Balance: $%.2f%n", balance);
    }
}

/**
 * Customer - Represents a bank customer performing transactions
 */
class Customer {
    private double pendingWithdrawal = 0.0;
    private final Account account;
    private final String customerName;
    
    public Customer(Account account, String customerName) {
        this.account = account;
        this.customerName = customerName;
    }
    
    /**
     * Withdraw money from account (synchronized with wait/notify)
     * If insufficient balance, wait until deposit occurs
     */
    public synchronized void withdrawMoney(double amount) {
        System.out.println("\n🏦 [" + customerName + "] Attempting to withdraw: $" + amount);
        
        double currentBalance = account.getBalance();
        pendingWithdrawal = amount;
        
        // Wait if insufficient balance
        while (currentBalance < amount) {
            System.out.println("⚠️  [" + customerName + "] Insufficient balance!");
            System.out.printf("   Available: $%.2f | Required: $%.2f%n", 
                currentBalance, amount);
            System.out.println("   Waiting for deposit...");
            
            try {
                wait(); // Release lock and wait for notify
            } catch (InterruptedException e) {
                System.err.println("Withdrawal interrupted: " + e.getMessage());
                return;
            }
            
            currentBalance = account.getBalance();
        }
        
        // Sufficient balance - proceed with withdrawal
        currentBalance -= amount;
        account.setBalance(currentBalance);
        System.out.println("✅ [" + customerName + "] Withdrawal successful!");
        System.out.printf("   Withdrew: $%.2f | Remaining: $%.2f%n", 
            amount, currentBalance);
        pendingWithdrawal = 0.0;
    }
    
    /**
     * Deposit money to account (synchronized with notify)
     */
    public synchronized void depositMoney(double amount) {
        System.out.println("\n💵 [" + customerName + "] Depositing: $" + amount);
        
        double currentBalance = account.getBalance();
        currentBalance += amount;
        account.setBalance(currentBalance);
        
        System.out.println("✅ [" + customerName + "] Deposit successful!");
        System.out.printf("   Deposited: $%.2f | New Balance: $%.2f%n", 
            amount, currentBalance);
        
        // Check if deposit covers pending withdrawal
        if (pendingWithdrawal > 0 && currentBalance >= pendingWithdrawal) {
            System.out.println("🔔 [" + customerName + "] Notifying waiting withdrawal...");
            notify(); // Wake up waiting withdrawal thread
        } else if (pendingWithdrawal > 0) {
            System.out.printf("⚠️  [" + customerName + "] Still insufficient for pending withdrawal of $%.2f%n", 
                pendingWithdrawal);
        }
    }
}

public class BankingSynchronizationDemo {
    
    /**
     * Simulate basic deposit and withdrawal scenario
     */
    public static void basicScenario() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   Basic Banking Synchronization Demo      ║");
        System.out.println("╚════════════════════════════════════════════╝");
        
        Account sharedAccount = new Account();
        Customer customer = new Customer(sharedAccount, "Alice");
        
        // Thread 1: Try to withdraw (will wait)
        Thread withdrawThread = new Thread(() -> {
            customer.withdrawMoney(15000);
        }, "Withdraw-Thread");
        
        // Thread 2: Deposit (insufficient)
        Thread deposit1Thread = new Thread(() -> {
            try {
                Thread.sleep(1000); // Delay to let withdrawal start
                customer.depositMoney(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Deposit1-Thread");
        
        // Thread 3: Deposit (will complete withdrawal)
        Thread deposit2Thread = new Thread(() -> {
            try {
                Thread.sleep(2000); // Delay for second deposit
                customer.depositMoney(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Deposit2-Thread");
        
        withdrawThread.start();
        deposit1Thread.start();
        deposit2Thread.start();
        
        try {
            withdrawThread.join();
            deposit1Thread.join();
            deposit2Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n" + "=".repeat(50));
        sharedAccount.displayBalance();
        System.out.println("=".repeat(50));
    }
    
    /**
     * Simulate multiple customers with concurrent transactions
     */
    public static void multiCustomerScenario() {
        System.out.println("\n\n╔════════════════════════════════════════════╗");
        System.out.println("║  Multi-Customer Concurrent Transactions    ║");
        System.out.println("╚════════════════════════════════════════════╝");
        
        Account sharedAccount = new Account();
        
        // Initial balance
        sharedAccount.setBalance(5000);
        System.out.println("🏦 Initial Balance: $5000.00\n");
        
        Customer bob = new Customer(sharedAccount, "Bob");
        Customer charlie = new Customer(sharedAccount, "Charlie");
        Customer diana = new Customer(sharedAccount, "Diana");
        
        // Create multiple transaction threads
        Thread[] threads = {
            new Thread(() -> bob.depositMoney(3000), "Bob-Deposit"),
            new Thread(() -> charlie.withdrawMoney(6000), "Charlie-Withdraw"),
            new Thread(() -> {
                try { Thread.sleep(1500); } catch (InterruptedException e) {}
                diana.depositMoney(4000);
            }, "Diana-Deposit"),
            new Thread(() -> {
                try { Thread.sleep(3000); } catch (InterruptedException e) {}
                bob.withdrawMoney(2000);
            }, "Bob-Withdraw")
        };
        
        // Start all threads
        for (Thread t : threads) {
            t.start();
        }
        
        // Wait for completion
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("\n" + "=".repeat(50));
        sharedAccount.displayBalance();
        System.out.println("=".repeat(50));
    }
    
    /**
     * Main method demonstrating thread synchronization
     */
    public static void main(String[] args) {
        System.out.println("\n🏦 Banking Synchronization Demonstration");
        System.out.println("Advanced Threading: wait(), notify(), synchronized\n");
        
        // Demo 1: Basic scenario
        basicScenario();
        
        // Demo 2: Multi-customer scenario
        multiCustomerScenario();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Banking Demo Completed!");
        System.out.println("Key Concepts Demonstrated:");
        System.out.println("  ✓ Synchronized methods");
        System.out.println("  ✓ wait() - Thread waiting mechanism");
        System.out.println("  ✓ notify() - Inter-thread communication");
        System.out.println("  ✓ Producer-Consumer pattern");
        System.out.println("  ✓ Race condition prevention");
        System.out.println("  ✓ Thread coordination");
        System.out.println("=".repeat(50));
    }
}
