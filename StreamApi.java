import java.util.*;
import java.util.stream.Collectors;

class Student {
    private int rollNo;
    private String name;
    private int age;
    private String standard;
    private String school;
    private String gender;
    private double percentage;
    private Fees fees;
    
    public Student(int rollNo, String name, int age, String standard, String school, String gender, double percentage, Fees fees) {
        this.rollNo = rollNo;
        this.name = name;
        this.age = age;
        this.standard = standard;
        this.school = school;
        this.gender = gender;
        this.percentage = percentage;
        this.fees = fees;
    }

    public String getSchool() {
        return school;
    }

    public String getGender() {
        return gender;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getStandard() {
        return standard;
    }

    public Fees getFees() {
        return fees;
    }

    public int getAge() {
        return age;
    }

    public double totalFees() {
        return fees.getTotalFees();
    }
    
    public double feesPaid() {
        return fees.getFeesPaid();
    }

    
    public double feesPending() {
        return fees.getFeesPending();
    }

    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", standard='" + standard + '\'' +
                ", school='" + school + '\'' +
                ", gender='" + gender + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}

class Fees {
    private double totalFees;
    private double feesPaid;
    private double feesPending;

    public Fees(double totalFees, double feesPaid) {
        this.totalFees = totalFees;
        this.feesPaid = feesPaid;
        this.feesPending = totalFees - feesPaid;
    }

    public double getTotalFees() {
        return totalFees;
    }

    public double getFeesPaid() {
        return feesPaid;
    }

    public double getFeesPending() {
        return feesPending;
    }
}

public class Main {
    public static void main(String[] args) {
        
        List<Student> students = new ArrayList<>();

        Fees fee1 = new Fees(1000, 800);
        Fees fee2 = new Fees(1200, 600);
        Fees fee3 = new Fees(1500, 1500);
        Fees fee4 = new Fees(1100, 500);
        Fees fee5 = new Fees(1300, 1300);

        students.add(new Student(1, "Nikhil", 20, "12th", "DPS School", "Male", 75.0, fee1));
        students.add(new Student(2, "Shreya", 19, "11th", "KV School", "Female", 50.0, fee2));
        students.add(new Student(3, "Rishwith", 18, "10th", "DPS School", "Male", 40.0, fee3));
        students.add(new Student(4, "Sreeja", 23, "12th", "KV School", "Female", 85.0, fee4));
        students.add(new Student(5, "Sathvik", 21, "11th", "DPS School", "Male", 35.0, fee5));

        // 1. Students in each standard
        System.out.println("Students in each standard:");
        students.stream().collect(Collectors.groupingBy(student -> student.getStandard(), Collectors.counting()))
                .forEach((standard, count) -> System.out.println(standard + ": " + count));

        System.out.println("------------------------------------------------------");

        // 2. Students male & female
        System.out.println("Students male & female:");
        students.stream()
                .collect(Collectors.groupingBy(student -> student.getGender(), Collectors.counting()))
                .forEach((gender, count) -> System.out.println(gender + ": " + count));

        // 3. How many students passed and failed (40%)
        System.out.println("------------------------------------------------------");

        // 3.1 University-wise pass and fail
        System.out.println("\nUniversity-wide pass and fail:");
        students.stream()
                .collect(Collectors.partitioningBy(student -> student.getPercentage() >= 40))
                .forEach((passed, studentList) -> {
                    String result = passed ? "Passed" : "Failed";
                    System.out.println(result + ": " + studentList.size());
                });

        // 3.2 School-wise pass and fail
        System.out.println("------------------------------------------------------");
        System.out.println("\nSchool-wise pass and fail:");
        students.stream()
                .collect(Collectors.groupingBy(student -> student.getSchool(),
                        Collectors.partitioningBy(student -> student.getPercentage() >= 40)))
                .forEach((school, passFailMap) -> {
                    passFailMap.forEach((passed, studentList) -> {
                        String result = passed ? "Passed" : "Failed";
                        System.out.println(school + " - " + result + ": " + studentList.size());
                    });
                });

        // 4. Top 3 students (Whole university)
        System.out.println("------------------------------------------------------");
        System.out.println("\nTop 3 students:");
        students.stream()
                .sorted((student1, student2) -> Double.compare(student2.getPercentage(), student1.getPercentage()))
                .limit(3)
                .forEach(student -> System.out.println(student));

        // 5. Top scorer school wise
        System.out.println("------------------------------------------------------");
        System.out.println("\nTop scorer school wise:");
        students.stream()
                .collect(Collectors.groupingBy(student -> student.getSchool(),
                        Collectors.collectingAndThen(
                                Collectors.maxBy((student1, student2) -> Double.compare(student1.getPercentage(), student2.getPercentage())),
                                Optional::get)))
                .forEach((school, topStudent) -> System.out.println(school + ": " + topStudent));

        // 6. Average age of male & female students
        System.out.println("------------------------------------------------------");
        System.out.println("\nAverage age of male & female students:");
        students.stream()
                .collect(Collectors.groupingBy(student -> student.getGender(), Collectors.averagingInt(student -> student.getAge())))
                .forEach((gender, avgAge) -> System.out.println(gender + ": " + avgAge));

        // 7. Total fees collected school wise
        System.out.println("------------------------------------------------------");
        System.out.println("\nTotal fees collected school wise:");
        students.stream()
                .collect(Collectors.groupingBy(student -> student.getSchool(),
                        Collectors.summingDouble(student -> student.feesPaid())))
                .forEach((school, totalFeesPaid) -> System.out.println(school + ": " + totalFeesPaid));

        // 8. Total fees pending school wise
        System.out.println("------------------------------------------------------");
        System.out.println("\nTotal fees pending school wise:");
        students.stream()
                .collect(Collectors.groupingBy(student -> student.getSchool(),
                        Collectors.summingDouble(student -> student.feesPending())))
                .forEach((school, totalFeesPending) -> System.out.println(school + ": " + totalFeesPending));

        // 9. Total number of students (University)
        System.out.println("------------------------------------------------------");
        System.out.println("\nTotal number of students in the university: " + students.size());
    }
}
