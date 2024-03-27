package motor_PHSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class LeaveApplication {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	private static final String LEAVE_REQUEST_FILE_PATH = "LeaveRequest.tsv";
	
	public static void applyLeave(String employeeId, String leaveType, LocalDate startDate, LocalDate endDate, String message) {
	    int days = calculateLeaveDays(startDate, endDate);
	    int availableCredits = LeaveCreditManager.getAvailableLeaveCredits(employeeId, leaveType);
	    if (availableCredits >= days) {
	        // Proceed with leave application
	        String dateFiled = getCurrentDate();
	        String leaveId = generateUniqueLeaveID();

	        // Write leave status to leave_status.csv file
	        String leaveRequestFilePath = "LeaveRequest.tsv";
	        try (FileWriter writer = new FileWriter(leaveRequestFilePath, true)) {
	            writer.append(leaveId).append("\t") // Append LeaveID
	                    .append(employeeId).append("\t")
	                    .append(dateFiled).append("\t")
	                    .append(leaveType).append("\t")
	                    .append(startDate.format(DATE_FORMATTER)).append("\t")
	                    .append(endDate.format(DATE_FORMATTER)).append("\t")
	                    .append(message).append("\t")
	                    .append("PENDING").append("\n");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Insufficient leave credits.");
	    }
	}

	    private static String getCurrentDate() {
	        return LocalDate.now().format(DATE_FORMATTER);
	    }

	    static int calculateLeaveDays(LocalDate startDate, LocalDate endDate) {
	        try {
	            int numberOfDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1; // Adding 1 to include both the start and end dates
	            if (numberOfDays < 0) {
	                return 1;
	            }
	            return numberOfDays;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return -1; // Return -1 to indicate an error
	        }
	    }
    private static String generateUniqueLeaveID() {
        String lastLeaveID = "0"; // Default value if no leave ID found
        try (BufferedReader reader = new BufferedReader(new FileReader(LEAVE_REQUEST_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lastLeaveID = line.split("\t")[0]; // Assuming leave ID is the first field
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Extract the numeric part of the leave ID and increment it by 1
        int numericPart = Integer.parseInt(lastLeaveID) + 1;

        // Format the incremented numeric part as a 3-digit string with leading zeros
        String formattedNumericPart = String.format("%03d", numericPart);

        // Construct and return the new leave ID
        return formattedNumericPart;
    }


}