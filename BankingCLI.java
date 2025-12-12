package bankingSystem;
import java.util.*;
import java.io.*;
public class BankingCLI {
	
	private static final String FILE_NAME = "users.dat";
	private static List<User> users = new ArrayList<>();
    private static Admin admin = new Admin("Admin", "admin@bank.com", "admin123", 0);
    private static Scanner input = new Scanner(System.in);
    
    
    
	public static void main(String[] args) {
		loadUserData();
		
		
        while (true) {
            System.out.println("\n===== Banking Management System =====");
            System.out.println("1. Register as User");
            System.out.println("2. Login as User");
            System.out.println("3. Login as Admin");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> loginUser();
                case 3 -> loginAdmin();
                case 4 -> {
                    saveUserData();
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
		
		

	}
	
	// Main Functions --->
	
	private static void registerUser() {
		String name , email, password; int id;
		
		System.out.println("Enter Name : ");
		name = input.nextLine();
		
		System.out.println("Enter Email : ");
		email = input.nextLine();
		
		System.out.println("Enter Password");
		password = input.nextLine();
		
		id = users.size()+1;
		
		User newUser = new User(name,email,password,id);
		
		users.add(newUser);
		System.out.println("Your account has been created! Your ID = " + id);
		saveUserData();
		
		
	}
	
	private static void loginUser() {
		String email,password;
		
		System.out.println("Enter email : ");
		email = input.nextLine();
		for(User user: users) {
			if(user.getEmail().equals(email)) {
				System.out.println("Enter your password : ");
				password = input.nextLine();
				if(user.getPassword().equals(password)) {
					System.out.println("Access Granted");
					userMenu(user);
					return;
				} else {
					System.out.println("Wrong Password. Access Denied");
					return;
				}
			}
		}
		System.out.println("Email not found.");

		
	}
	
	public static void loginAdmin() {
		String email,password;
		
		System.out.println("Enter your email : ");
		email = input.nextLine();
	
		System.out.println("Enter your password : ");
		password = input.nextLine();
		
		if(email.equals("admin@bank.com") && password.equals("admin123")) {
			
			System.out.println("Access Granted");
			adminMenu();
		}
		else {
			System.out.println("Access Denied");
		}
	}
	
	public static void userMenu(User user) {
		while(true) {
			    System.out.println("\n===== Welcome, " + user.getName() + " =====");
	            System.out.println("1. Deposit");
	            System.out.println("2. Withdraw");
	            System.out.println("3. Transfer Money");
	            System.out.println("4. View Transaction History");
	            System.out.println("5. View Balance");
	            System.out.println("6. Logout");
	            System.out.print("Enter your choice: ");
	            
	            int choice = input.nextInt(); input.nextLine();
	            
	            switch(choice) {
	            
	            case 1 ->{
	            	System.out.println("Enter Amount to deposit : ");
	            	double amount = input.nextDouble();
	            	user.deposit(amount);
	            }
	            
	            case 2 ->{
	            	System.out.println("Enter Amount to withdraw : ");
	            	double amount = input.nextDouble();
	            	user.withdraw(amount);
	            }
	            
	            case 3 ->{
	            	System.out.println("Enter Reciever id : ");
	            	int id = input.nextInt();
	            	System.out.println("Enter Amount to transfer : ");
	            	double amount = input.nextDouble();
	            	
	            	Optional<User> receiver = users.stream().filter(u -> u.getId() == id).findFirst();
	                    if (receiver.isPresent()) {
	                        user.transfer(receiver.get(), amount);
	                    } else {
	                        System.out.println("User not found.");
	                    }
	            }
	            
	            case 4 ->{
	            	user.viewTransactionHistory();
	            }
	            
	            case 5 ->{
	            	System.out.println("Balance : " + user.viewBalance());
	            }
	            
	            case 6 ->{
	            	saveUserData();
	            	return;
	            }
	            default -> System.out.println("Invalid Choice");
	            
	            }
	            
	            
		}
		
	}
	
	public static void adminMenu() {
	    while (true) {
	        System.out.println("\n===== Admin Panel =====");
	        System.out.println("1. View All Users");
	        System.out.println("2. View All Transactions");
	        System.out.println("3. Delete User");
	        System.out.println("4. Logout");
	        System.out.print("Enter your choice: ");
	        
	        int choice = input.nextInt();
	        input.nextLine(); 
	        
	        switch (choice) {
	            case 1 -> admin.viewAllUsers(users);
	            case 2 -> admin.viewAllTransactions(users);
	            case 3 -> {
	                System.out.print("Enter the ID of the user to delete: ");
	                int userId = input.nextInt();
	                input.nextLine(); 
	                admin.deleteUser(users, userId);
	            }
	            case 4 -> {
	                saveUserData();
	                return;  
	            }
	            default -> System.out.println("Invalid Choice");
	        }
	    }
	}

	
	private static void saveUserData() {
		System.out.println("Saving data to: " + new File(FILE_NAME).getAbsolutePath());

	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
	        out.writeObject(users);
	    } catch (IOException e) {
	        System.out.println("Error saving user data: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	@SuppressWarnings("unchecked")
	private static void loadUserData() {
	    File file = new File(FILE_NAME);
	    if (!file.exists()) {
	        System.out.println("No user data file found. Starting with an empty user list.");
	        users = new ArrayList<>();
	        return;
	    }

	    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
	        users = (ArrayList<User>) in.readObject();
	    } catch (IOException | ClassNotFoundException e) {
	        System.out.println("Error loading user data: " + e.getMessage());
	        e.printStackTrace();
	        users = new ArrayList<>();
	    }
	}

	

}
