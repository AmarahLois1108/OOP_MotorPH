package motor_PHSystem;

import java.util.Scanner;

public class DeductionsCalculator {

	 public static double calculateSSSContribution(double basicSalary) {
	        // Define the compensation range and corresponding contribution
	        double[][] ranges = {
	            { 3250, 135 }, { 3750, 157.5 }, { 4250, 180 }, { 4750, 202.5 }, { 5250, 225 }, { 5750, 247.5 }, { 6250, 270 }, { 6750, 292.5 },
	            { 7250, 315 }, { 7750, 337.5 }, { 8250, 360 }, { 8750, 382.5 }, { 9250, 405 }, { 9750, 427.5 }, { 10250, 450 }, { 10750, 472.5 },
	            { 11250, 495 }, { 11750, 517.5 }, { 12250, 540 }, { 12750, 562.5 }, { 13250, 585 }, { 13750, 607.5 }, { 14250, 630 }, { 14750, 652.5 },
	            { 15250, 675 }, { 15750, 697.5 }, { 16250, 720 }, { 16750, 742.5 }, { 17250, 765 }, { 17750, 787.5 }, { 18250, 810 }, { 18750, 832.5 },
	            { 19250, 855 }, { 19750, 877.5 }, { 20250, 900 }, { 20750, 922.5 }, { 21250, 945 }, { 21750, 967.5 }, { 22250, 990 }, { 22750, 1012.5 },
	            { 23250, 1035 }, { 23750, 1057.5 }, { 24250, 1080 }, { 24750, 1102.5 }, { 25250, 1125 }
	        };

	        // Iterate through the ranges to find the appropriate contribution
	        for (int i = 0; i < ranges.length; i++) {
	            double lowerBound = ranges[i][0];
	            double contribution = ranges[i][1];
	            if (basicSalary < lowerBound) {
	                return contribution;
	            }
	        }

	        // If the basic salary exceeds the maximum range, return the maximum contribution
	        return 1125;
	    }

	 public static double calculatePhilHealthContribution(double basicSalary) {
	        double monthlyPremium = 0;

	        if (basicSalary <= 10000) {
	            monthlyPremium = 300;
	        } else if (basicSalary <= 59999.99) {
	            monthlyPremium = 300 + (basicSalary - 10000) * 0.03;
	            monthlyPremium = Math.min(monthlyPremium, 1800); // Cap the monthly premium to 1800
	        } else if (basicSalary >= 60000) {
	            monthlyPremium = 1800;
	        }

	        // Monthly premium contribution payments are equally shared between the employee and employer
	        return monthlyPremium / 2; // Return the employee's share
	    }


	 public static double calculatePagIBIGContribution(double basicSalary) {
	        double employeeContributionRate;
	        double employerContributionRate;
	        
	        if (basicSalary >= 1000 && basicSalary <= 1500) {
	            employeeContributionRate = 0.01;
	            employerContributionRate = 0.02;
	        } else {
	            employeeContributionRate = 0.02;
	            employerContributionRate = 0.02;
	        }

	        double employeeContribution = Math.min(basicSalary * employeeContributionRate, 100);
	        double employerContribution = Math.min(basicSalary * employerContributionRate, 100);
	        
	        return employeeContribution; // Return the employee's share
	    }

	 public static double calculateTax(double basicSalary, double sssDeduction, double philHealthDeduction, double pagIBIGDeduction) {
	        double totalDeductions = sssDeduction + philHealthDeduction + pagIBIGDeduction;
	        double taxableIncome = basicSalary - totalDeductions;
	        
	        double withholdingTax = 0;
	        if (taxableIncome <= 20832) {
	            withholdingTax = 0;
	        } else if (taxableIncome > 20832 && taxableIncome <= 33333) {
	            withholdingTax = (taxableIncome - 20833) * 0.20;
	        } else if (taxableIncome > 33333 && taxableIncome <= 66667) {
	            withholdingTax = 2500 + (taxableIncome - 33333) * 0.25;
	        } else if (taxableIncome > 66667 && taxableIncome <= 166667) {
	            withholdingTax = 10833 + (taxableIncome - 66667) * 0.30;
	        } else if (taxableIncome > 166667 && taxableIncome <= 666667) {
	            withholdingTax = 40833.33 + (taxableIncome - 166667) * 0.32;
	        } else if (taxableIncome > 666667) {
	            withholdingTax = 200833.33 + (taxableIncome - 666667) * 0.35;
	        }
	        
	        return withholdingTax;
	    }

	 public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);

	        System.out.print("Enter the employee's basic salary: ");
	        double basicSalary = scanner.nextDouble();

	        // Calculate SSS, PhilHealth, and Pag-IBIG deductions based on the basic salary
	        double sssDeduction = calculateSSSContribution(basicSalary);
	        double philHealthDeduction = calculatePhilHealthContribution(basicSalary);
	        double pagIBIGDeduction = calculatePagIBIGContribution(basicSalary);
	        double taxContribution = calculateTax(basicSalary, sssDeduction, philHealthDeduction, pagIBIGDeduction);

	        System.out.println("SSS Deduction: " + sssDeduction);
	        System.out.println("PhilHealth Deduction: " + philHealthDeduction);
	        System.out.println("Pag-IBIG Deduction: " + pagIBIGDeduction);
	        System.out.println("Tax Contribution: " + taxContribution);

	        scanner.close();
	    }
	    
	    // Other methods for calculating SSS, PhilHealth, Pag-IBIG, and tax contributions...
	}