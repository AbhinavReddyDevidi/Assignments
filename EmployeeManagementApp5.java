import java.io.*;
import java.util.*;
import java.util.regex.*;

class InvalidAgeException extends Exception {
    public InvalidAgeException() {
        super();
    }

    public InvalidAgeException(String message) {
        super(message);
    }
}

class InvalidChoiceException extends Exception {
    public InvalidChoiceException() {
        super();
    }

    public InvalidChoiceException(String message) {
        super(message);
    }
}

class InvalidNameException extends Exception {
    public InvalidNameException() {
        super();
    }

    public InvalidNameException(String message) {
        super(message);
    }
}

class CeoMissingException extends Exception {
    public CeoMissingException() {
        super("Details of the CEO are missing");
    }

    public CeoMissingException(String message) {
        super(message);
    }
}

abstract class Emp {
    private String name;
    private int age;
    private int employeeId;
    protected int salary;
    private String designation;

    Emp(int sal, String design, int id) {
        Scanner sc = new Scanner(System.in);
        name = Checkers.NameCheck();
        age = Checkers.AgeCheck(21, 60);
        salary = sal;
        designation = design;
        this.employeeId = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getDgn() {
        return designation;
    }

    final public void display() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Salary: " + salary);
        System.out.println("Designation: " + designation);
    }

    public abstract void raiseSalary();

    public String toCSV() {
        return employeeId + "," + name + "," + age + "," + salary + "," + designation;
    }

    public static Emp fromCSV(String csvData) {
        String[] values = csvData.split(",");
        int id = Integer.parseInt(values[0]);
        String name = values[1];
        int age = Integer.parseInt(values[2]);
        int salary = Integer.parseInt(values[3]);
        String designation = values[4];

        switch (designation) {
            case "CLERK":
                return new Clerk(id, name, age, salary, designation);
            case "Programmer":
                return new Programmer(id, name, age, salary, designation);
            case "Manager":
                return new Manager(id, name, age, salary, designation);
            default:
                return null;
        }
    }
}

final class Ceo extends Emp {
    private static Ceo c1 = null;

    private Ceo(int id, String name, int age, int salary, String designation) {
        super(1000000, "CEO", id);
    }

    public static Ceo getCeo(int id) throws CeoMissingException {
        if (c1 == null) {
            c1 = new Ceo(id, "CEO", 45, 1000000, "CEO");
        }
        return c1;
    }

    public void raiseSalary() {
        salary += 50000;
    }
}

class Clerk extends Emp {
    Clerk(int id, String name, int age, int salary, String designation) {
        super(20000, "CLERK", id);
    }

    public void raiseSalary() {
        salary += 2000;
    }
}

class Programmer extends Emp {
    Programmer(int id, String name, int age, int salary, String designation) {
        super(20000, "Programmer", id);
    }

    public void raiseSalary() {
        salary += 5000;
    }
}

class Manager extends Emp {
    Manager(int id, String name, int age, int salary, String designation) {
        super(20000, "Manager", id);
    }

    public void raiseSalary() {
        salary += 15000;
    }
}

