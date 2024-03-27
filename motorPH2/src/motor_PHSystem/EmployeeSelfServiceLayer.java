package motor_PHSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

public class EmployeeSelfServiceLayer {
    private LoginManager loginManager; 
    private Scanner scanner; // Declare scanner as an instance variable
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // Define DATE_FORMAT
    private static final String LEAVE_REQUEST_FILE_PATH = "LeaveRequest.tsv";
    private static final String OVERTIME_REQUEST_FILE_PATH = "OvertimeRequest.tsv";
    private static final String DISPUTE_FILE_PATH = "Disputes.tsv";
    private LeaveCreditManager leaveCreditManager;
    
    	public EmployeeSelfServiceLayer(LoginManager loginManager, Scanner scanner) {
	        this.loginManager = loginManager;
	        this.scanner = scanner; // Initialize scanner in the constructor
	        this.leaveCreditManager = new LeaveCreditManager();
	        loginManager.getLoggedInUser();
	    }
	
	    public void displayLeaveRequest() {
	        while (true) {
	        	System.out.println("----------------------");
	            System.out.println("Leave Request Menu");
	            System.out.println("----------------------");
	            System.out.println("1. View Leave Requests");
	            System.out.println("2. Submit Leave Request");
	            System.out.println("3. Go Back to Main Menu");
	            System.out.print("Enter your choice: ");
	            int choice = scanner.nextInt();
	            switch (choice) {
	                case 1:
	                    viewLeaveRequestHistory();
	                    break;
	                case 2:
	                    submitLeaveRequest();
	                    break;
	                case 3:
	                    return; // Go back to the main menu
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	                    break;
	            }
	        }
	    }
		
		
	    private void viewLeaveRequestHistory() {
	        String employeeID = loginManager.getLoggedInUser();
	        System.out.println("Leave Request History");
	        System.out.println("----------------------------------------------------------------------------------------------------------------");
	
	        try (BufferedReader reader = new BufferedReader(new FileReader(LEAVE_REQUEST_FILE_PATH))) {
	            String line;
	            boolean found = false;
	            System.out.printf("%-12s | %-12s | %-12s | %-12s | %-12s | %-28s | %-10s%n", "Leave ID", "Date Filed", "Leave Type", "Start Date", "End Date", "Message", "Status");
	            System.out.println("----------------------------------------------------------------------------------------------------------------");
	            while ((line = reader.readLine()) != null) {
	                String[] data = line.split("\t");
	                if (data[1].equals(employeeID)) {
	                    found = true;
	                    String status = data[7];
	                    String formattedStatus = "";
	                    // Change color based on status
	                    switch (status.toUpperCase()) {
	                        case "PENDING":
	                            formattedStatus = "\u001B[34m" + status + "\u001B[0m"; // Blue
	                            break;
	                        case "REJECT":
	                            formattedStatus = "\u001B[31m" + status + "\u001B[0m"; // Red
	                            break;
	                        case "APPROVE":
	                            formattedStatus = "\u001B[32m" + status + "\u001B[0m"; // Green
	                            break;
	                        default:
	                            formattedStatus = status; // Default color
	                            break;
	                    }
	                    System.out.printf("%-12s | %-12s | %-12s | %-12s | %-12s | %-28s | %-10s%n", data[0], data[2], data[3], data[4], data[5], data[6], formattedStatus);
	                }
	            }
	            if (!found) {
	                System.out.println("No leave requests found for the employee.");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	
	        // Prompt the user to press enter to go back
	        System.out.println();
	        System.out.println("Press Enter to go back to the Leave Request Menu...");
	        scanner.nextLine(); // Consume the newline character
	        scanner.nextLine(); // Wait for the user to press enter
	    }
		    // Method to view overtime request history for a specific employee
		    public static void viewOvertimeRequestHistory() {
		        System.out.println("Overtime Request History");
		        System.out.println("------------------------");
		        Scanner scanner = new Scanner(System.in);
		        System.out.print("Enter Employee ID: ");
		        String employeeID = scanner.nextLine().trim();
	
		        try (BufferedReader reader = new BufferedReader(new FileReader(OVERTIME_REQUEST_FILE_PATH))) {
		            String line;
		            while ((line = reader.readLine()) != null) {
		                String[] data = line.split("\t");
		                if (data[1].equals(employeeID)) { // Check if the employee ID matches
		                    System.out.println("Overtime ID: " + data[0]);
		                    System.out.println("Date Filed: " + data[2]);
		                    System.out.println("Overtime Date: " + data[3]);
		                    System.out.println("Start Time: " + data[4]);
		                    System.out.println("End Time: " + data[5]);
		                    System.out.println("Reason: " + data[6]);
		                    System.out.println();
		                }
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
	
		    // Method to view dispute history for a specific employee
		    public static void viewDisputeHistory() {
		        System.out.println("Dispute History");
		        System.out.println("---------------");
		        Scanner scanner = new Scanner(System.in);
		        System.out.print("Enter Employee ID: ");
		        String employeeID = scanner.nextLine().trim();
	
		        try (BufferedReader reader = new BufferedReader(new FileReader(DISPUTE_FILE_PATH))) {
		            String line;
		            while ((line = reader.readLine()) != null) {
		                String[] data = line.split("\t");
		                if (data[0].equals(employeeID)) { // Check if the employee ID matches
		                    System.out.println("Dispute ID: " + data[1]);
		                    System.out.println("Date Filed: " + data[2]);
		                    System.out.println("Details: " + data[3]);
		                    System.out.println();
		                }
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		   
	
		    private void submitLeaveRequest() {
		        Scanner scanner = new Scanner(System.in);

		        // Get the logged-in employee ID
		        String employeeId = loginManager.getLoggedInUser(); // Assuming you have a login manager

		        // Display remaining leave credits
		        System.out.println("Available Leave Credits:");
		        System.out.println("\nChoose leave type:");
		        System.out.println("1. Sick Leave");
		        System.out.println("2. Vacation Leave");
		        System.out.println("3. Emergency Leave");
		        System.out.print("Enter your choice: ");
		        int leaveTypeOption = scanner.nextInt();
		        scanner.nextLine(); // Consume newline character

		        String leaveType;
		        while (true) {
		            switch (leaveTypeOption) {
		                case 1:
		                    leaveType = "Sick";
		                    break;
		                case 2:
		                    leaveType = "Vacation";
		                    break;
		                case 3:
		                    leaveType = "Emergency";
		                    break;
		                default:
		                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
		                    // Prompt the user to enter their choice again
		                    System.out.print("Enter your choice: ");
		                    leaveTypeOption = scanner.nextInt();
		                    scanner.nextLine(); // Consume newline character
		                    continue; // Continue to the next iteration of the loop
		            }
		            break; // Break out of the loop if a valid option is selected
		        }
		        

		        // Input leave details for start date
		        LocalDate startDate;
		        do {
		            System.out.print("Start Date (MM/DD/YYYY): ");
		            String startDateInput = scanner.nextLine().trim();
		            try {
		                startDate = LocalDate.parse(startDateInput, DATE_FORMATTER); // Use DATE_FORMATTER instead of DATE_FORMAT
		                break; // Exit the loop if the date is successfully parsed
		            } catch (DateTimeParseException e) {
		                System.out.println("Invalid date format. Please enter the date in MM/DD/YYYY format.");
		            }
		        } while (true);

		        // Input leave details for end date
		        LocalDate endDate;
		        do {
		            System.out.print("End Date (MM/DD/YYYY): ");
		            String endDateInput = scanner.nextLine().trim();
		            try {
		                endDate = LocalDate.parse(endDateInput, DATE_FORMATTER); // Use DATE_FORMATTER instead of DATE_FORMAT
		                break; // Exit the loop if the date is successfully parsed
		            } catch (DateTimeParseException e) {
		                System.out.println("Invalid date format. Please enter the date in MM/DD/YYYY format.");
		            }
		        } while (true);
		        // Input leave message
		        System.out.print("Message: ");
		        String message = scanner.nextLine().trim();

		        // Apply leave
		        LeaveApplication.applyLeave(employeeId, leaveType, startDate, endDate, message);
		        System.out.println();
		        System.out.println("Leave request submitted successfully!");
		    }
		

	
			public static void displayPayslipRecords() {
				// TODO Auto-generated method stub
				
			}
	
			public static void displayPersonalInformation() {
				// TODO Auto-generated method stub
				
			}
	
			public static void displayDispute() {
				// TODO Auto-generated method stub
				
			}
	
			public void displayOvertimeRequest() {
				// TODO Auto-generated method stub
				
			}
	}