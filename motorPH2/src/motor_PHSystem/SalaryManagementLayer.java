package motor_PHSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalaryManagementLayer {

	public class PayslipManager {

	    private static final String PAYROLL_FILE_PATH = "payroll.tsv"; // Adjust the file path as per your setup

	    public static List<Payslip> generatePayslips(LocalDate payPeriod) {
	        List<Payslip> payslips = new ArrayList<>();
	        try (BufferedReader reader = new BufferedReader(new FileReader(PAYROLL_FILE_PATH))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split("\t");
	                // Extract payroll information from each line
	                String payrollId = parts[0];
	                String employeeId = parts[1];
	                double hoursWorked = Double.parseDouble(parts[3]);
	                double grossSalary = Double.parseDouble(parts[4]);
	                double riceSubsidy = Double.parseDouble(parts[5]);
	                double phoneAllowance = Double.parseDouble(parts[6]);
	                double clothingAllowance = Double.parseDouble(parts[7]);
	                double sssContribution = Double.parseDouble(parts[8]);
	                double pagIbigContribution = Double.parseDouble(parts[9]);
	                double philHealthContribution = Double.parseDouble(parts[10]);
	                double tax = Double.parseDouble(parts[11]);
	                double netSalary = Double.parseDouble(parts[12]);
	                
	                // Retrieve status, position, and hourly rate from Employee class
	                String status = Employee.getStatus(employeeId);
	                String position = Employee.getPosition(employeeId);
	                double hourlyRate = Employee.getHourlyRate(employeeId);

	                // Create a Payslip object and add it to the list
	                Payslip payslip = new Payslip(payrollId, employeeId, payPeriod, hoursWorked, grossSalary,
	                        riceSubsidy, phoneAllowance, clothingAllowance,
	                        sssContribution, pagIbigContribution,
	                        philHealthContribution, tax, netSalary, status, position, hourlyRate);
	                payslips.add(payslip);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return payslips;
	    }
	}
}