package motor_PHSystem;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LeaveManagement {
    private static final String STATUS_FILE_PATH = "Leave_Status.csv";

    public static String getLeaveStatus(String employeeId, String leaveType) {
        // Read leave status from file or database
        // Return the leave status for the specified employee and leave type
        return null;
    }

    public static void updateLeaveStatus(String employeeId, String dateFiled, String leaveType, String startDate, String endDate, String status) {
        try (FileWriter writer = new FileWriter(STATUS_FILE_PATH, true)) {
            writer.append(employeeId).append(",").append(dateFiled).append(",").append(leaveType).append(",").append(startDate).append(",").append(endDate).append(",").append(status).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
