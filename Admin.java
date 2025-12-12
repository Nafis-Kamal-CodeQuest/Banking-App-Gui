package bankingSystem;
import java.io.Serializable;  
import java.util.*;
public class Admin extends Person implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public Admin(String name, String email, String password, int id) {
		super(name,email,password,id);
	}
	
	public void viewAllUsers(List<User> users) {
		if(users.isEmpty()) {
			System.out.println("No users found");
		}
		else {
			for(User user : users) {
				System.out.println(user.toString());
			}
		}
		
	}
	
	public void viewAllTransactions(List<User> users) {
		boolean hasTransaction = false;
		for(User user: users) {
			List<Transaction> transactions = user.viewTransactions();
			if(!transactions.isEmpty()) {
				hasTransaction = true;
				 System.out.println("User: " + user.getName());
	                for (Transaction t : transactions) {
	                    System.out.println("  " + t.getSummary());
	                }
	                System.out.println();
			}
		}
		if (!hasTransaction) {
            System.out.println("No transactions found.");
        }
	}
	
	public void deleteUser(List<User> users, int userId) {
		User userToDelete = null;
		for(User user: users) {
			if(user.getId()==userId) {
				userToDelete = user;
				break;
			}
		}
		if(userToDelete!=null) {
			users.remove(userToDelete);
			System.out.println(userToDelete.getName() + " "  + userToDelete.getId() + 
					           " has been deleted");
			
		}
		else {
			System.out.println("Could not find " + userId);
		}
	}
	
	@Override
    public String toString() {
		
	      return "Admin ID: " + getId() + ", Name: " + getName() + ", Email: " + getEmail();
	      
	    }
	
}