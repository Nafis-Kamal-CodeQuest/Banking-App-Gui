package bankingSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User extends Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private Account account;
    private List<Transaction> transaction;

    
    public User(String name, String email, String password, int id) {
        super(name, email, password, id);
        this.account = new Account(this);
        this.transaction = new ArrayList<>();
    }
    
    public void deposit(double amount) {
        account.credit(amount);
        Transaction t = new Transaction("Deposit", amount, null, account);
        transaction.add(t);
        System.out.println("Deposited: $" + amount + " successfully.");
    }
    
    public void withdraw(double amount) {
        boolean success = account.debit(amount);
        if (success) {
            Transaction t = new Transaction("Withdraw", amount, account, null);
            transaction.add(t);
            System.out.println("Withdrew $" + amount + " successfully.");
        } else {
            System.out.println("Withdrawal failed. Not enough balance.");
        }
    }
    
    public boolean transfer(User to, double amount) {
    	if(amount<=0) {
    		System.out.println("Amount not valid");
    		return false;
    	}
        boolean success = account.debit(amount);
        
        if (success) {
            to.account.credit(amount);
            Transaction t = new Transaction("Transfer", amount, account, to.account);
            transaction.add(t);
            
            to.transaction.add(new Transaction("Received", amount, account, to.getAccount()));
            System.out.println(amount + " Transferred to " + to.getName());
            return true;
        }
        return false;
    }
    
    public List<Transaction> viewTransactions() {
        return transaction;
    }
    
    public void viewTransactionHistory() {
        if (transaction.isEmpty()) {
            System.out.println("No Transaction History Yet");
        } else {
            System.out.println("Transaction history for " + getName() + ":");
            for (Transaction t : transaction) {
                System.out.println(t.getSummary());
            }
        }
    }
    
    public double viewBalance() {
        return account.getBalance();
    }
    
    public Account getAccount() {
        return account;
    }
    
    @Override
    public String toString() {
        return "User ID: " + getId() +
               ", Name: " + getName() +
               ", Email: " + getEmail() +
               ", Balance: $" + String.format("%.2f", account.getBalance());
    }
}
