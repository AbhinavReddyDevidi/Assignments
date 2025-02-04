import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.Scanner;

public static Connection getConnection() throws SQLException {
    try {
        Class.forName("org.postgresql.Driver"); // Explicit driver loading
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/mpdb", "postgres", "tiger");
    } catch (ClassNotFoundException e) {
        throw new SQLException("PostgreSQL JDBC Driver not found!", e);
    }
}

    public static void createEmployee() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee details:");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();

        System.out.print("Salary: ");
        double salary = scanner.nextDouble();

        scanner.nextLine(); 

        System.out.println("Select Designation:");
        System.out.println("1. Clerk");
        System.out.println("2. Programmer");
        System.out.println("3. Manager");
        System.out.println("4. Others");
        System.out.println("5. Exit");

        System.out.print("Enter choice: ");
        int designationChoice = scanner.nextInt();
        scanner.nextLine();
        
        String designation = "";
        switch (designationChoice) {
            case 1:
                designation = "Clerk";
                break;
            case 2:
                designation = "Programmer";
                break;
            case 3:
                designation = "Manager";
                break;
            case 4:
                designation = "Others";
                break;
            case 5:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        System.out.print("Department: ");
        String department = scanner.nextLine();

        String insertSQL = "INSERT INTO EMPLOYEE (NAME, AGE, SALARY, DESIGNATION, DEPARTMENT) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setDouble(3, salary);
            pstmt.setString(4, designation);
            pstmt.setString(5, department);
            pstmt.executeUpdate();
            System.out.println("Employee added successfully!");
        }
    }

    public static void displayEmployee() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose display option:");
        System.out.println("1. By ID");
        System.out.println("2. By Name");
        System.out.println("3. By Designation");
        System.out.println("4. By Age");
        System.out.println("5. By Salary");
        System.out.println("6. Exit");

        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String query = "SELECT * FROM EMPLOYEE ";

        switch (choice) {
            case 1:
                query += "ORDER BY EID ";
                break;
            case 2:
                query += "ORDER BY NAME ";
                break;
            case 3:
                query += "ORDER BY DESIGNATION ";
                break;
            case 4:
                query += "ORDER BY AGE ";
                break;
            case 5:
                query += "ORDER BY SALARY ";
                break;
            case 6:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        try (Connection con = getConnection();
             JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl("jdbc:postgresql://localhost:5432/mpdb");
            rowSet.setUsername("postgres");
            rowSet.setPassword("tiger");
            rowSet.setCommand(query);
            rowSet.execute();

            while (rowSet.next()) {
                System.out.println("EID: " + rowSet.getInt("EID") + ", Name: " + rowSet.getString("NAME") +
                        ", Age: " + rowSet.getInt("AGE") + ", Salary: " + rowSet.getDouble("SALARY") +
                        ", Designation: " + rowSet.getString("DESIGNATION") + ", Department: " + rowSet.getString("DEPARTMENT"));
            }
        }
    }
    
    
    public static void appraisal() throws SQLException {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter Employee ID for appraisal: ");
    int empId = scanner.nextInt();
    scanner.nextLine();
    String selectSQL = "SELECT EID, NAME, SALARY FROM EMPLOYEE WHERE EID = ?";
    
    try (Connection con = getConnection();
         JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {

        rowSet.setUrl("jdbc:postgresql://localhost:5432/mpdb");
        rowSet.setUsername("postgres");
        rowSet.setPassword("tiger");
        rowSet.setCommand(selectSQL);
        rowSet.setInt(1, empId);
        rowSet.execute();

        if (rowSet.next()) {
            double currentSalary = rowSet.getDouble("SALARY");
            System.out.println("Current Salary of " + rowSet.getString("NAME") + ": " + currentSalary);
            
            System.out.println("Choose Appraisal Method:");
            System.out.println("1. Increase by Fixed Amount");
            System.out.println("2. Increase by Percentage");
            System.out.println("3. Decrease by Fixed Amount");
            System.out.println("4. Decrease by Percentage");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            double newSalary = currentSalary;

            switch (choice) {
                case 1:
                    System.out.print("Enter the amount to increase: ");
                    double fixedIncrease = scanner.nextDouble();
                    newSalary = currentSalary + fixedIncrease;
                    break;
                case 2:
                    System.out.print("Enter the percentage to increase: ");
                    double percentageIncrease = scanner.nextDouble();
                    newSalary = currentSalary + (currentSalary * (percentageIncrease / 100));
                    break;
                case 3:
                    System.out.print("Enter the amount to decrease: ");
                    double fixedDecrease = scanner.nextDouble();
                    newSalary = currentSalary - fixedDecrease;
                    break;
                case 4:
                    System.out.print("Enter the percentage to decrease: ");
                    double percentageDecrease = scanner.nextDouble();
                    newSalary = currentSalary - (currentSalary * (percentageDecrease / 100));
                    break;
                default:
                    System.out.println("Invalid choice!");
                    return;
            }

            String updateSQL = "UPDATE EMPLOYEE SET SALARY = ? WHERE EID = ?";
            rowSet.setCommand(updateSQL);
            rowSet.setDouble(1, newSalary);
            rowSet.setInt(2, empId);
            rowSet.execute();

            System.out.println("Salary updated successfully! New Salary: " + newSalary);
        } else {
            System.out.println("Employee not found with the provided ID.");
        }
    }
}


    public static void searchEmployee() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose search option:");
        System.out.println("1. By ID");
        System.out.println("2. By Name");
        System.out.println("3. By Designation");
        System.out.println("4. By Age");
        System.out.println("5. By Salary");
        System.out.println("6. Exit");

        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String query = "SELECT * FROM EMPLOYEE WHERE ";

        switch (choice) {
            case 1:
                System.out.print("Enter Employee ID: ");
                int id = scanner.nextInt();
                query += "EID = ?";
                break;
            case 2:
                System.out.print("Enter Employee Name: ");
                String name = scanner.nextLine();
                query += "NAME = ?";
                break;
            case 3:
                System.out.print("Enter Employee Designation: ");
                String designation = scanner.nextLine();
                query += "DESIGNATION = ?";
                break;
            case 4:
                System.out.print("Enter Employee Age: ");
                int age = scanner.nextInt();
                query += "AGE = ?";
                break;
            case 5:
                System.out.print("Enter Employee Salary: ");
                double salary = scanner.nextDouble();
                query += "SALARY = ?";
                break;
            case 6:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        try (Connection con = getConnection();
             JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl("jdbc:postgresql://localhost:5432/mpdb");
            rowSet.setUsername("postgres");
            rowSet.setPassword("tiger");
            rowSet.setCommand(query);

            if (choice == 1) {
                rowSet.setInt(1, scanner.nextInt());
            } else {
                rowSet.setString(1, scanner.nextLine());
            }

            rowSet.execute();

            if (rowSet.next()) {
                System.out.println("EID: " + rowSet.getInt("EID") + ", Name: " + rowSet.getString("NAME") +
                        ", Age: " + rowSet.getInt("AGE") + ", Salary: " + rowSet.getDouble("SALARY") +
                        ", Designation: " + rowSet.getString("DESIGNATION") + ", Department: " + rowSet.getString("DEPARTMENT"));
            } else {
                System.out.println("No employee found with the given criteria.");
            }
        }
    }

    public static void removeEmployee() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter employee ID to remove: ");
        int empId = scanner.nextInt();

        String deleteSQL = "DELETE FROM EMPLOYEE WHERE EID = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, empId);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee removed successfully!");
            } else {
                System.out.println("Employee not found.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("-----------------------");
            System.out.println("1. Create");
            System.out.println("2. Display");
            System.out.println("3. Appraisal");
            System.out.println("4. Search");
            System.out.println("5. Remove");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    try {
                        createEmployee();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        displayEmployee();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        appraisal();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        searchEmployee();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    try {
                        removeEmployee();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
