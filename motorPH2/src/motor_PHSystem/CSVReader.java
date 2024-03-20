package motor_PHSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {
	
    public static List<Employee> readEmployeesFromCSV(String filePath) {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // Flag to skip the header row
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header row
                }
                String[] data = line.split(",");
                if (data.length >= 20) { // Ensure the line has the required fields
                    Employee employee = createEmployeeFromCSVData(data);
                    employees.add(employee);
                } else {
                    System.out.println("Invalid line in CSV: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employees;
    }

    private static Employee createEmployeeFromCSVData(String[] data) {
        try {
            String employeeID = data[0].trim();
            String lastName = data[1].trim();
            String firstName = data[2].trim();
            Date birthday = new SimpleDateFormat("MM/dd/yyyy").parse(data[3].trim());
            String address = data[4].trim();
            String phoneNumber = data[5].trim();
            String sssNumber = data[6].trim();
            String philhealthNumber = data[7].trim();
            String tinNumber = data[8].trim();
            String pagIbigNumber = data[9].trim();
            String status = data[10].trim();
            String position = data[11].trim();
            String immediateSupervisor = data[12].trim();
            double basicSalary = Double.parseDouble(data[13].trim());
            double riceSubsidy = Double.parseDouble(data[14].trim());
            double phoneAllowance = Double.parseDouble(data[15].trim());
            double clothingAllowance = Double.parseDouble(data[16].trim());
            double grossSemiMonthlyRate = Double.parseDouble(data[17].trim());
            double hourlyRate = Double.parseDouble(data[18].trim());

            return new Employee(employeeID, lastName, firstName, birthday, address, phoneNumber, sssNumber,
                    philhealthNumber, tinNumber, pagIbigNumber, status, position, immediateSupervisor, basicSalary,
                    riceSubsidy, phoneAllowance, clothingAllowance, grossSemiMonthlyRate, hourlyRate);
        } catch (ParseException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error parsing employee data: " + e.getMessage());
            return null;
        }
    }

    public static Map<String, String[]> loadUsersFromCSV(String filePath) {
        Map<String, String[]> users = new HashMap<>();
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
                    String username = data[2];
                    users.put(username, data); // Username-User data mapping
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
                if (data.length >= 2) { // Ensure the line has the required fields
                    String roleID = data[0].trim();
                    String roleType = data[1].trim();
                    rolesMap.put(roleID, roleType);
                } else {
                    System.out.println("Invalid line in CSV: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rolesMap;
    }

    public static void saveUsersToCSV(String filePath, String[] userData, int maxUserID) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String userID = String.valueOf(maxUserID);
            String line = String.join(",", userID, userData[1], userData[2], userData[3], userData[4]);
            writer.write(line + "\n");
            System.out.println("User added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
