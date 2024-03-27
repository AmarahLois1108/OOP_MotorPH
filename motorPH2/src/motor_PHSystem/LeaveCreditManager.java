package motor_PHSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LeaveCreditManager {
    private static final String LEAVE_CREDITS_FILE_PATH = "LeaveCredits.tsv";

    public static Map<String, Map<String, Integer>> readLeaveCredits() {
        Map<String, Map<String, Integer>> leaveCredits = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LEAVE_CREDITS_FILE_PATH))) {
            String line;
            // Skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 4) {
                    String employeeId = parts[0];
                    int sickLeaves = Integer.parseInt(parts[1]);
                    int vacationLeaves = Integer.parseInt(parts[2]);
                    int emergencyLeaves = Integer.parseInt(parts[3]);

                    Map<String, Integer> employeeCredits = leaveCredits.getOrDefault(employeeId, new HashMap<>());
                    employeeCredits.put("Sick", sickLeaves);
                    employeeCredits.put("Vacation", vacationLeaves);
                    employeeCredits.put("Emergency", emergencyLeaves);

                    leaveCredits.put(employeeId, employeeCredits);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return leaveCredits;
    }


    public static void updateLeaveCredits(Map<String, Map<String, Integer>> leaveCredits) {
        try (FileWriter writer = new FileWriter(LEAVE_CREDITS_FILE_PATH)) {
            // Write header
            writer.append("Employee ID\tSick \tVacation \tEmergency \n");
            for (Map.Entry<String, Map<String, Integer>> entry : leaveCredits.entrySet()) {
                String employeeId = entry.getKey();
                Map<String, Integer> employeeCredits = entry.getValue();
                int sickLeaves = employeeCredits.getOrDefault("Sick", 0);
                int vacationLeaves = employeeCredits.getOrDefault("Vacation", 0);
                int emergencyLeaves = employeeCredits.getOrDefault("Emergency", 0);

                writer.append(employeeId).append("\t")
                        .append(String.valueOf(sickLeaves)).append("\t")
                        .append(String.valueOf(vacationLeaves)).append("\t")
                        .append(String.valueOf(emergencyLeaves)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static int getAvailableLeaveCredits(String employeeId, String leaveType) {
        Map<String, Map<String, Integer>> leaveCredits = readLeaveCredits();
        Map<String, Integer> employeeCredits = leaveCredits.getOrDefault(employeeId, new HashMap<>());
        return employeeCredits.getOrDefault(leaveType, 0);
    }

    public static void deductLeaveCredits(String employeeId, String leaveType, int days) {
        Map<String, Map<String, Integer>> leaveCredits = readLeaveCredits();
        Map<String, Integer> employeeCredits = leaveCredits.getOrDefault(employeeId, new HashMap<>());
        int availableCredits = employeeCredits.getOrDefault(leaveType, 0);
        int updatedCredits = Math.max(availableCredits - days, 0);
        employeeCredits.put(leaveType, updatedCredits);
        leaveCredits.put(employeeId, employeeCredits);
        updateLeaveCredits(leaveCredits);
    }
    
    public static void approveLeave(String employeeId, String leaveType, int days) {
        deductLeaveCredits(employeeId, leaveType, days);
    }
    
    public static void main(String[] args) {
        // Employee ID for which you want to check leave credits
        String employeeId = "1"; // Replace with the desired employee ID
        
        // Displaying available leave credits for the employee
        displayAvailableLeaveCredits(employeeId);
    }
    
    public static void displayAvailableLeaveCredits(String employeeId) {
        int sickLeaveCredits = LeaveCreditManager.getAvailableLeaveCredits(employeeId, "Sick Leave");
        int vacationLeaveCredits = LeaveCreditManager.getAvailableLeaveCredits(employeeId, "Vacation Leave");
        int emergencyLeaveCredits = LeaveCreditManager.getAvailableLeaveCredits(employeeId, "Emergency Leave");
        
        System.out.println();
        System.out.println("Available Leave Credits for Employee ID: " + employeeId);
        System.out.println(); Credits:
        System.out.println("Sick Leave: " + sickLeaveCredits);
        System.out.println("Vacation Leave: " + vacationLeaveCredits);
        System.out.println("Emergency Leave: " + emergencyLeaveCredits);
    }
}

