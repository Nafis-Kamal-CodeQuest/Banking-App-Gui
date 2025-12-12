package bankingSystem;

import java.time.LocalDateTime;
import java.util.UUID;
import java.io.Serializable;
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String transactionId;              
    private LocalDateTime date;                
    private String type;                       
    private double amount;                     
    private String sourceAccountNumber;
    private String destinationAccountNumber;


    public Transaction(String type, double amount, Account source, Account destination) {
        this.type = type;
        this.amount = amount;
        this.sourceAccountNumber =
                source != null ? source.getAccountNumber() : "N/A";
        this.destinationAccountNumber =
                destination != null ? destination.getAccountNumber() : "N/A";
        this.transactionId = UUID.randomUUID().toString();
        this.date = LocalDateTime.now();
    }


    // Returns a readable summary of the transaction
    public String getSummary() {
    	return String.format("[%s] %s - Amount: %.2f | From: %s | To: %s",
    	        date,
    	        type,
    	        amount,
    	        sourceAccountNumber,
    	        destinationAccountNumber);

    }

    // Getters
    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

   
}
