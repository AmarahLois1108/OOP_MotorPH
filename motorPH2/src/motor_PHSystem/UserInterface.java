package motor_PHSystem;

import java.util.Scanner;

public class UserInterface {
    private LoginManager loginManager;

    public UserInterface(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("MotorPH Payroll System");
                System.out.println("-----------------------");
                System.out.print("Enter username: ");
                String username = scanner.nextLine().trim();
                System.out.print("Enter password: ");
                String password = scanner.nextLine().trim();
                
                // Call the login method from LoginManager instance
                boolean loginSuccessful = loginManager.login(username, password);
                
                if (loginSuccessful) {
                    // If login is successful, display the appropriate menu based on user role
                    String userRole = loginManager.getLoggedInUserRole();
                    if (userRole.equalsIgnoreCase("Employee")) { // Use equalsIgnoreCase() for case-insensitive comparison
                        System.out.println("Redirecting to Employee Self Service Hub...");
                        Menu employeeSelfServiceMenu = new EmployeeSelfServiceMenu();
                        employeeSelfServiceMenu.display();
                        employeeSelfServiceMenu.handleChoice(scanner);
                    } else {
                        System.out.println("Do you want to log in as an authorized user? (Y/N)");
                        String choice = scanner.nextLine().trim().toUpperCase();
                        if (choice.equals("Y")) {
                            Menu authorizedUserMenu = new AuthorizedUserMenu();
                            authorizedUserMenu.display();
                            authorizedUserMenu.handleChoice(scanner);
                        } else {
                            System.out.println("Redirecting to Employee Self Service Hub...");
                            Menu employeeSelfServiceMenu = new EmployeeSelfServiceMenu();
                            employeeSelfServiceMenu.display();
                            employeeSelfServiceMenu.handleChoice(scanner);
                        }
                    }
                    // Break out of the loop after handling menu choice
                    break;
                } else {
                    System.out.println("Login failed. Please try again.");
                }
            }
        } finally {
            scanner.close();
        }
    }
 
}
   