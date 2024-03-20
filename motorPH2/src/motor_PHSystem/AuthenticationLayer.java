package motor_PHSystem;

public class AuthenticationLayer extends UserManagementLayer {
    public AuthenticationLayer() {
        super(); // Call the constructor of the superclass (UserManagementLayer)
    }

 // Method to handle user login
    public boolean login(String username, String password) {
        if (users.containsKey(username)) {
            String[] userData = users.get(username);
            String storedPassword = userData[3]; // Password stored at index 3
            if (password.equals(storedPassword)) {
                System.out.println("-----------------------");
                String roleID = userData[4]; // Role ID stored at index 4
                String roleType = roles.get(roleID); // Get role type based on role ID
                if (roleType != null) {
                    System.out.println("Login successful. Role: " + roleType);
                    return true;
                } else {
                    System.out.println("Role not found for the user.");
                    return false;
                }
            } else {
                System.out.println("Invalid password.");
                return false;
            }
        } else {
            System.out.println("Invalid username.");
            return false;
        }
    }
}