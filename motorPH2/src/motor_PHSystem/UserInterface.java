package motor_PHSystem;

import java.util.Scanner;


public class UserInterface {
    private LoginManager loginManager;
    private EmployeeSelfServiceLayer employeeSelfServiceLayer; // Add this

    public UserInterface(LoginManager loginManager, EmployeeSelfServiceLayer employeeSelfServiceLayer) {
        this.loginManager = loginManager;
        this.employeeSelfServiceLayer = employeeSelfServiceLayer; // Initialize employeeSelfServiceLayer
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
                    String userRole = loginManager.getLoggedInUserRole();
                    displayMenu(scanner, userRole);
                    break; // Exit the loop after handling menu choice
                } else {
                    System.out.println("Login failed. Please try again.");
                }
            }
        } finally {
            scanner.close();
        }
    }

    private void displayMenu(Scanner scanner, String userRole) {
        Menu menu;
        switch (userRole.toLowerCase()) {
            case "employee":
                System.out.println("Redirecting to Employee Self Service Hub...");
                menu = new EmployeeSelfServiceMenu(employeeSelfServiceLayer); // Pass employeeSelfServiceLayer
                break;
            default:
                System.out.println("Do you want to log in as an authorized user? (Y/N)");
                String choice = scanner.nextLine().trim().toUpperCase();
                if (choice.equals("Y")) {
                    System.out.println("Redirecting to Authorized User Menu...");
                    menu = new AuthorizedUserMenu();
                } else {
                    System.out.println("Redirecting to Employee Self Service Hub...");
                    menu = new EmployeeSelfServiceMenu(employeeSelfServiceLayer); // Pass employeeSelfServiceLayer
                }
                break;
        }
        menu.display();
        menu.handleChoice(scanner);
    }
}
