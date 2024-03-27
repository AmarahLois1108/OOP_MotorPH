package motor_PHSystem;

import java.util.Scanner; // Import Scanner class

public class Main {
    public static void main(String[] args) {
        // Create an instance of LoginManager
        LoginManager loginManager = new LoginManager();
        
        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);
        
        // Create an instance of EmployeeSelfServiceLayer and pass the LoginManager and Scanner objects
        EmployeeSelfServiceLayer employeeSelfServiceLayer = new EmployeeSelfServiceLayer(loginManager, scanner);
        
        // Create an instance of UserInterface and pass both LoginManager and EmployeeSelfServiceLayer to its constructor
        UserInterface userInterface = new UserInterface(loginManager, employeeSelfServiceLayer);
        
        // Start the system by calling the start method of UserInterface
        userInterface.start();
        
        // Don't forget to close the scanner when it's no longer needed
        scanner.close();
    }
}