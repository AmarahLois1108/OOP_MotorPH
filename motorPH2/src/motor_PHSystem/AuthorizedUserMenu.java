package motor_PHSystem;

import java.util.Scanner;

class AuthorizedUserMenu implements Menu {
	
	private static UserManagementLayer userManagementLayer = new UserManagementLayer();
	private static EmployeeManagementLayer employeeManagementLayer = new EmployeeManagementLayer();
	 private static LoginManager loginManager = LoginManager.getInstance();
	
	
    @Override
    public void display() {
        System.out.println("Authorized User Dashboard");
        System.out.println("-----------------------");
        System.out.println("1. User Management");
        System.out.println("2. Employee Records");
        System.out.println("3. Time and Attendance Records");
        System.out.println("4. Salary Management");
        System.out.println("5. Disputes");
        System.out.println("6. Employee Self Service Hub");
        System.out.println("7. Logout");
    }

    @Override
    public void handleChoice(Scanner scanner) {
        String userRoleString = LoginManager.getInstance().getLoggedInUserRole();
        
        // Convert the user role string to UserRole enum
        UserRole userRole = UserRole.fromString(userRoleString);

        int choice;
        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            
            switch (choice) {
                case 1:
                    if (userRole != UserRole.ADMIN) {
                        System.out.println("Unauthorized access to User Management Dashboard. Only ADMIN role can access this functionality.");
                        System.out.println();
                        display(); // Display the main menu
                    } else {
                        displayUserManagementDashboard(scanner, userRole);
                        validChoice = true;
                    }
                    break;
                case 2:
                    displayEmployeeRecordsMenu(scanner);
                    validChoice = true;
                    break;
                case 3:
                    displayTimeAttendanceMenu();
                    validChoice = true;
                    break;
                case 4:
                    displaySalaryManagementMenu();
                    validChoice = true;
                    break;
                case 5:
                    displayDisputesMenu();
                    validChoice = true;
                    break;
                case 6:
                    // Implement Employee Self Service Hub
                    validChoice = true;
                    break;
                case 7:
                    System.out.println("Logging out...");
                    validChoice = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // Implement methods for displaying specific menus
    void displayUserManagementDashboard(Scanner scanner, UserRole userRole) {

        System.out.println("User Management Dashboard");
        System.out.println("-------------------------");
        System.out.println("1. Add User");
        System.out.println("2. Edit User");
        System.out.println("3. Delete User");
        System.out.println("4. Back to main menu");

        int choice;
        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                switch (choice) {
                    case 1:
                        userManagementLayer.addUser();
                        validChoice = true;
                        break;
                    case 2:
                        userManagementLayer.editUser();
                        validChoice = true;
                        break;
                    case 3:
                        userManagementLayer.deleteUser();
                        validChoice = true;
                        break;
                    case 4:
                        System.out.println("Returning to main menu...");
                        validChoice = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
            
            System.out.println();
            display(); // Display options again
        }
    }

    void displayEmployeeRecordsMenu(Scanner scanner) {
    	 System.out.println("Employee Records Menu");
         System.out.println("---------------------");
         System.out.println("1. View Employee Records");
         System.out.println("2. Add New Employee");
         System.out.println("3. Update Employee");
         System.out.println("4. Delete Employee");
         System.out.println("5. Back to main menu");
         System.out.println();
         System.out.print("Enter your choice: ");


         int choice = scanner.nextInt();
         scanner.nextLine(); // Consume the newline character
         switch (choice) {
             case 1:
            	 employeeManagementLayer.viewEmployeeData();
                 break;
             case 2:
                 employeeManagementLayer.addEmployee();
                 break;
             case 3:
                 employeeManagementLayer.updateEmployee();
                 break;
             case 4:
                 employeeManagementLayer.deleteEmployee();
                 break;
             case 5:
                 display();
                 break;
             default:
                 System.out.println("Invalid choice. Please try again.");
                 break;
         }
     }


    private void displayTimeAttendanceMenu() {
        System.out.println("Time and Attendance Records Menu");
        // Display options for time and attendance records
    }

    private void displaySalaryManagementMenu() {
        System.out.println("Salary Management Menu");
        // Display options for salary management
    }

    private void displayDisputesMenu() {
        System.out.println("Disputes Menu");
        // Display options for disputes
    }
}
