package motor_PHSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class UserManagementLayer {
    protected Map<String, String[]> users; // User data stored as username-[UserID, EmployeeID, Password, RoleID] pairs
    protected Map<String, String> roles; // Role data stored as RoleID-RoleType pairs
    private int maxUserID; // Maximum user ID
    

    public UserManagementLayer() {
        users = CSVReader.loadUsersFromCSV("UsersLogin.csv"); // Load user data from CSV file
        roles = CSVReader.loadRolesFromCSV("rolesData.csv"); // Load role data from CSV file
        maxUserID = calculateMaxUserID(users);
        
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
        for (Map.Entry<String, String> entry : roles.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        String roleID;
        // Let user choose a role
        while (true) {
            roleID = getUserInput("Role ID");
            if (roles.containsKey(roleID)) {
                break;
            } else {
                System.out.println("Invalid Role ID. Please choose from the available roles.");
            }
        }

        // Add user
        String[] userData = {null, employeeID, username, password, roleID};
        CSVReader.saveUsersToCSV("UsersLogin.csv", userData, maxUserID);

        // Prompt to add another user
        if (askToAddAnotherUser()) {
            addUser(); // Recursively call addUser to add another user
        } else {
            returnToUserManagement(); // Return to user management dashboard
        }
    }
    

    // Method to check if the Username already exists
    private boolean isUsernameExists(String username) {
        Scanner scanner = new Scanner(System.in);
        while (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            System.out.print("Enter username again: ");
            username = scanner.nextLine().trim();
        }
        return false;
    }

    // Helper method to get user input
    private String getUserInput(String prompt) {
        System.out.print("Enter " + prompt + ": ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        return input;
    }
    
    private boolean isPasswordValid(String password) {
        Scanner scanner = new Scanner(System.in);
        while (!(password.length() >= 6 && 
                 password.matches(".*[A-Z].*") && 
                 password.matches(".*[a-z].*") && 
                 password.matches(".*\\d.*"))) {
            System.out.println("Password must be at least 6 characters with at least one uppercase letter, one lowercase letter, and one number.");
            System.out.print("Enter password again: ");
            password = scanner.nextLine().trim();
        }
        return true;
    }

    
    private int calculateMaxUserID(Map<String, String[]> users) {
        int maxID = 0;
        for (String[] userData : users.values()) {
            int userID = Integer.parseInt(userData[0]);
            maxID = Math.max(maxID, userID);
        }
        return maxID;
    }


    // Method to assign a role to a user
    public void assignRole(String username, String roleID) {
        String[] userData = users.get(username);
        if (userData != null) {
            userData[3] = roleID;
            users.put(username, userData);
            System.out.println("Role assigned successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    // Method to display user information
    public void displayUserInfo(String username) {
        String[] userData = users.get(username);
        if (userData != null) {
            System.out.println("User ID: " + userData[0]);
            System.out.println("Employee ID: " + userData[1]);
            System.out.println("Role ID: " + userData[4]);
        } else {
            System.out.println("User not found.");
        }
    }
    
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
    
  
    public void editUser(String employeeID) {
        String username = getUsernameByEmployeeID(employeeID);
        if (username != null) {
            String[] userData = users.get(username);
            System.out.println("Current user details:");
            System.out.println("Username: " + username);
            System.out.println("Password: " + userData[3]); // Password is at index 3
            String currentRoleType = roles.get(userData[4]); // Role ID is at index 4
            System.out.println("Role: " + currentRoleType);

            Scanner scanner = new Scanner(System.in);
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

            // Loop for entering a valid new password
            while (true) {
                System.out.print("Enter new password (leave blank to keep current): ");
                newPassword = scanner.nextLine().trim();
                if (newPassword.isEmpty() || isPasswordValid(newPassword)) {
                    break;
                }
            }

            // Display available roles for the user to choose
            System.out.println("Available Roles:");
            for (Map.Entry<String, String> entry : roles.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            String newRoleID = "";
            // Loop for entering a valid new role ID
            while (true) {
                System.out.print("Enter new role ID (leave blank to keep current): ");
                newRoleID = scanner.nextLine().trim();
                if (newRoleID.isEmpty() || roles.containsKey(newRoleID)) {
                    break;
                } else {
                    System.out.println("Invalid Role ID. Please choose from the available roles.");
                }
            }

            // Update the user data with the edited details
            if (!newUsername.isEmpty()) {
                userData[2] = newUsername;
            }
            if (!newPassword.isEmpty()) {
                userData[3] = newPassword;
            }
            if (!newRoleID.isEmpty()) {
                userData[4] = newRoleID;
            }
            users.put(username, userData);

            // Update the CSV file with the edited user details
            updateCSVFile();

            System.out.println("User details updated successfully.");
            
            returnToUserManagement(); // Return to user management dashboard only after successful update
        } else {
            System.out.println("Employee ID not found.");
            returnToUserManagement(); // Return to user management dashboard when employee ID is not found
        }
    }

    public void deleteUser(String employeeID) {
        String username = getUsernameByEmployeeID(employeeID);
        if (username != null) {
            String[] userData = users.get(username);
            String roleID = userData[4];
            String roleType = roles.get(roleID);

            // Display user information for confirmation
            System.out.println("Employee ID: " + employeeID);
            System.out.println("Username: " + username);
            System.out.println("Role: " + roleType);

            // Prompt for confirmation
            boolean confirm = confirmDeletion();
            if (confirm) {
                users.remove(username);
                updateCSVFile(); // Call the updateCSVFile method without passing any arguments
                System.out.println("User account deleted successfully.");
            } else {
                System.out.println("Deletion canceled.");
            }
        } else {
            System.out.println("Employee ID not found.");
        }
        returnToUserManagement(); // Return to the user management layer only after confirming and deleting the user account
    }



    // Method to return to the user management layer
    private void returnToUserManagement() {
        ConsoleUILayer consoleUILayer = new ConsoleUILayer();
        consoleUILayer.displayUserManagementDashboard();
    }




    // Method to update the CSV file after user deletion
 // Method to update the CSV file after user deletion
    private void updateCSVFile() {
        try (FileWriter writer = new FileWriter("UsersLogin.csv")) {
            // Write header
            writer.write("UserID,EmployeeID,Username,Password,RoleID\n");
            // Sort user data based on user ID
            List<Map.Entry<String, String[]>> sortedUsers = new ArrayList<>(users.entrySet());
            Collections.sort(sortedUsers, Comparator.comparing(entry -> Integer.parseInt(entry.getValue()[0])));
            // Write each user's data in sorted order
            for (Map.Entry<String, String[]> entry : sortedUsers) {
                String[] userData = entry.getValue();
                String line = String.join(",", userData);
                writer.write(line + "\n");
            }
            System.out.println("CSV file updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error updating CSV file.");
        }
    }

    private String getUsernameByEmployeeID(String employeeID) {
        while (true) {
            for (Map.Entry<String, String[]> entry : users.entrySet()) {
                if (entry.getValue()[1].equals(employeeID)) {
                    return entry.getKey();
                }
            }
            System.out.println("Employee ID not found. Please re-enter the Employee ID:");
            employeeID = getUserInput("Employee ID");
        }
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

