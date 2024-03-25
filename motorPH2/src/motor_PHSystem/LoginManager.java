package motor_PHSystem;

import java.util.Map;


public class LoginManager {
    private static LoginManager instance; // Declare instance variable
    private User loggedInUser;
    private Map<String, User> users; // Map to store user data

    // Constructor to initialize LoginManager with user data
    LoginManager() {
        this.users = CSVReader.loadUsersFromCSV();
    }

    // Method to get the singleton instance of LoginManager
    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

    // Method to handle user login
    public boolean login(String username, String password) {
        User user = users.get(username); // Retrieve user by username
        if (user != null) {
            String storedPassword = user.getPassword();
            if (password.equals(storedPassword)) {
                // Set the loggedInUser field with the authenticated user
                loggedInUser = user;
                String roleType = user.getRoleType();
                System.out.println("Login successful. Role: " + roleType);
                return true;
            } else {
                System.out.println("Invalid password.");
            }
        } else {
            System.out.println("User not found.");
        }
        return false;
    }

    // Method to get the role of the logged-in user
    public String getLoggedInUserRole() {
        if (loggedInUser != null) {
            return loggedInUser.getRoleType();
        } else {
            return "No user logged in"; // You can handle this case as per your requirement
        }
    }
}