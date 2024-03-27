package motor_PHSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PayrollGenerator {
    private static final String TIMESHEETS_FILE_PATH = "timesheets.csv";
    private static final String LEAVE_REQUEST_FILE_PATH = "leave_requests.csv";
    private static final String OVERTIME_FILE_PATH = "overtime.csv";
    private static final double REGULAR_WORK_HOURS = 8; // Regular work hours per day

    public static List<Payroll> generatePayroll(LocalDate payPeriod) {
        List<Payroll> payrolls = new ArrayList<>();

        List<LeaveRequest> approvedLeaveRequests = getApprovedLeaveRequests(payPeriod);
        List<Overtime> overtimeRecords = getOvertimeRecords(payPeriod);

        try (BufferedReader reader = new BufferedReader(new FileReader(TIMESHEETS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                int employeeId = Integer.parseInt(parts[1]);
                LocalDate date = LocalDate.parse(parts[2]);
                LocalTime timeIn = LocalTime.parse(parts[3]);
                LocalTime breakStart = LocalTime.parse(parts[4]);
                LocalTime breakEnd = LocalTime.parse(parts[5]);
                LocalTime timeOut = LocalTime.parse(parts[6]);

                Duration totalWorkDuration = calculateTotalWorkDuration(timeIn, breakStart, breakEnd, timeOut);
                double totalHoursWorked = totalWorkDuration.toHours();

                boolean hasApprovedLeave = hasApprovedLeave(approvedLeaveRequests, employeeId, date);
                boolean hasOvertime = hasOvertime(overtimeRecords, employeeId, date, totalWorkDuration);

                // Check if the total hours worked exceed regular work hours and adjust for overtime
                if (totalHoursWorked > REGULAR_WORK_HOURS) {
                    totalHoursWorked = REGULAR_WORK_HOURS; // Consider only regular work hours
                }

                // Calculate deductions for this employee based on their basic salary
                double basicSalary = Employee.getBasicSalary(String.valueOf(employeeId));
                double sssDeduction = DeductionsCalculator.calculateSSSContribution(basicSalary);
                double philHealthDeduction = DeductionsCalculator.calculatePhilHealthContribution(basicSalary);
                double pagIBIGDeduction = DeductionsCalculator.calculatePagIBIGContribution(basicSalary);
                double tax = DeductionsCalculator.calculateTax(basicSalary, sssDeduction, philHealthDeduction, pagIBIGDeduction);

                // Calculate net salary after deductions
                double netSalary = basicSalary - sssDeduction - philHealthDeduction - pagIBIGDeduction - tax;

                // Create payroll object and add it to the list
                Payroll payroll = new Payroll(employeeId, payPeriod, totalHoursWorked, basicSalary, netSalary,
                        hasApprovedLeave, hasOvertime);
                payrolls.add(payroll);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return payrolls;
    }

    private static Duration calculateTotalWorkDuration(LocalTime timeIn, LocalTime breakStart, LocalTime breakEnd, LocalTime timeOut) {
        Duration workDurationBeforeBreak = Duration.between(timeIn, breakStart);
        Duration workDurationAfterBreak = Duration.between(breakEnd, timeOut);
        return workDurationBeforeBreak.plus(workDurationAfterBreak);
    }

    private static List<LeaveRequest> getApprovedLeaveRequests(LocalDate payPeriod) {
        // Implement logic to read and retrieve approved leave requests for the given pay period
        return new ArrayList<>(); // Placeholder
    }

    private static List<Overtime> getOvertimeRecords(LocalDate payPeriod) {
        // Implement logic to read and retrieve overtime records for the given pay period
        return new ArrayList<>(); // Placeholder
    }

    private static boolean hasApprovedLeave(List<LeaveRequest> approvedLeaveRequests, int employeeId, LocalDate date) {
        // Implement logic to check if the employee has approved leave for the given date
        return false; // Placeholder
    }

    private static boolean hasOvertime(List<Overtime> overtimeRecords, int employeeId, LocalDate date, Duration totalWorkDuration) {
        // Implement logic to check if the employee
