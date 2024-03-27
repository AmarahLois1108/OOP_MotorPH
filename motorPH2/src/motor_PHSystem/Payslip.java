package motor_PHSystem;

import java.time.LocalDate;

public class Payslip {
    private String payrollId;
    private String employeeId;
    private LocalDate payPeriod;
    private double hoursWorked;
    private double grossSalary;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;
    private double sssContribution;
    private double pagIbigContribution;
    private double philHealthContribution;
    private double tax;
    private double netSalary;
    private String status;
    private String position;
    private double hourlyRate;

    public Payslip(String payrollId, String employeeId, LocalDate payPeriod, double hoursWorked, double grossSalary,
                   double riceSubsidy, double phoneAllowance, double clothingAllowance,
                   double sssContribution, double pagIbigContribution,
                   double philHealthContribution, double tax, double netSalary,
                   String status, String position, double hourlyRate) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.payPeriod = payPeriod;
        this.hoursWorked = hoursWorked;
        this.grossSalary = grossSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.sssContribution = sssContribution;
        this.pagIbigContribution = pagIbigContribution;
        this.philHealthContribution = philHealthContribution;
        this.tax = tax;
        this.netSalary = netSalary;
        this.status = status;
        this.position = position;
        this.hourlyRate = hourlyRate;
    }

    // Getter methods for accessing attributes

    public String getPayrollId() {
        return payrollId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public LocalDate getPayPeriod() {
        return payPeriod;
    }

    // Add getter methods for other attributes

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public double getRiceSubsidy() {
        return riceSubsidy;
    }

    public double getPhoneAllowance() {
        return phoneAllowance;
    }

    public double getClothingAllowance() {
        return clothingAllowance;
    }

    public double getSssContribution() {
        return sssContribution;
    }

    public double getPagIbigContribution() {
        return pagIbigContribution;
    }

    public double getPhilHealthContribution() {
        return philHealthContribution;
    }

    public double getTax() {
        return tax;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public String getStatus() {
        return status;
    }

    public String getPosition() {
        return position;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}
