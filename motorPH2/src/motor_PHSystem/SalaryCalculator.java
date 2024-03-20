package motor_PHSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SalaryCalculator {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    // Function to read CSV file
 // Function to read CSV file
    private static void readCSV(String filename) {
        // Read the CSV file line by line and process the data
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean skipHeader = true; // Flag to skip the header row
            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue; // Skip processing the header row
                }
                // Split the line into fields
                String[] fields = line.split(",");
                // Process the data accordingly
                if (fields.length >= 7) { // Ensure that there are enough fields
                    String attendanceId = fields[0];
                    int employeeId = Integer.parseInt(fields[1]);
                    Date date = dateFormat.parse(fields[2]);
                    Date timeIn = timeFormat.parse(fields[3]);
                    Date breakStart = timeFormat.parse(fields[4]);
                    Date breakEnd = timeFormat.parse(fields[5]);
                    Date timeOut = timeFormat.parse(fields[6]);

                    // Example: calculate hours worked, check overtime approval, check leave approval, calculate salary, etc.
                    double hoursWorked = calculateHoursWorked(timeIn, breakStart, breakEnd, timeOut);
                    boolean overtimeApproved = checkOvertimeApproval(employeeId, date, hoursWorked, timeOut, timeOut, timeOut, timeOut);
                    boolean leaveApproved = checkLeaveApproval(employeeId, date);
                    double salary = calculateSalary(date, employeeId, hoursWorked, overtimeApproved, leaveApproved);

                    // Output the results or further process them
                    System.out.println("Employee ID: " + employeeId + ", Date: " + dateFormat.format(date) + ", Hours Worked: " + hoursWorked + ", Salary: " + salary);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


    // Function to calculate hours worked
    private static double calculateHoursWorked(Date timeIn, Date breakStart, Date breakEnd, Date timeOut) {
        long workTimeInMillis = timeOut.getTime() - timeIn.getTime(); // Total time from time in to time out
        long breakTimeInMillis = breakEnd.getTime() - breakStart.getTime(); // Total break time
        long workingTimeWithoutBreak = workTimeInMillis - breakTimeInMillis; // Total working time without break
        return (double) workingTimeWithoutBreak / (1000 * 60 * 60); // Convert milliseconds to hours
    }

    // Function to check overtime approval
    private static boolean checkOvertimeApproval(int employeeId, Date date, double hoursWorked, Date timeIn, Date breakStart, Date breakEnd, Date timeOut) {
        String filename = "overtime_requests.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    int overtimeId = Integer.parseInt(fields[0]);
                    int empId = Integer.parseInt(fields[1]);
                    Date overtimeDate = dateFormat.parse(fields[2]);
                    Date startTime = timeFormat.parse(fields[3]);
                    Date endTime = timeFormat.parse(fields[4]);
                    String status = fields[5];

                    // Check if the overtime request matches the given employee and falls on the given date
                    if (empId == employeeId && overtimeDate.equals(date)) {
                        // Check if the time period for overtime overlaps with the actual working hours
                        if (startTime.before(timeIn) && endTime.after(timeOut)) {
                            // Calculate the overtime hours based on the actual working hours
                            double overtimeHours = calculateHoursWorked(timeIn, breakStart, breakEnd, timeOut);
                            return overtimeHours > 0 && overtimeHours > hoursWorked; // Overtime is approved if hours worked exceed regular hours
                        }
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false; // Overtime is not approved or not found
    }



    // Function to check leave approval
    private static boolean checkLeaveApproval(int employeeId, Date date) {
        String filename = "leave_requests.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    int leaveId = Integer.parseInt(fields[0]);
                    int empId = Integer.parseInt(fields[1]);
                    Date startDate = dateFormat.parse(fields[2]);
                    Date endDate = dateFormat.parse(fields[3]);
                    String leaveType = fields[4];
                    String status = fields[5];

                    // Check if the leave is for the given employee and the date falls within the leave period
                    if (empId == employeeId && !date.before(startDate) && !date.after(endDate) && status.equalsIgnoreCase("Approved")) {
                        return true; // Leave is approved for the given employee and date
                    }
                }
            }
        } catch (IOException | java.text.ParseException e) {
            e.printStackTrace();
        }
        return false; // Leave is not approved or not found
    }

    // Function to calculate salary
    private static double calculateSalary(Date date, int employeeId, double hoursWorked, boolean overtimeApproved, boolean leaveApproved) {
        // Placeholder logic to calculate salary
        double baseSalary = 1000; // Example base salary
        double hourlyRate = baseSalary / 160; // Assuming 160 hours per month
        double salary = hoursWorked * hourlyRate; // Base salary based on hours worked

        // Apply overtime rate if applicable
        if (overtimeApproved) {
            double overtimeRate = 1.5; // Example overtime rate
            double overtimeHours = Math.max(hoursWorked - 8, 0); // Calculate overtime hours (after 8 hours)
            salary += overtimeHours * overtimeRate * hourlyRate; // Add overtime salary
        }

        // Deduct salary for approved leave
        if (leaveApproved) {
            salary -= baseSalary / 160; // Deduct one day's salary for each day of leave
        }

        return salary;
    }

    public static void main(String[] args) {
        String filename = "timesheet.csv";
        readCSV(filename);
    }
}
