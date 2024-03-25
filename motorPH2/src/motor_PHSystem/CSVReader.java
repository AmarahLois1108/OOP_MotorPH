package motor_PHSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVReader {
    public static Map<String, User> loadUsersFromCSV() {
        String filePath = "UsersLogin.csv"; // Specify the file path here
        Map<String, User> users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // Flag to skip the header row
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header row
                }
                String[] data = line.split(",");
                if (data.length >= 5) { // Ensure the line has the required fields
                    String userID = data[0].trim();
                    String employeeID = data[1].trim();
                    String username = data[2].trim();
                    String password = data[3].trim();
                    String roleType = data[4].trim();
                    User user = new User(userID, employeeID, username, password, roleType);
                    users.put(username, user); // Username-User data mapping
                } else {
                    System.out.println("Invalid line in CSV: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }



    public static Map<String, String> loadRolesFromCSV(String filePath) {
        Map<String, String> rolesMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // Flag to skip the header row
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header row
                }
                String[] data = line.split(",");
                if (data.length >= 5) { // Ensure the line has the required fields
                    String roleType = data[4].trim();
                    rolesMap.put(roleType, roleType); // Store role type as both key and value
                } else {
                    System.out.println("Invalid line in CSV: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rolesMap;
    }

    public static void main(String[] args) {
        // Example usage:
        Map<String, String> rolesMap = loadRolesFromCSV("UsersLogin.csv");
        System.out.println("Loaded roles:");
        for (Map.Entry<String, String> entry : rolesMap.entrySet()) {
            System.out.println("Role type: " + entry.getValue());
        }
    }

    
    public static Map<String, String[]> loadEmployeesFromCSV(String filePath) {
        Map<String, String[]> employees = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // Flag to skip the header row
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header row
                }
                String[] data = line.split("\t");
                if (data.length >= 16) { // Ensure the line has the required fields
                    String employeeID = data[0];
                    employees.put(employeeID, data); // EmployeeID-Employee data mapping
                } else {
                    System.out.println("Invalid line in CSV: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }
}

