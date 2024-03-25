package motor_PHSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CSVWriter {

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
    public static void updateCSVFile(Map<String, String[]> users) {
        String filePath = "UsersLogin.csv";
        String tempFilePath = "temp.csv";
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             FileWriter writer = new FileWriter(tempFilePath)) {

            String header = br.readLine(); // Read the header line
            if (header == null) {
                System.out.println("Error: Empty CSV file.");
                return;
            }
            writer.write(header + "\n"); // Write the header to the new file

            String line;
            boolean updated = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[2].trim();
                if (users.containsKey(username)) {
                    String[] updatedUserData = users.get(username);
                    if (updatedUserData.length >= 3) {
                        // Update the data with the new values for username, password, and role
                        data[2] = updatedUserData[0]; // Updated username
                        data[3] = updatedUserData[1]; // Updated password
                        data[4] = updatedUserData[2]; // Updated role
                        updated = true;
                    } else {
                        System.out.println("Error: Insufficient data for user '" + username + "'.");
                        continue; // Skip updating this row
                    }
                }
                String updatedLine = String.join(",", data);
                writer.write(updatedLine + "\n");
            }

            if (!updated) {
                System.out.println("Warning: No data was updated.");
            } else {
                System.out.println("CSV file updated successfully.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error updating CSV file.");
        }

        // Perform file renaming after closing the file streams
        File originalFile = new File(filePath);
        File tempFile = new File(tempFilePath);
        if (tempFile.exists() && tempFile.isFile() && tempFile.length() > 0) {
            if (originalFile.delete()) {
                if (tempFile.renameTo(originalFile)) {
                    System.out.println("File renamed successfully.");
                } else {
                    System.out.println("Error renaming file.");
                }
            } else {
                System.out.println("Error deleting original file.");
            }
        } else {
            System.out.println("Error: Temporary file is missing or empty.");
        }
    }
    
    public static void deleteUserDataFromCSV(String filePath, String usernameToDelete) {
        String tempFilePath = "temp.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             FileWriter writer = new FileWriter(tempFilePath)) {

            String header = br.readLine(); // Read the header line
            if (header == null) {
                System.out.println("Error: Empty CSV file.");
                return;
            }
            writer.write(header + "\n"); // Write the header to the new file

            String line;
            boolean deleted = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[2].trim();
                if (username.equals(usernameToDelete)) {
                    // Skip writing this user's data to the new file
                    deleted = true;
                    continue;
                }
                String updatedLine = String.join(",", data);
                writer.write(updatedLine + "\n");
            }

            if (deleted) {
                System.out.println("User data deleted successfully.");
            } else {
                System.out.println("User data not found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error deleting user data from CSV file.");
        }

        // Perform file renaming after closing the file streams
        File originalFile = new File(filePath);
        File tempFile = new File(tempFilePath);
        if (tempFile.exists() && tempFile.isFile() && tempFile.length() > 0) {
            if (originalFile.delete()) {
                if (tempFile.renameTo(originalFile)) {
                    System.out.println("File renamed successfully.");
                } else {
                    System.out.println("Error renaming file.");
                }
            } else {
                System.out.println("Error deleting original file.");
            }
        } else {
            System.out.println("Error: Temporary file is missing or empty.");
        }
    }
}
