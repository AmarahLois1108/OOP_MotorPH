package motor_PHSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LeaveCreditManager {
    private static final String CREDITS_FILE_PATH = "Leave_Credits.csv";

    public static Map<String, Map<String, Integer>> readLeaveCredits() {
        Map<String, Map<String, Integer>> leaveCredits = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDITS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String employeeId = parts[0];
                    int sickLeaves = Integer.parseInt(parts[1]);
                    int vacationLeaves = Integer.parseInt(parts[2]);
                    int emergencyLeaves = Integer.parseInt(parts[3]);

                    Map<String, Integer> employeeCredits = leaveCredits.getOrDefault(employeeId, new HashMap<>());
                    employeeCredits.put("Sick Leave", sickLeaves);
                    employeeCredits.put("Vacation Leave", vacationLeaves);
                    employeeCredits.put("Emergency Leave", emergencyLeaves);

                    leaveCredits.put(employeeId, employeeCredits);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return leaveCredits;
    }


    public static void updateLeaveCredits(Map<String, Map<String, Integer>> leaveCredits) {
        try (FileWriter writer = new FileWriter(CREDITS_FILE_PATH)) {
            for (Map.Entry<String, Map<String, Integer>> entry : leaveCredits.entrySet()) {
                String employeeId = entry.getKey();
                Map<String, Integer> employeeCredits = entry.getValue();
                int sickLeaves = employeeCredits.getOrDefault("Sick Leave", 0);
                int vacationLeaves = employeeCredits.getOrDefault("Vacation Leave", 0);
                int emergencyLeaves = employeeCredits.getOrDefault("Emergency Leave", 0);

                writer.append(employeeId).append(",")
                        .append(String.valueOf(sickLeaves)).append(",")
                        .append(String.valueOf(vacationLeaves)).append(",")
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
}
