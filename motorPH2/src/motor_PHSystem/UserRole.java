package motor_PHSystem;

public enum UserRole {
    ADMIN,
    HR,
    PAYROLL,
    EMPLOYEE;
    
   
        // Method to convert a string to UserRole enum value
        public static UserRole fromString(String roleType) {
            for (UserRole role : UserRole.values()) {
                if (role.name().equalsIgnoreCase(roleType)) {
                    return role;
                }
            }
            return null; // Return null if roleType is not recognized
        }
    }

