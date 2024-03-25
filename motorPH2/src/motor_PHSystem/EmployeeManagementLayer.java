package motor_PHSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EmployeeManagementLayer extends Employee {

    static final String EMPLOYEE_FILE = "Employee.tsv";
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private AuthorizedUserMenu authorizedUserMenu;
    
    public EmployeeManagementLayer() {
    	this.authorizedUserMenu = new AuthorizedUserMenu();
    	
    }
 
    public void viewEmployeeData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("View Employee Data");
        System.out.println("=================================");
        System.out.println("1. View Personal Information");
        System.out.println("2. View Salary Information");
        System.out.println("3. View All Employee Personal Information");
        System.out.println("4. View All Employee Salary Information");
        System.out.println("5. Back to Employee Records Menu");
        System.out.println();
        
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        try {
            switch (choice) {
                case 1:
                    // Prompt user for employee ID
                    System.out.print("Enter Employee ID: ");
                    String employeeID = scanner.nextLine();

                    // View personal information for the given employee ID
                    viewPersonalInformation(employeeID);
                    break;
                case 2:
                	System.out.print("Enter Employee ID: ");
                    String employeeID2 = scanner.nextLine();
                    // View personal information for the given employee ID
                    viewSalaryInformation(employeeID2);
                    break;
                case 3:
                    viewAllEmployeePersonalInfo("AllEmployeePersonalInfo.txt");
                    break;
                case 4:
                	 viewAllEmployeeSalaryInfo("AllEmployeeSalaryInfo.txt");
                    
                    break; 
                case 5:
                    authorizedUserMenu.displayEmployeeRecordsMenu(scanner);
                    break;  
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println();
                    viewEmployeeData();
                    break;
            }
        } finally {
            scanner.close();
        }
    }

    private void viewAllEmployeeSalaryInfo(String outputFile) {
    	Scanner scanner = new Scanner(System.in); // Define scanner object
        try (BufferedReader reader = new BufferedReader(new FileReader(Employee.EMPLOYEE_FILE))) {
            String line;
            
            // Print header
            String headerLine = reader.readLine();
            if (headerLine != null) {
                String[] headers = headerLine.split("\t");
                String format = "%-12s%-15s%-15s%-15s%-35s%-25s%-18s%-18s%-18s%-18s%n";
                System.out.printf(format, headers[0], headers[1], headers[2], headers[10], headers[11], headers[12], headers[13], headers[14], headers[15], headers[16]);
            } else {
                System.out.println("Error: Empty employee file.");
                return;
            }
            
            // Print employee data
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                System.out.println();
                String format = "%-12s%-15s%-15s%-15s%-35s%-25s%-18s%-18s%-18s%-18s%n";
                System.out.printf(format, parts[0], parts[1], parts[2], parts[10], parts[11], parts[12], parts[13], parts[14], parts[15], parts[16]);
            }
            
            // Prompt user to print to file
            System.out.print("Do you want to print this to a file? (y/n): ");
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            String choice = inputReader.readLine();
            if (choice.equalsIgnoreCase("y")) {
                printToFile(outputFile, headerLine + "\n" + "%-12s%-15s%-15s%-15s%-35s%-25s%-18s%-18s%-18s%-18s%n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Press Enter to go back to the Employee Records Menu.");
        scanner.nextLine();

        // Display Employee Records Menu
        viewEmployeeData();
    }

    

    private void viewAllEmployeePersonalInfo(String outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Employee.EMPLOYEE_FILE))) {
            String line;
            // Print data
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                System.out.println();
                String format = "%-12s%-12s%-15s%-15s%-80s%-15s%-18s%-18s%-18s%-18s%n";
                System.out.printf(format, parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]);
               
            }
            // Prompt user for choice
            System.out.print("Do you want to print this to a file? (y/n): ");
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            String choice = inputReader.readLine();
            if (choice.equalsIgnoreCase("y")) {
                printToFile(outputFile, "%-12s%-12s%-15s%-15s%-80s%-15s%-18s%-18s%-18s%-18s%n");
            }
            System.out.println();
            viewEmployeeData();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printToFile(String outputFile, String format) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Employee.EMPLOYEE_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            // Write data
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                String formattedLine = String.format(format, (Object[]) parts);
                writer.write(formattedLine);
                writer.newLine(); // Insert newline after each data record
               
            }
            System.out.println("Data has been successfully written to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

		    private void viewPersonalInformation(String employeeID) {
		        String tab = "\t:\t";
		       
		
		        System.out.println("Personal Information");
		        System.out.println("--------------------");
		
		        // Retrieve employee information using the Employee class
		        String lastName = getLastName(employeeID);
		        String firstName = getFirstName(employeeID);
		        String birthday = getBirthday(employeeID);
		        String address = formatAddress(getAddress(employeeID));
		        String phoneNumber = getPhoneNumber(employeeID);
		        String sssNumber = getSssNo(employeeID);
		        String philhealthNumber = getPhilhealthNo(employeeID);
		        String tinNumber = getTinNo(employeeID);
		        String pagIbigNumber = getPagibigNo(employeeID);
		
		        // Display employee information
		        System.out.println("                      Personal Information");
		        System.out.println("=====================================================================");
		        System.out.println("Employee ID" + tab + employeeID);
		        System.out.println("---------------------------------------------------------------------");
		        System.out.println("Last Name" + tab + lastName);
		        System.out.println("---------------------------------------------------------------------");
		        System.out.println("First Name" + tab + firstName);
		        System.out.println("---------------------------------------------------------------------");
		        System.out.println("Birthday" + tab + birthday);
		        System.out.println("---------------------------------------------------------------------");
		        System.out.println("Address\t" + tab + address);
		        System.out.println("---------------------------------------------------------------------");
		        System.out.println("Phone Number" + tab + phoneNumber);
		        System.out.println("---------------------------------------------------------------------");
		        System.out.println("SSS #\t" + tab + sssNumber);
		        System.out.println("---------------------------------------------------------------------");
		        System.out.println("Philhealth" + tab + philhealthNumber);
		        System.out.println("---------------------------------------------------------------------");
		        System.out.println("TIN\t #" + tab + tinNumber);
		        System.out.println("---------------------------------------------------------------------");
		        System.out.println("Pag-ibig #" + tab + pagIbigNumber);
		        System.out.println("---------------------------------------------------------------------");
		        System.out.println("=====================================================================");
		
		        // Ask user if they want to print details to a file
		        Scanner scanner = new Scanner(System.in);
		        String choice;
		        do {
		            System.out.println("Do you want to print these details to a file? (y/n)");
		            choice = scanner.nextLine().trim();
		        } while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"));
		
		        if (choice.equalsIgnoreCase("y")) {
		            try (PrintWriter writer = new PrintWriter(new FileWriter("personal_information.txt"))) {
		                writer.println("                        Personal Details");
		                writer.println("=====================================================================");
		                writer.println("Employee ID" + tab + employeeID);
		                writer.println("---------------------------------------------------------------------");
		                writer.println("Last Name" + tab + lastName);
		                writer.println("---------------------------------------------------------------------");
		                writer.println("First Name" + tab + firstName);
		                writer.println("---------------------------------------------------------------------");
		                writer.println("Birthday" + tab + birthday);
		                writer.println("---------------------------------------------------------------------");
		                writer.println("Address\t" + tab + address);
		                writer.println("---------------------------------------------------------------------");
		                writer.println("Phone Number" + tab + phoneNumber);
		                writer.println("---------------------------------------------------------------------");
		                writer.println("SSS #\t" + tab + sssNumber);
		                writer.println("---------------------------------------------------------------------");
		                writer.println("Philhealth" + tab + philhealthNumber);
		                writer.println("---------------------------------------------------------------------");
		                writer.println("TIN #\t" + tab + tinNumber);
		                writer.println("---------------------------------------------------------------------");
		                writer.println("Pag-ibig #" + tab + pagIbigNumber);
		                writer.println("=====================================================================");
		                System.out.println("Personal details printed to personal_information.txt");
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		
		        // Wait for Enter key to be pressed and return to the Employee Records Menu
		        System.out.println("Press Enter to go back to the Employee Records Menu.");
		        scanner.nextLine();
		
		        // Display Employee Records Menu
		        viewEmployeeData();
		    }


		    public void addEmployee() {
		        Scanner scanner = new Scanner(System.in);
		       

		        try (PrintWriter writer = new PrintWriter(new FileWriter(EMPLOYEE_FILE, true))) {
		            int employeeID = getLastEmployeeID() + 1; // Automatically generated employee number
		            System.out.println("Enter Employee Details:");
		            System.out.println("Employee Number: " + employeeID); // Displaying the automatically generated employee number
		            
		            // Enter employee details
		            System.out.print("Last Name: ");
		            String lastName = scanner.nextLine();
		            System.out.print("First Name: ");
		            String firstName = scanner.nextLine();
		            String birthday;
		            do {
		                System.out.print("Birthday (MM/DD/YYYY): ");
		                birthday = scanner.nextLine();
		            } while (!isValidDate(birthday));

		            System.out.print("Address: ");
		            String address = scanner.nextLine();
		            System.out.print("Phone Number: ");
		            String phoneNumber = scanner.nextLine();
		            String sssNumber;
		            do {
		                System.out.print("SSS # (Format: XX-XXXXXXX-X): ");
		                sssNumber = scanner.nextLine();
		            } while (!isValidSSS(sssNumber));

		            String philhealthNumber;
		            do {
		                System.out.print("Philhealth # (Format: Must be 12 Digits): ");
		                philhealthNumber = scanner.nextLine();
		            } while (!isValidPhilhealth(philhealthNumber));

		            String tinNumber;
		            do {
		                System.out.print("TIN # (Format: XXX-XXX-XXX): ");
		                tinNumber = scanner.nextLine();
		            } while (!isValidTIN(tinNumber));

		            String pagibigNumber;
		            do {
		                System.out.print("Pag-ibig # (Format: Must be 12 Digits): ");
		                pagibigNumber = scanner.nextLine();
		            } while (!isValidPagibig(pagibigNumber));

		            System.out.print("Status: ");
		            String status = scanner.nextLine();
		            System.out.print("Position: ");
		            String position = scanner.nextLine();
		            System.out.print("Immediate Supervisor: ");
		            String supervisor = scanner.nextLine();
		            System.out.print("Basic Salary: ");
		            double basicSalary = scanner.nextDouble();
		            System.out.print("Rice Subsidy: ");
		            double riceSubsidy = scanner.nextDouble();
		            System.out.print("Phone Allowance: ");
		            double phoneAllowance = scanner.nextDouble();
		            System.out.print("Clothing Allowance: ");
		            double clothingAllowance = scanner.nextDouble();
		            System.out.print("Gross Semi-monthly Rate: ");
		            double grossSemiMonthlyRate = scanner.nextDouble();
		            System.out.print("Hourly Rate: ");
		            double hourlyRate = scanner.nextDouble();

		            // Append the new employee details to the tab-separated file
		            writer.printf("%d\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%.0f\t%.0f\t%.0f\t%.0f\t%.0f\t%.0f%n",
		                    employeeID, lastName, firstName, birthday, address, phoneNumber,
		                    sssNumber, philhealthNumber, tinNumber, pagibigNumber, status, position,
		                    supervisor, basicSalary, riceSubsidy, phoneAllowance, clothingAllowance,
		                    grossSemiMonthlyRate, hourlyRate);
		            System.out.println("Employee added successfully.");

		            // Ask if the user wants to add another employee
		            System.out.print("Do you want to add another employee? (Y/N): ");
		            String choice = scanner.next();
		            if (!choice.equalsIgnoreCase("Y")) {
		                authorizedUserMenu.displayEmployeeRecordsMenu(scanner);
		                return; // Exit the method if the user chooses not to add another employee
		            }
		        } catch (IOException e) {
		            System.err.println("Error adding employee: " + e.getMessage());
		        } finally {
		            scanner.close(); // Close the scanner to prevent resource leaks
		        }
		    }

		    private int getLastEmployeeID() throws IOException {
		        try (Scanner scanner = new Scanner(new FileReader(EMPLOYEE_FILE))) {
		            int lastEmployeeID = 0;
		            // Skip the header line
		            if (scanner.hasNextLine()) {
		                scanner.nextLine();
		            }
		            // Read employee IDs from the second line onwards
		            while (scanner.hasNextLine()) {
		                String line = scanner.nextLine();
		                String[] parts = line.split("\t");
		                if (parts.length > 0) {
		                    int employeeID = Integer.parseInt(parts[0].trim());
		                    if (employeeID > lastEmployeeID) {
		                        lastEmployeeID = employeeID;
		                    }
		                }
		            }
		            return lastEmployeeID;
		        }
		    }

		    private boolean isValidDate(String dateStr) {
		        try {
		            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		            sdf.setLenient(false);
		            sdf.parse(dateStr);
		            return true;
		        } catch (ParseException e) {
		            System.out.println("Invalid date format. Please enter date in MM/DD/YYYY format.");
		            return false;
		        }
		    }

		    private boolean isValidSSS(String sssNumber) {
		        // Check if the SSS number has the format XXX-XXX-XXX and each part is a valid integer
		        if (sssNumber.matches("\\d{2}-\\d{7}-\\d{1}")) {
		            // Check if the SSS number already exists in the file
		            if (isDuplicateNumber(sssNumber, "SSS")) {
		                System.out.println("SSS number already exists. Please enter a different SSS number.");
		                return false;
		            }
		            return true;
		        } else {
		            System.out.println("Invalid SSS number format. Please enter a valid SSS number in the format XXX-XXX-XXX.");
		            return false;
		        }
		    }

		    private boolean isValidPhilhealth(String philhealthNumber) {
		        // Check if the Philhealth number has the format XXXXXXXXXXXX and each part is a valid integer
		        if (philhealthNumber.matches("\\d{12}")) {
		            // Check if the Philhealth number already exists in the file
		            if (isDuplicateNumber(philhealthNumber, "Philhealth")) {
		                System.out.println("Philhealth number already exists. Please enter a different Philhealth number.");
		                return false;
		            }
		            return true;
		        } else {
		            System.out.println("Invalid Philhealth number format. Please enter a valid Philhealth number with exactly 12 digits.");
		            return false;
		        }
		    }

		    private boolean isValidTIN(String tinNumber) {
		        // Check if the TIN number has the format XXX-XXXXXXXXX-X and each part is a valid integer
		        if (tinNumber.matches("\\d{3}-\\d{3}-\\d{3}")) {
		            // Check if the TIN number already exists in the file
		            if (isDuplicateNumber(tinNumber, "TIN")) {
		                System.out.println("TIN number already exists. Please enter a different TIN number.");
		                return false;
		            }
		            return true;
		        } else {
		            System.out.println("Invalid TIN number format. Please enter a valid TIN number in the format XXX-XXXXXXXXX-X.");
		            return false;
		        }
		    }

		    private boolean isValidPagibig(String pagibigNumber) {
		        // Check if the Pag-ibig number has the format XXXXXXXXXXXX and each part is a valid integer
		        if (pagibigNumber.matches("\\d{12}")) {
		            // Check if the Pag-ibig number already exists in the file
		            if (isDuplicateNumber(pagibigNumber, "Pag-ibig")) {
		                System.out.println("Pag-ibig number already exists. Please enter a different Pag-ibig number.");
		                return false;
		            }
		            return true;
		        } else {
		            System.out.println("Invalid Pag-ibig number format. Please enter a valid Pag-ibig number with exactly 12 digits.");
		            return false;
		        }
		    }

		    private boolean isDuplicateNumber(String number, String type) {
		        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE))) {
		            String line;
		            // Read each line of the file
		            while ((line = reader.readLine()) != null) {
		                // Split the line by tab to get the number column
		                String[] parts = line.split("\t");
		                // Assuming the column containing the number is at index 6
		                if (parts.length > 6 && parts[6].equals(number)) {
		                    // Number already exists
		                    return true;
		                }
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        return false;
		    }


		private void viewSalaryInformation(String employeeID2) {
		    String tab = "\t:\t";
		
		    System.out.println("Salary Information");
		    System.out.println("------------------");
		
		    // Retrieve salary information using the Employee class
		    String status = getStatus(employeeID2);
		    String position = getPosition(employeeID2);
		    String supervisor = getSupervisor(employeeID2);
		    double basicSalary = getBasicSalary(employeeID2);
		    double riceSubsidy = getRiceSubsidy(employeeID2);
		    double phoneAllowance = getPhoneAllowance(employeeID2);
		    double clothingAllowance = getClothingAllowance(employeeID2);
		    double grossSemiMonthlyRate = getGrossSemiMonthlyRate(employeeID2);
		    double hourlyRate = getHourlyRate(employeeID2);
		
		    // Display salary information
		    System.out.println("                      Salary Information");
		    System.out.println("=====================================================================");
		    System.out.println("Employee ID\t" + tab + employeeID2);
		    System.out.println("---------------------------------------------------------------------");
		    System.out.println("Status\t\t" + tab + status);
		    System.out.println("---------------------------------------------------------------------");
		    System.out.println("Position\t" + tab + position);
		    System.out.println("---------------------------------------------------------------------");
		    System.out.println("Immediate Supervisor" + tab + supervisor);
		    System.out.println("---------------------------------------------------------------------");
		    System.out.println("Basic Salary\t" + tab + basicSalary);
		    System.out.println("---------------------------------------------------------------------");
		    System.out.println("Rice Subsidy\t" + tab + riceSubsidy);
		    System.out.println("---------------------------------------------------------------------");
		    System.out.println("Phone Allowance\t" + tab + phoneAllowance);
		    System.out.println("---------------------------------------------------------------------");
		    System.out.println("Clothing Allowance" + tab + clothingAllowance);
		    System.out.println("---------------------------------------------------------------------");
		    System.out.println("Gross Semi-monthly Rate" + tab + grossSemiMonthlyRate);
		    System.out.println("---------------------------------------------------------------------");
		    System.out.println("Hourly Rate\t" + tab + hourlyRate);
		    System.out.println("=====================================================================");
		
		    // Ask user if they want to print details to a file
		    Scanner scanner = new Scanner(System.in);
		    String choice;
		    do {
		        System.out.println("Do you want to print these details to a file? (y/n)");
		        choice = scanner.nextLine().trim();
		    } while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"));
		
		    if (choice.equalsIgnoreCase("y")) {
		        try (PrintWriter writer = new PrintWriter(new FileWriter("salary_information.txt"))) {
		            writer.println("                      Salary Information");
		            writer.println("=====================================================================");
		            writer.println("Employee ID\t" + tab + employeeID2);
		            writer.println("---------------------------------------------------------------------");
		            writer.println("Status\t\t" + tab + status);
		            writer.println("---------------------------------------------------------------------");
		            writer.println("Position\t" + tab + position);
		            writer.println("---------------------------------------------------------------------");
		            writer.println("Immediate Supervisor" + tab + supervisor);
		            writer.println("---------------------------------------------------------------------");
		            writer.println("Basic Salary\t" + tab + basicSalary);
		            writer.println("---------------------------------------------------------------------");
		            writer.println("Rice Subsidy\t" + tab + riceSubsidy);
		            writer.println("---------------------------------------------------------------------");
		            writer.println("Phone Allowance\t" + tab + phoneAllowance);
		            writer.println("---------------------------------------------------------------------");
		            writer.println("Clothing Allowance" + tab + clothingAllowance);
		            writer.println("---------------------------------------------------------------------");
		            writer.println("Gross Semi-monthly Rate" + tab + grossSemiMonthlyRate);
		            writer.println("---------------------------------------------------------------------");
		            writer.println("Hourly Rate\t" + tab + hourlyRate);
		            writer.println("=====================================================================");
		            System.out.println("Salary Information printed to salary_information.txt");
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		
		    // Wait for Enter key to be pressed and return to the Employee Records Menu
		    System.out.println("Press Enter to go back to the Employee Records Menu.");
		    scanner.nextLine();
		
		    // Display Employee Records Menu
		    viewEmployeeData();
		}
        	
    // Method to update employee data
		public void updateEmployee() {
		    Scanner scanner = new Scanner(System.in);

		    try {
		        // Prompt for the employee ID to update
		        System.out.print("Enter Employee ID to update: ");
		        String employeeID = scanner.next();
		        scanner.nextLine(); // Consume the newline character

		        // Check if the employee exists
		        if (!isEmployeeExists(employeeID)) {
		            System.out.println("Employee with ID " + employeeID + " does not exist.");
		            return;
		        }

		        // Display menu for choosing the category of information to update
		        System.out.println("Select category of information to update:");
		        System.out.println("1. Personal Information");
		        System.out.println("2. Employment Information");
		        System.out.println("3. Government IDs");
		        System.out.println("4. Salary Information");
		        System.out.print("Enter your choice: ");
		        int choice = scanner.nextInt();
		        scanner.nextLine(); // Consume the newline character

		        switch (choice) {
		            case 1:
		                updatePersonalInformation(employeeID, scanner);
		                break;
		            case 2:
		                updateEmploymentInformation(employeeID, scanner);
		                break;
		            case 3:
		                updateGovernmentIDs(employeeID, scanner);
		                break;
		            case 4:
		                updateSalaryInformation(employeeID, scanner);
		                break;
		            default:
		                System.out.println("Invalid choice.");
		        }
		    } catch (IOException e) {
		        System.err.println("Error updating employee: " + e.getMessage());
		    } finally {
		        scanner.close();
		    }
		}

		private void updateSalaryInformation(String employeeID, Scanner scanner) {
			// TODO Auto-generated method stub
			
		}

		private void updateEmploymentInformation(String employeeID, Scanner scanner) {
			// TODO Auto-generated method stub
			
		}

		private void updateGovernmentIDs(String employeeID, Scanner scanner) {
			// TODO Auto-generated method stub
			
		}

		private void updatePersonalInformation(String employeeID, Scanner scanner) throws IOException {
	        // Retrieve existing personal information of the employee
	        String[] existingInfo = Employee.getEmployeeDetails(employeeID);
	        if (existingInfo == null) {
	            System.out.println("Error retrieving employee information.");
	            return;
	        }

	        // Prompt for updated personal information
	        System.out.println("Enter Updated Personal Information for ID " + employeeID + ":");
	        System.out.print("Last Name: ");
	        String lastName = scanner.nextLine();
	        System.out.print("First Name: ");
	        String firstName = scanner.nextLine();
	        // Update other personal information fields as needed
	        // ...

	        // Update the personal information in the employee record
	        existingInfo[1] = lastName;
	        existingInfo[2] = firstName;
	        // Update other fields as needed
	        // ...

	        // Write the updated employee details to the file
	        Employee.writeUpdatedInfo(employeeID, existingInfo);
	        System.out.println("Personal information for employee with ID " + employeeID + " updated successfully.");
	    }

		private void writeUpdatedInfo(int employeeID, String[] existingInfo) {
			// TODO Auto-generated method stub
			
		}
		
		 public static boolean isEmployeeExists(String employeeID) {
		        String retrievedEmployeeId = Employee.getEmployeeId(employeeID);
		        return !retrievedEmployeeId.isEmpty(); // If retrievedEmployeeId is not empty, employee exists
		    }

		


    public void deleteEmployee() {
        // Implementation
    }
    
    private static String formatAddress(String address) {
        int maxLineLength = 42; // Maximum length for each line
        StringBuilder formattedAddress = new StringBuilder();

        // Check if the address is longer than the maximum line length
        if (address.length() > maxLineLength) {
            // Find the last space within the maximum line length
            int lastSpaceIndex = address.lastIndexOf(' ', maxLineLength);

            // If there is no space within the maximum line length, just break the line at the maximum length
            if (lastSpaceIndex == -1) {
                formattedAddress.append(address.substring(0, maxLineLength));
                formattedAddress.append("\n\t\t\t").append(address.substring(maxLineLength));
            } else {
                // Break the line at the last space within the maximum line length
                formattedAddress.append(address.substring(0, lastSpaceIndex));
                formattedAddress.append("\n\t\t\t").append(address.substring(lastSpaceIndex + 1));
            }
        } else {
            // If the address is short enough, just return it
            formattedAddress.append(address);
        }

        return formattedAddress.toString();
    }


    public static void main(String[] args) {
        EmployeeManagementLayer employeeManagementLayer = new EmployeeManagementLayer();
        employeeManagementLayer.viewEmployeeData();
    }
}
