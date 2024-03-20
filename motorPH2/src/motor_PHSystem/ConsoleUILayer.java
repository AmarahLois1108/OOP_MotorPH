package motor_PHSystem;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleUILayer {
    private AuthenticationLayer authenticationLayer;
    public UserManagementLayer userManagementLayer;
    private TimeAttendanceLayer timeAttendanceLayer;
    private SalaryManagementLayer salaryManagementLayer;
    private DisputesLayer disputesLayer;

    public ConsoleUILayer() {
        this.authenticationLayer = new AuthenticationLayer();
        this.userManagementLayer = new UserManagementLayer();
        this.timeAttendanceLayer = new TimeAttendanceLayer();
        this.salaryManagementLayer = new SalaryManagementLayer();
        this.disputesLayer = new DisputesLayer();
    }

    // Method to display the main menu
    public void displayMainMenu() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("MotorPH Payroll System");
                System.out.println("-----------------------");
                System.out.print("Enter username: ");
                String username = scanner.nextLine().trim();
                System.out.print("Enter password: ");
                String password = scanner.nextLine().trim();
                
                // Call the login method from AuthenticationLayer
                boolean loginSuccessful = authenticationLayer.login(username, password);
                
                if (loginSuccessful) {
                    // If login is successful, check if the user wants to log in as an authorized user
                    boolean loginAsAuthorizedUser = chooseLoginOption();
                    if (loginAsAuthorizedUser) {
                        displayAuthorizedUserDashboard();
                    } else {
                        displayEmployeeSelfServiceHub();
                    }
                    // Break out of the loop after login
                    break;
                } else {
                    System.out.println("Login failed. Please try again.");
                }
            }
        } finally {
            scanner.close();
        }
    }

    private boolean chooseLoginOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to login as an Authorized User? (Y/N): ");
        String choice = scanner.nextLine();
        return choice.equalsIgnoreCase("Y");
    }

    private void displayAuthorizedUserDashboard() {
        System.out.println("Authorized User Dashboard");
        System.out.println("-----------------------");
        System.out.println("1. User Management");
        System.out.println("2. Employee Records");
        System.out.println("3. Time and Attendance Records");
        System.out.println("4. Salary Management");
        System.out.println("5. Disputes");
        System.out.println("6. Employee Self Service Hub");
        System.out.println("7. Logout");
        System.out.print("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                displayUserManagementDashboard();
                break;
            case 2:
                // Implement Employee Records
                break;
            case 3:
                displayTimeAttendanceMenu();
                break;
            case 4:
                displaySalaryManagementMenu();
                break;
            case 5:
                displayDisputesMenu();
                break;
            case 6:
                // Implement Employee Self Service Hub
                break;
            case 7:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void displayTimeAttendanceMenu() {
        System.out.println("Time and Attendance Records Menu");
        System.out.println("-------------------------------");
        System.out.println("1. View Time and Attendance Records");
        System.out.println("2. Upload Attendance Record");
        System.out.println("3. Back to main menu");
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        // Handle menu choices
        switch (choice) {
            case 1:
                // View Time and Attendance Records
                timeAttendanceLayer.viewRecords();
                break;
            case 2:
                // Upload Attendance Record
                timeAttendanceLayer.uploadRecord();
                break;
            case 3:
                // Back to main menu
                displayMainMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void displaySalaryManagementMenu() {
        System.out.println("Salary Management Menu");
        System.out.println("----------------------");
        System.out.println("1. Generate Payslip");
        System.out.println("2. Calculate Salary");
        System.out.println("3. Adjust Salary");
        System.out.println("4. Back to main menu");
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        // Handle menu choices
        switch (choice) {
            case 1:
                // Generate Payslip
                salaryManagementLayer.generatePayslip();
                break;
            case 2:
                // Calculate Salary
                salaryManagementLayer.calculateSalary();
                break;
            case 3:
                // Adjust Salary
                salaryManagementLayer.adjustSalary();
                break;
            case 4:
                // Back to main menu
                displayMainMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void displayDisputesMenu() {
        System.out.println("Disputes Menu");
        System.out.println("-------------");
        System.out.println("1. View Disputes History");
        System.out.println("2. Manage Disputes");
        System.out.println("3. Back to main menu");
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        // Handle menu choices
        switch (choice) {
            case 1:
                // View Disputes History
                disputesLayer.viewDisputesHistory();
                break;
            case 2:
                // Manage Disputes
                disputesLayer.manageDisputes();
                break;
            case 3:
                // Back to main menu
                displayMainMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    void displayUserManagementDashboard() {
        System.out.println("User Management Dashboard");
        System.out.println("-------------------------");
        System.out.println("1. Add User");
        System.out.println("2. Edit User");
        System.out.println("3. Delete User");
        System.out.println("4. Back to main menu");
        System.out.print("Enter your choice: ");
        
        Scanner scanner = new Scanner(System.in); // Create Scanner object
        
        try {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                
                switch (choice) {
                    case 1:
                        userManagementLayer.addUser();
                        break;
                    case 2:
                        System.out.print("Enter Employee ID: ");
                        String employeeIDForEdit = scanner.nextLine().trim();
                        userManagementLayer.editUser(employeeIDForEdit);
                        break;
                    case 3:
                        System.out.print("Enter Employee ID: ");
                        String employeeIDForDelete = scanner.nextLine().trim();
                        userManagementLayer.deleteUser(employeeIDForDelete);
                        break;
                    case 4:
                        displayMainMenu();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer choice.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Error reading input. Please try again.");
        }
    }

    private void displayEmployeeSelfServiceHub() {
        System.out.println("Employee Self Service Hub");
        System.out.println("------------------------");
        System.out.println("1. Payslip Records");
        System.out.println("2. Personal Information");
        System.out.println("3. Leave Request");
        System.out.println("4. Overtime Request");
        System.out.println("5. Dispute");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                employeeSelfServiceLayer.displayPayslipRecords();
                break;
            case 2:
                employeeSelfServiceLayer.displayPersonalInformation();
                break;
            case 3:
                employeeSelfServiceLayer.displayLeaveRequest();
                break;
            case 4:
                employeeSelfServiceLayer.displayOvertimeRequest();
                break;
            case 5:
                employeeSelfServiceLayer.displayDispute();
                break;
            case 6:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
}

