package motor_PHSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

public class Calculations {
	

    public static double calculateHoursWorkedInWeek(String employeeId, int weekNumber) {
        double hoursWorkedInWeek = 0.0; // Initialize to 0
        try (BufferedReader reader = new BufferedReader(new FileReader("timesheet2.txt"))) {
            String line;
            String[] parts = null;
            boolean foundEmployee = false;

            while ((line = reader.readLine()) != null) {
                parts = line.split("\t");
                if (parts[0].equals(employeeId)) {
                    foundEmployee = true;
                    LocalDate date = LocalDate.parse(parts[1]);
                    if (date.getYear() == 2022 && date.get(WeekFields.ISO.weekOfWeekBasedYear()) == weekNumber) {
                        LocalTime timeIn = LocalTime.parse(parts[2], DateTimeFormatter.ofPattern("H:mm"));
                        LocalTime timeOut = LocalTime.parse(parts[3], DateTimeFormatter.ofPattern("H:mm"));
                        double hoursWorked = Duration.between(timeIn, timeOut).toHours();
                        hoursWorkedInWeek += hoursWorked;
                    }
                }
            }
            if (!foundEmployee) {
                System.out.println("Employee not found in timesheet.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Deduct 1 hour only if hoursWorkedInWeek is greater than 0
        if (hoursWorkedInWeek > 0) {
            hoursWorkedInWeek -= 1;
        }
        return hoursWorkedInWeek;
    }

    public static double calculateWeeklyGrossSalary(String employeeId, int weekNumber) {
        double riceSubsidy = GetData.getRiceSubsidy(employeeId);
        double phoneAllowance = GetData.getPhoneAllowance(employeeId);
        double clothingAllowance = GetData.getClothingAllowance(employeeId);
        double hourlyRate = GetData.getHourlyRate(employeeId);

        try (BufferedReader reader = new BufferedReader(new FileReader("Employee_salary.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 7) {
                    // handle the error here, such as by skipping the line or logging a message
                    continue;
                }
                if (parts[0].equals(employeeId)) {
                    hourlyRate = Double.parseDouble(parts[6]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        double hoursWorkedInWeek = calculateHoursWorkedInWeek(employeeId, weekNumber);
        double weeklyGrossSalary = (hourlyRate * hoursWorkedInWeek) + ((riceSubsidy + phoneAllowance + clothingAllowance) / 4);
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedGrossSalary = df.format(weeklyGrossSalary);

        return Double.parseDouble(formattedGrossSalary);
    }

    public static double calculateWeeklyDeduction(String employeeId, int weekNumber, double weeklyGrossSalary) {
        double philhealth = GetData.getPhilhealthDeduction(employeeId);
        double sss = GetData.getSssDeduction(employeeId);
        double pagibig = GetData.getPagibigDeduction(employeeId);
        double tax = calculateWithholdingTax(weeklyGrossSalary);
        double totalDeduction = philhealth + sss + pagibig;
        double weeklyDeduction = (totalDeduction / 4.0) + tax;
        return weeklyDeduction;
    }

    public static double calculateWithholdingTax(double weeklyGrossSalary) {
        double annualGrossSalary = weeklyGrossSalary * 52;
        double taxableIncome = annualGrossSalary - 250000;
        double taxAmount = 0.0;
        if (taxableIncome <= 0) {
            taxAmount = 0.0;
        } else if (taxableIncome <= 400000) {
            taxAmount = taxableIncome * 0.20;
        } else if (taxableIncome <= 800000) {
            taxAmount = 40000 + (taxableIncome - 400000) * 0.25;
        } else if (taxableIncome <= 2000000) {
            taxAmount = 140000 + (taxableIncome - 800000) * 0.30;
        } else if (taxableIncome <= 8000000) {
            taxAmount = 540000 + (taxableIncome - 2000000) * 0.32;
        } else {
            taxAmount = 2320000 + (taxableIncome - 8000000) * 0.35;
        }
        double withholdingTax = taxAmount / 52;
        return withholdingTax;
    }

}

