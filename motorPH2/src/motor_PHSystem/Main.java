package motor_PHSystem;

public class Main {
    public static void main(String[] args) {
        // Create an instance of LoginManager
        LoginManager loginManager = new LoginManager();
        
        // Create an instance of UserInterface and pass the LoginManager to its constructor
        UserInterface userInterface = new UserInterface(loginManager);
        
        // Start the system by calling the start method of UserInterface
        userInterface.start();
    }
}
