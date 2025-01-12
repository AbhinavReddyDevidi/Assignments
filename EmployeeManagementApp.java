import java.util.*;
abstract class Emp
{
	String name;
	int age;
	int salary;
	String designation;

	static int count;
	
	Emp(int sal, String design)
	{	
		Scanner sc= new Scanner(System.in);
		System.out.print("Enter name : ");
		name = sc.next();
		System.out.print("Enter age : ");
		age = sc.nextInt();
		salary = sal;
		designation = design;
		
		count++;
	}
	public void display()
	{
		System.out.println("Name : "+name);
		System.out.println("Age : "+age);
		System.out.println("Salary : "+salary);
		System.out.println("Designation : "+designation);
	}
	public abstract void raiseSalary();
}
class Clerk extends Emp
{
	Clerk()
	{
		super(20000,"CLERK");
	}
	public void raiseSalary()
	{
		salary +=2000;
	}
}
class Programmer extends Emp
{
	Programmer()
	{
		super(20000,"Programmer");
	}
	public void raiseSalary()
	{
		salary +=5000;
	}
}
class Manager extends Emp
{
	Manager()
	{
		super(20000,"Manager");
	}
	public void raiseSalary()
	{
		salary +=15000;
	}
}
public class EmployeeManagementApp
{
	public static void main(String args[])
	{
		int ch1=0, ch2=0;
		Emp emp[]= new Emp[100];

		do
		{
			System.out.println("--------------------------");
			System.out.println(" 1.Create ");
			System.out.println(" 2.Display");
			System.out.println(" 3.Raise Salary");
			System.out.println(" 4.Remove");
			System.out.println(" 5.Exit");
			System.out.println("--------------------------");
			System.out.print("Enter choice : ");
			ch1=new Scanner(System.in).nextInt();
			if(ch1 == 1)
			{
				do
				{
					System.out.println("--------------------------");
					System.out.println("1.Clerk");
					System.out.println("2.Programmer");
					System.out.println("3.Manager");
					System.out.println("4.Exit");
					System.out.println("--------------------------");
					System.out.print("Enter choice : ");
					ch2=new Scanner(System.in).nextInt();
					switch(ch2)
					{
						case 1: emp[Emp.count]=new Clerk();
							break;
						case 2: emp[Emp.count]=new Programmer();
							break;
						case 3: emp[Emp.count]=new Manager();
							break;
					}
				}while(ch2 !=4);
			}
			if(ch1 == 2)
			{
				if(Emp.count ==0)
					System.out.println("No employee exist to display");
				for(int i=0;i<emp.length;i++)
					emp[i].display();
			}
			if(ch1 == 3)
			{
				if(Emp.count ==0)
					System.out.println("No employee exist to raise salary");
				for(int i=0;i<emp.length;i++)
					emp[i].raiseSalary();
			}
	
		}while (ch1 != 4);
		
	    System.out.println("Total number of employees added :" +Emp.count);
	}
}