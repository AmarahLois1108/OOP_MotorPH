package motor_PHSystem;

public class AccessControl {
    // Method to check if a user with a specific role has access to a particular functionality
    public static boolean hasAccess(UserRole userRole, Functionality functionality) {
        switch (userRole) {
            case ADMIN:
                return true; // ADMIN has access to all functionalities
            case HR:
                return isHRFunctionality(functionality);
            case PAYROLL:
                return isPayrollFunctionality(functionality);
            case EMPLOYEE:
                return isEmployeeFunctionality(functionality);
            default:
                return false; // Default to false if the role is not recognized
        }
    }

    // Private helper methods to check specific functionalities for each role
    private static boolean isHRFunctionality(Functionality functionality) {
        return functionality == Functionality.VIEW_PERSONAL_INFO ||
                functionality == Functionality.UPDATE_PERSONAL_INFO ||
                functionality == Functionality.UPDATE_EMPLOYMENT_INFO;
    }

    private static boolean isPayrollFunctionality(Functionality functionality) {
        return functionality == Functionality.VIEW_SALARY_INFO ||
                functionality == Functionality.UPDATE_GOVERNMENT_ID ||
                functionality == Functionality.UPDATE_SALARY_INFO;
    }

    private static boolean isEmployeeFunctionality(Functionality functionality) {
        return functionality == Functionality.VIEW_OWN_PERSONAL_INFO ||
                functionality == Functionality.UPDATE_OWN_PERSONAL_INFO ||
                functionality == Functionality.VIEW_OWN_SALARY_INFO;
                
    }

    // Define Functionality enum
    public enum Functionality {
        VIEW_PERSONAL_INFO, 
        VIEW_SALARY_INFO, 
        UPDATE_SALARY_INFO,
        UPDATE_PERSONAL_INFO,
        UPDATE_EMPLOYMENT_INFO,       
        UPDATE_GOVERNMENT_ID,
        VIEW_ALL_EMPLOYEE_PERSONAL_INFO, 
        VIEW_ALL_EMPLOYEE_SALARY_INFO, 
        ADD_NEW_EMPLOYEE, 
        DELETE_EMPLOYEE,
        ADD_NEW_USER,
        UPDATE_USER,
        DELETE_USER,
        VIEW_OWN_PERSONAL_INFO, 
        VIEW_OWN_SALARY_INFO,
        UPDATE_OWN_PERSONAL_INFO
    }
}
