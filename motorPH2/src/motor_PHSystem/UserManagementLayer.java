	package motor_PHSystem;
	
	import java.util.HashMap;
	import java.util.Map;
	import java.util.Scanner;
	
	
	public class UserManagementLayer {
	    private Map<String, User> users; // User data stored as username-[UserID, EmployeeID, Password, RoleType] pairs
	    private int maxUserID; // Maximum user ID
	    private AuthorizedUserMenu authorizedUserMenu;

	    public UserManagementLayer() {
	        this.users = CSVReader.loadUsersFromCSV(); // Load users from CSV file
	        this.maxUserID = calculateMaxUserID();
	        this.authorizedUserMenu = new AuthorizedUserMenu();
	    
	     }
	    

	    
	    // Method to add a new user
	    public void addUser() {
	        String employeeID = String.valueOf(++maxUserID); // Automatically increment employee ID
	        System.out.println("Automatically generated Employee ID: " + employeeID);
	
	        String username;
	        while (true) {
	            username = getUserInput("Username");
	            boolean isUsernameExists = isUsernameExists(username);
	            if (isUsernameExists) {
	                System.out.println("Username already exists. Please enter a different Username.");
	            } else {
	                break;
	            }
	        }
	
	        String password;
	        while (true) {
	            password = getUserInput("Password");
	            if (!isPasswordValid(password)) {
	                System.out.println("Password must be at least 6 characters with at least one uppercase letter, one lowercase letter, and one number.");
	            } else {
	                break;
	            }
	        }
	
	        // Display available roles for user to choose
	        System.out.println("Assign Role:");
	        System.out.println("1: ADMIN");
	        System.out.println("2: HR");
	        System.out.println("3: PAYROLL");
	        System.out.println("4: EMPLOYEE");
	
	        String roleType;
	        // Let user choose a role
	        while (true) {
	            roleType = getRoleTypeInput();
	            if (isValidRoleType(roleType)) {
	                break;
	            } else {
	                System.out.println("Invalid Role Type. Please choose from the available roles.");
	            }
	        }
	
	        // Add user
	        String[] userData = {null, employeeID, username, password, roleType};
	        CSVWriter.saveUsersToCSV("UsersLogin.csv", userData, maxUserID);

	        // Prompt to add another user
	        if (askToAddAnotherUser()) {
	            addUser(); // Recursively call addUser to add another user
	        } else {
	            authorizedUserMenu.displayUserManagementDashboard(new Scanner(System.in), UserRole.ADMIN); // Return to user management dashboard
	        }
	    }
	
	    // Method to check if the Username already exists
	    private boolean isUsernameExists(String username) {
	        return users.containsKey(username);
	    }
	
	    // Helper method to get user input
	    private String getUserInput(String prompt) {
	        System.out.print("Enter " + prompt + ": ");
	        Scanner scanner = new Scanner(System.in);
	        return scanner.nextLine().trim();
	    }
	
	    private boolean isPasswordValid(String password) {
	        return password.length() >= 6 &&
	               password.matches(".*[A-Z].*") &&
	               password.matches(".*[a-z].*") &&
	               password.matches(".*\\d.*");
	    }
	
	    private int calculateMaxUserID() {
	        return users.size();
	    }
	
	    // Method to return to the user management layer
	   
	    // Method to get role type input from user
	    private String getRoleTypeInput() {
	        System.out.print("Enter Role Type: ");
	        Scanner scanner = new Scanner(System.in);
	        return scanner.nextLine().trim().toUpperCase(); // Convert input to uppercase for consistency
	    }
	
	    // Method to validate role type input
	    private boolean isValidRoleType(String roleType) {
	        return roleType.equals("ADMIN") || roleType.equals("HR") || roleType.equals("PAYROLL") || roleType.equals("EMPLOYEE");
	    }
	
	    // Method to prompt for adding another user
	    private boolean askToAddAnotherUser() {
	        Scanner scanner = new Scanner(System.in);
	        while (true) {
	            System.out.print("Would you like to add another user? (yes/no): ");
	            String input = scanner.nextLine().trim().toLowerCase();
	            if (input.equals("yes")) {
	                return true;
	            } else if (input.equals("no")) {
	                return false;
	            } else {
	                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
	            }
	        }
	    }
	    public void editUser() {
	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Enter the employee ID: ");
	        String employeeID = scanner.nextLine();
	        String username = getUsernameByEmployeeID(employeeID);
	        if (username != null) {
	            User userData = users.get(username);
	            System.out.println("Current user details:");
	            System.out.println("Username: " + username);
	            System.out.println("Password: " + userData.getPassword()); // Get password using getter method
	            System.out.println("Role: " + userData.getRoleType()); // Get role type using getter method

	            String newUsername = "";
	            String newPassword = "";

	            // Loop for entering a valid new username
	            while (true) {
	                System.out.print("Enter new username (leave blank to keep current): ");
	                newUsername = scanner.nextLine().trim();
	                if (newUsername.isEmpty() || !isUsernameExists(newUsername)) {
	                    break;
	                } else {
	                    System.out.println("Username already exists. Please choose a different username.");
	                }
	            }

	            while (true) {
	                System.out.print("Enter new password (leave blank to keep current): ");
	                newPassword = scanner.nextLine().trim();
	                if (newPassword.isEmpty() || isPasswordValid(newPassword)) {
	                    break;
	                } else {
	                    System.out.println("Invalid password. Password must be at least 6 characters with at least one uppercase letter, one lowercase letter, and one number.");
	                }
	            }

	            // Display available roles for the user to choose
	            System.out.println("Available Roles:");
	            for (UserRole role : UserRole.values()) {
	                System.out.println(role.name()); // Display only the enum name
	            }

	            String newRoleType = "";
	            // Loop for entering a valid new role type
	            while (true) {
	                System.out.print("Enter new role type (leave blank to keep current): ");
	                newRoleType = scanner.nextLine().trim().toUpperCase();
	                if (newRoleType.isEmpty() || UserRole.fromString(newRoleType) != null) {
	                    break;
	                } else {
	                    System.out.println("Invalid Role Type. Please choose from the available roles.");
	                }
	            }

	            // Update the user data with the edited details
	            if (!newUsername.isEmpty()) {
	                userData.setUsername(newUsername); // Set new username using setter method
	            }
	            if (!newPassword.isEmpty()) {
	                userData.setPassword(newPassword); // Set new password using setter method
	            }
	            if (!newRoleType.isEmpty()) {
	                userData.setRoleType(newRoleType); // Set new role type using setter method
	            }
	            Map<String, String[]> userMap = new HashMap<>();
	            for (Map.Entry<String, User> entry : users.entrySet()) {
	                String userKey = entry.getKey();
	                User user = entry.getValue();
	                String[] userArray = {user.getEmployeeID(), user.getUsername(), user.getPassword(), user.getRoleType()};
	                userMap.put(userKey, userArray);
	            }
	            CSVWriter.updateCSVFile(userMap); // Pass the updated users map to update the CSV file

	            System.out.println("User details updated successfully.");
	            System.out.println();
	            authorizedUserMenu.displayUserManagementDashboard(new Scanner(System.in), UserRole.ADMIN);
	        } else {
	            System.out.println("Employee ID not found. Please try again.");
	            editUser(); // Prompt user to enter employee ID again
	        }
	    }
	    
	    public static void main(String[] args) {
	        // Create an instance of your class that contains the editUser method
	        // Assuming the class containing editUser is named UserManagementLayer
	        UserManagementLayer userManagementLayer = new UserManagementLayer();

	        // Call the editUser method to simulate user interaction
	        System.out.println("Testing deleteUser method:");
	        userManagementLayer.deleteUser();
	    }

	    public void deleteUser() {
	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Enter the employee ID of the user to delete: ");
	        String employeeID = scanner.nextLine();
	        String username = getUsernameByEmployeeID(employeeID);
	        if (username != null) {
	            User userData = users.get(username);
	            System.out.println("User details:");
	            System.out.println("Employee ID: " + userData.getEmployeeID());
	            System.out.println("Username: " + username);
	            System.out.println("Password: " + userData.getPassword());
	            System.out.println("Role: " + userData.getRoleType());

	            System.out.print("Do you want to delete this user? (yes/no): ");
	            String choice = scanner.nextLine().trim().toLowerCase();
	            if (choice.equals("yes")) {
	                users.remove(username); // Remove the user from the map

	                // Call the deleteUserDataFromCSV method from the CSVWriter class
	                CSVWriter.deleteUserDataFromCSV("UsersLogin.csv", username);
	                System.out.println("User account deleted successfully.");
	                System.out.println();
	                authorizedUserMenu.displayUserManagementDashboard(new Scanner(System.in), UserRole.ADMIN);
	            } else if (choice.equals("no")) {
	                System.out.println("User deletion canceled.");
	            } else {
	                System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
	            }
	        } else {
	        	 System.out.println("Employee ID not found. Please try again.");
		            deleteUser(); // Prompt user to enter employee ID again
	        }
	    }
	

	    private String getUsernameByEmployeeID(String employeeID) {
	        for (Map.Entry<String, User> entry : users.entrySet()) {
	            User user = entry.getValue();
	            String empID = user.getEmployeeID();
	            if (empID != null && empID.equals(employeeID)) {
	                return entry.getKey(); // Found matching employee ID, return corresponding username
	            }
	        }
	        return null; // Employee ID not found
	    }
	

	
	
	    private boolean confirmDeletion() {
	        try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
				    System.out.println("Are you sure you want to delete this user? (yes/no)");
				    String input = scanner.nextLine().trim().toLowerCase();
				    if (input.equals("yes")) {
				        return true;
				    } else if (input.equals("no")) {
				        return false;
				    } else {
				        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
				    }
				}
			}
	    }
	}
	