class Checkers {
    public static String NameCheck() {
        while (true) {
            System.out.print("Enter name: ");
            try {
                String name = new Scanner(System.in).nextLine();
                Pattern pat = Pattern.compile("^[A-Z][a-z]* [A-Z][a-z]*$");
                Matcher m = pat.matcher(name);
                if (m.matches()) {
                    return name;
                } else {
                    throw new InvalidNameException("Wrong Format of name");
                }
            } catch (InvalidNameException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int AgeCheck(int minAge, int maxAge) {
        while (true) {
            System.out.print("Enter age: ");
            try {
                int age = new Scanner(System.in).nextInt();
                if (age < minAge || age > maxAge) {
                    throw new InvalidAgeException("Age must be between " + minAge + " and " + maxAge + ".");
                } else {
                    return age;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a Number.");
            } catch (InvalidAgeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int choiceCheck(int maxChoice) {
        while (true) {
            System.out.print("Enter Choice: ");
            try {
                int choice = new Scanner(System.in).nextInt();
                if (choice < 1 || choice > maxChoice) {
                    throw new InvalidChoiceException("Choice must be between 1 and " + maxChoice);
                } else {
                    return choice;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a Number.");
            } catch (InvalidChoiceException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

public class EmployeeManagementApp5 {

    private static final String FILE_PATH = "employees.csv";

    public static void main(String[] args) {
        int ch1 = 0, ch2 = 0;
        HashMap<Integer, Emp> empMap = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        boolean ceoCreated = false;

        loadEmployeesFromCSV(empMap);

        do {
            System.out.println("--------------------------");
            System.out.println("1. Create");
            System.out.println("2. Display");
            System.out.println("3. Raise Salary");
            System.out.println("4. Remove");
            System.out.println("5. Search by ID");
            System.out.println("6. Exit");
            System.out.println("--------------------------");

            ch1 = Checkers.choiceCheck(6);

            if (ch1 == 1) {
                if (!ceoCreated) {
                    System.out.println("Enter the Details of CEO first");
                    try {
                        System.out.print("Enter CEO ID: ");
                        int ceoId = sc.nextInt();
                        Ceo ceo = Ceo.getCeo(ceoId);
                        empMap.put(ceo.getEmployeeId(), ceo);
                        ceoCreated = true;
                        System.out.println("CEO has been created.");
                    } catch (CeoMissingException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    do {
                        System.out.println("--------------------------");
                        System.out.println("1. Clerk");
                        System.out.println("2. Programmer");
                        System.out.println("3. Manager");
                        System.out.println("4. Exit");
                        System.out.println("--------------------------");

                        ch2 = Checkers.choiceCheck(4);

                        switch (ch2) {
                            case 1:
                                System.out.print("Enter Clerk ID: ");
                                int clerkId = sc.nextInt();
                                Emp clerk = new Clerk(clerkId, "Clerk", 25, 20000, "Clerk");
                                empMap.put(clerk.getEmployeeId(), clerk);
                                break;
                            case 2:
                                System.out.print("Enter Programmer ID: ");
                                int programmerId = sc.nextInt();
                                Emp programmer = new Programmer(programmerId, "Programmer", 25, 20000, "Programmer");
                                empMap.put(programmer.getEmployeeId(), programmer);
                                break;
                            case 3:
                                System.out.print("Enter Manager ID: ");
                                int managerId = sc.nextInt();
                                Emp manager = new Manager(managerId, "Manager", 30, 20000, "Manager");
                                empMap.put(manager.getEmployeeId(), manager);
                                break;
                            case 4:
                                break;
                            default:
                                System.out.println("Invalid choice");
                        }
                    } while (ch2 != 4);
                }
            }

            if (ch1 == 2) {
                if (empMap.isEmpty()) {
                    System.out.println("No employee exists to display.");
                } else {
                    displayEmployees(empMap);
                }
            }

            if (ch1 == 3) {
                if (empMap.isEmpty()) {
                    System.out.println("No employee exists to raise salary.");
                } else {
                    raiseSalary(empMap);
                }
            }

            if (ch1 == 4) {
                if (empMap.isEmpty()) {
                    System.out.println("No employee exists to remove.");
                } else {
                    removeEmployee(empMap);
                }
            }

            if (ch1 == 5) {
                searchEmployee(empMap);
            }

        } while (ch1 != 6);

        saveEmployeesToCSV(empMap);

        System.out.println("Total number of employees added: " + empMap.size());
    }

    private static void loadEmployeesFromCSV(HashMap<Integer, Emp> empMap) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "rw")) {
            String line;
            while ((line = raf.readLine()) != null) {
                Emp emp = Emp.fromCSV(line);
                empMap.put(emp.getEmployeeId(), emp);
            }
        } catch (IOException e) {
            System.out.println("Error reading from CSV file: " + e.getMessage());
        }
    }

    private static void saveEmployeesToCSV(HashMap<Integer, Emp> empMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Emp emp : empMap.values()) {
                writer.write(emp.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    private static void displayEmployees(HashMap<Integer, Emp> empMap) {
        System.out.println("Employee Details: ");
        for (Emp emp : empMap.values()) {
            emp.display();
        }
    }

    private static void raiseSalary(HashMap<Integer, Emp> empMap) {
        for (Emp emp : empMap.values()) {
            emp.raiseSalary();
        }
        saveEmployeesToCSV(empMap);
    }

    private static void removeEmployee(HashMap<Integer, Emp> empMap) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter employee ID to remove: ");
        int id = sc.nextInt();
        if (empMap.containsKey(id)) {
            Emp empToRemove = empMap.get(id);
            empToRemove.display();
            System.out.print("Do you really want to remove this employee record (Y/N)? ");
            String s = sc.next();
            if (s.equalsIgnoreCase("Y")) {
                empMap.remove(id);
                saveEmployeesToCSV(empMap);
                System.out.println("Employee removed.");
            }
        } else {
            System.out.println("Employee ID not found.");
        }
    }

    private static void searchEmployee(HashMap<Integer, Emp> empMap) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Search by: ");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("3. Designation");
        int searchChoice = Checkers.choiceCheck(3);
        System.out.print("Enter search value: ");
        String searchValue = sc.next();

        for (Emp emp : empMap.values()) {
            boolean match = false;
            switch (searchChoice) {
                case 1:
                    match = String.valueOf(emp.getEmployeeId()).equals(searchValue);
                    break;
                case 2:
                    match = emp.getName().equalsIgnoreCase(searchValue);
                    break;
                case 3:
                    match = emp.getDgn().equalsIgnoreCase(searchValue);
                    break;
            }
            if (match) {
                emp.display();
            }
        }
    }
}
