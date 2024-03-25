package motor_PHSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Employee {

    static final String EMPLOYEE_FILE = "Employee.tsv";

    static String[] getEmployeeDetails(String employeeId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(employeeId + "\t")) {
                    return line.split("\t");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if employee not found
    }

    static String getValueFromEmployeeFile(String employeeId, int valueIndex) {
        String[] employeeDetails = getEmployeeDetails(employeeId);
        if (employeeDetails != null && employeeDetails.length > valueIndex) {
            return employeeDetails[valueIndex];
        }
        return ""; // Return empty string if value not found
    }


	
	    static String getEmployeeId(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 0);
	    }

	    static String getLastName(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 1);
	    }

	    static String getFirstName(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 2);
	    }

	    static String getBirthday(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 3);
	    }

	    static String getAddress(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 4);
	    }

	    static String getPhoneNumber(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 5);
	    }

	    static String getSssNo(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 6);
	    }

	    static String getPhilhealthNo(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 7);
	    }

	    static String getTinNo(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 8);
	    }

	    static String getPagibigNo(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 9);
	    }

	    static String getStatus(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 10);
	    }

	    static String getPosition(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 11);
	    }

	    static String getSupervisor(String employeeId) {
	        return getValueFromEmployeeFile(employeeId, 12);
	    }
	    static double getRiceSubsidy(String employeeId) {
	        String riceSubsidyStr = getValueFromEmployeeFile(employeeId, 14);
	        riceSubsidyStr = riceSubsidyStr.replace(",", ""); // Remove commas
	        return Double.parseDouble(riceSubsidyStr);
	    }

	    static double getBasicSalary(String employeeId) {
	        String basicSalaryStr = getValueFromEmployeeFile(employeeId, 13);
	        basicSalaryStr = basicSalaryStr.replace(",", ""); // Remove commas
	        return Double.parseDouble(basicSalaryStr);
	    }

	    static double getPhoneAllowance(String employeeId) {
	        String phoneAllowanceStr = getValueFromEmployeeFile(employeeId, 15);
	        phoneAllowanceStr = phoneAllowanceStr.replace(",", ""); // Remove commas
	        return Double.parseDouble(phoneAllowanceStr);
	    }

	    static double getClothingAllowance(String employeeId) {
	        String clothingAllowanceStr = getValueFromEmployeeFile(employeeId, 16);
	        clothingAllowanceStr = clothingAllowanceStr.replace(",", ""); // Remove commas
	        return Double.parseDouble(clothingAllowanceStr);
	    }

	    static double getGrossSemiMonthlyRate(String employeeId) {
	        String grossSemiMonthlyRateStr = getValueFromEmployeeFile(employeeId, 17);
	        grossSemiMonthlyRateStr = grossSemiMonthlyRateStr.replace(",", ""); // Remove commas
	        return Double.parseDouble(grossSemiMonthlyRateStr);
	    }

	    static double getHourlyRate(String employeeId) {
	        String hourlyRateStr = getValueFromEmployeeFile(employeeId, 18);
	        hourlyRateStr = hourlyRateStr.replace(",", ""); // Remove commas
	        return Double.parseDouble(hourlyRateStr);
	    }
	    
	    static boolean hasAdminAccess(UserRole userRole) {
	        return userRole == UserRole.ADMIN;
	    }

	    static boolean hasHRUpdateAccess(UserRole userRole) {
	        return userRole == UserRole.ADMIN || userRole == UserRole.HR;
	    }

	    static boolean hasPayrollUpdateAccess(UserRole userRole) {
	        return userRole == UserRole.ADMIN || userRole == UserRole.PAYROLL;
	    }

	    static boolean hasEmployeeUpdateAccess(UserRole userRole) {
	        return userRole == UserRole.ADMIN || userRole == UserRole.EMPLOYEE;
	    }
	    
	    static void updatePersonalInformation(String employeeId, String newAddress, UserRole userRole) {
	        if (hasHRUpdateAccess(userRole)) {
	            // Update address
	            // You should implement the actual update logic here
	        } else {
	            System.out.println("Unauthorized to update personal information.");
	        }
	    }

	    static void updateGovernmentIDs(String employeeId, String newSssNo, String newPhilhealthNo, String newTinNo, String newPagibigNo, UserRole userRole) {
	        if (hasPayrollUpdateAccess(userRole)) {
	            // Update government IDs
	            // You should implement the actual update logic here
	        } else {
	            System.out.println("Unauthorized to update government IDs.");
	        }
	    }
	    
	    static void updateEmploymentInformation(String employeeId, String newStatus, String newPosition, String newSupervisor, UserRole userRole) {
	        if (hasHRUpdateAccess(userRole)) {
	            // Update employment information
	            // You should implement the actual update logic here
	        } else {
	            System.out.println("Unauthorized to update employment information.");
	        }
	    }

	    static void updateSalaryInformation(String employeeId, double newBasicSalary, double newRiceSubsidy, double newPhoneAllowance, double newClothingAllowance, double newGrossSemiMonthlyRate, double newHourlyRate, UserRole userRole) {
	        if (hasPayrollUpdateAccess(userRole)) {
	            // Update salary information
	            // You should implement the actual update logic here
	        } else {
	            System.out.println("Unauthorized to update salary information.");
	        }
	    }
	    static boolean hasAccessToUpdatePersonalInformation(UserRole userRole) {
	        return userRole == UserRole.ADMIN || userRole == UserRole.HR;
	    }

	    static boolean hasAccessToUpdateGovernmentIDs(UserRole userRole) {
	        return userRole == UserRole.ADMIN || userRole == UserRole.PAYROLL;
	    }

	    static boolean hasAccessToUpdateSalaryInformation(UserRole userRole) {
	        return userRole == UserRole.ADMIN || userRole == UserRole.PAYROLL;
	    }

	    static boolean hasAccessToUpdateEmploymentDetails(UserRole userRole) {
	        return userRole == UserRole.ADMIN || userRole == UserRole.HR;
	    }

		public static void writeUpdatedInfo(String employeeID, String[] existingInfo) {
			// TODO Auto-generated method stub
			
		}
	
		}
	    
	
