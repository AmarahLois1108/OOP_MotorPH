package motor_PHSystem;

import java.util.Scanner;

public interface Menu {
    void display();
    void handleChoice(Scanner scanner);
}


class EmployeeRecordsMenu implements Menu {
    @Override
    public void display() {
        System.out.println("Employee Records Menu");
        System.out.println("---------------------");
        System.out.println("1. View Employee Records");
        System.out.println("2. Add New Employee");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Back to main menu");
    }

    @Override
    public void handleChoice(Scanner scanner) {
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                // Handle View Employee Records
                break;
            case 2:
                // Handle Add New Employee
                break;
            case 3:
                // Handle Update Employee
                break;
            case 4:
                // Handle Delete Employee
                break;
            case 5:
                System.out.println("Returning to main menu...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
}
    
    class TimeAttendanceMenu implements Menu {
        @Override
        public void display() {
            System.out.println("Time and Attendance Records Menu");
            System.out.println("-------------------------------");
            System.out.println("1. View Time and Attendance Records");
            System.out.println("2. Upload Attendance Record");
            System.out.println("3. Back to main menu");
        }

        @Override
        public void handleChoice(Scanner scanner) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Handle View Time and Attendance Records
                    break;
                case 2:
                    // Handle Upload Attendance Record
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    

    class SalaryManagementMenu implements Menu {
        @Override
        public void display() {
            System.out.println("Salary Management Menu");
            System.out.println("----------------------");
            System.out.println("1. Generate Payslip");
            System.out.println("2. Calculate Salary");
            System.out.println("3. Adjust Salary");
            System.out.println("4. Back to main menu");
        }

        @Override
        public void handleChoice(Scanner scanner) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Handle Generate Payslip
                    break;
                case 2:
                    // Handle Calculate Salary
                    break;
                case 3:
                    // Handle Adjust Salary
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    

    class DisputesMenu implements Menu {
        @Override
        public void display() {
            System.out.println("Disputes Menu");
            System.out.println("-------------");
            System.out.println("1. View Disputes History");
            System.out.println("2. Manage Disputes");
            System.out.println("3. Back to main menu");
        }

        @Override
        public void handleChoice(Scanner scanner) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Handle View Disputes History
                    break;
                case 2:
                    // Handle Manage Disputes
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    class EmployeeSelfServiceMenu implements Menu {
        @Override
        public void display() {
            System.out.println("Employee Self Service Hub");
            System.out.println("------------------------");
            System.out.println("1. Payslip Records");
            System.out.println("2. Personal Information");
            System.out.println("3. Leave Request");
            System.out.println("4. Overtime Request");
            System.out.println("5. Dispute");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
        }
  

        @Override
        public void handleChoice(Scanner scanner) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    EmployeeSelfServiceLayer.displayPayslipRecords();
                    break;
                case 2:
                    EmployeeSelfServiceLayer.displayPersonalInformation();
                    break;
                case 3:
                    EmployeeSelfServiceLayer.displayLeaveRequest();
                    break;
                case 4:
                    EmployeeSelfServiceLayer.displayOvertimeRequest();
                    break;
                case 5:
                    EmployeeSelfServiceLayer.displayDispute();
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
    
    class UserManagementMenu implements Menu {
        @Override
        public void display() {
            System.out.println("User Management Dashboard");
            System.out.println("-------------------------");
            System.out.println("1. Add User");
            System.out.println("2. Edit User");
            System.out.println("3. Delete User");
            System.out.println("4. Back to main menu");
        }

        @Override
        public void handleChoice(Scanner scanner) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // Handle Add User
                    break;
                case 2:
                    // Handle Edit User
                    break;
                case 3:
                    // Handle Delete User
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
          
    
   