import java.util.*;


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

abstract class Emp {
    String name;
    int age;
    int employeeid;
    int salary;
    String designation;

    static int count = 0;
    
    Emp(int sal, String design) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name: ");
        name = sc.next();
        
        while (true) {
            try {
                System.out.print("Enter age: ");
                age = sc.nextInt();
                if (age >= 21 && age <= 60) {
                    break;
                } else {
                    throw new InvalidAgeException("Age should be between 21 and 60. Please enter again.");
                }
            }
            catch(InvalidAgeException e){
                System.out.println(e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Enter Number  Only.");
                sc.next();
            }
        }
        
        salary = sal;
        designation = design;
        employeeid = ++count;
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

    @Override
    public void raiseSalary() {
        salary += 2000;
    }
}

class Programmer extends Emp {
    Programmer() {
        super(20000, "Programmer");
    }

    @Override
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

            while (true) {
                try {
                    System.out.print("Enter choice: ");
                    ch1 = sc.nextInt();
                    if (ch1 < 1 || ch1 > 5) {
                        throw new InvalidChoiceException("Enter a number between 1 and 5.");
                    } else {
                        break;
                    }
                } 
                catch(InvalidChoiceException e){
                    System.out.println(e.getMessage());
                }
                catch (Exception e) {
                    System.out.println("Enter Number only.");
                    sc.next();
                }
            }

            if (ch1 == 1) {
                do {
                    System.out.println("--------------------------");
                    System.out.println("1. Clerk");
                    System.out.println("2. Programmer");
                    System.out.println("3. Manager");
                    System.out.println("4. Exit");
                    System.out.println("--------------------------");
                    
                    while (true) {
                        try {
                            System.out.print("Enter choice: ");
                            ch2 = sc.nextInt();
                            if (ch2 < 1 || ch2 > 4) {
                                throw new InvalidChoiceException("Enter a number between 1 and 4.");
                            } else {
                                break;
                            }
                        }
                        catch(InvalidChoiceException e){
                            System.out.println(e.getMessage());
                        }
                        catch (Exception e) {
                            System.out.println("Enter number only.");
                            sc.next(); 
                        }
                    }

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
                        if (emp[i].employeeid == id) {
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
