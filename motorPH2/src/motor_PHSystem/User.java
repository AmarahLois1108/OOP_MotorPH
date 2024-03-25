package motor_PHSystem;

public class User {
    private String userID;
    private String employeeID;
    private String username;
    private String password;
    private String roleType;

    // Constructor
    public User(String userID, String employeeID, String username, String password, String roleType) {
        this.userID = userID;
        this.employeeID = employeeID;
        this.username = username;
        this.password = password;
        this.roleType = roleType;
    }

    // Getters
    public String getUserID() {
        return userID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRoleType() {
        return roleType;
    }

    // Setters
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}


