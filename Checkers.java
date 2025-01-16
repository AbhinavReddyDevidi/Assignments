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

abstract class Emp {
    private String name;
    private int age;
    private int employeeid;
    protected int salary;
    private String designation;

    static int count = 0;

    Emp(int sal, String design) {
        Scanner sc = new Scanner(System.in);
        name = Checkers.NameCheck();
        age = Checkers.AgeCheck(21, 60);
        salary = sal;
        designation = design;
        employeeid = ++count;
    }
    public int getEmployeeid() {
        return employeeid;
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
        System.out.println("Employee ID: " + employeeid);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Salary: " + salary);
        System.out.println("Designation: " + designation);
    }

    public abstract void raiseSalary();
}

class Clerk extends Emp {
    Clerk() {
        super(20000, "CLERK");
    }

    public void raiseSalary() {
        salary += 2000;
    }
}

class Programmer extends Emp {
    Programmer() {
        super(20000, "Programmer");
    }

    public void raiseSalary() {
        salary += 5000;
    }
}

class Manager extends Emp {
    Manager() {
        super(20000, "Manager");
    }

    @Override
    public void raiseSalary() {
        salary += 15000;
    }
}

// Checkers class
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
            } 
            catch (InvalidNameException e) {
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

public class EmployeeManagementApp2 {
    public static void main(String args[]) {
        int ch1 = 0, ch2 = 0;
        Emp[] emp = new Emp[100];
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("--------------------------");
            System.out.println("1. Create");
            System.out.println("2. Display");
            System.out.println("3. Raise Salary");
            System.out.println("4. Remove");
            System.out.println("5. Exit");
            System.out.println("--------------------------");

            ch1 = Checkers.choiceCheck(5);

            if (ch1 == 1) {
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
                            emp[Emp.count] = new Clerk();
                            break;
                        case 2:
                            emp[Emp.count] = new Programmer();
                            break;
                        case 3:
                            emp[Emp.count] = new Manager();
                            break;
                        case 4:
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                } while (ch2 != 4);
            }

            if (ch1 == 2) {
                if (Emp.count == 0)
                    System.out.println("No employee exists to display.");
                else {
                    for (int i = 0; i < Emp.count; i++) {
                        emp[i].display();
                    }
                }
            }

            if (ch1 == 3) {
                if (Emp.count == 0)
                    System.out.println("No employee exists to raise salary.");
                else {
                    for (int i = 0; i < Emp.count; i++) {
                        emp[i].raiseSalary();
                    }
                }
            }

            if (ch1 == 4) {
                if (Emp.count == 0)
                    System.out.println("No employee exists to remove.");
                else {
                    System.out.print("Enter employee ID to remove: ");
                    int id = sc.nextInt();
                    boolean found = false;
                    for (int i = 0; i < Emp.count; i++) {
                        if (emp[i].getEmployeeid() == id) {
                            emp[i].display();
                            System.out.print("Do you really want to remove this employee record (Y/N)? ");
                            String s = sc.next();
                            if (s.equalsIgnoreCase("Y")) {
                                emp[i] = emp[--Emp.count];
                                emp[Emp.count] = null;
                            }
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Employee ID not found.");
                    }
                }
            }

        } while (ch1 != 5);

        System.out.println("Total number of employees added: " + Emp.count);
    }
}
