import java.util.*;

class Biker extends Thread {
    String name;
    long startTime;
    long endTime;
    long timeTaken;

    // Constructor to initialize biker name
    Biker(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is ready to race.....");

            // Start the race (simulate time taken using sleep)
            startTime = System.currentTimeMillis(); // Record start time

            System.out.print(name + " Racing... ");
            for (int i = 1; i <= RacingDetails.distance; i++) {
                Thread.sleep((int) (Math.random() * 100)); // Simulate racing
                if (i % 100 == 0) {
                    System.out.print(".");
                }
            }

            // End of race
            endTime = System.currentTimeMillis(); // Record end time
            timeTaken = endTime - startTime;
            System.out.println(name + " finished!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name + " | Time Taken: " + timeTaken + " ms";
    }
}

class RacingDetails {
    static int noOfBikers;
    static int distance;
    static String unit = "Meters";
}

public class BikeRacing {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of bikers: ");
        RacingDetails.noOfBikers = scanner.nextInt();
        Biker[] bikers = new Biker[RacingDetails.noOfBikers];
        RacingDetails.distance = 1000;

        // Create bikers
        scanner.nextLine(); // consume the newline character left by nextInt()
        for (int i = 0; i < bikers.length; i++) {
            System.out.print("Enter name for biker " + (i + 1) + ": ");
            String name = scanner.nextLine();
            bikers[i] = new Biker(name);
        }

        // Countdown for race start
        Thread.sleep(2000);
        System.out.println("Countdown Started....");
        for (int i = 10; i > 0; i--) {
            System.out.println(i);
            Thread.sleep(1000);
        }
        System.out.println("GO");

        // Start all bikers
        for (Biker b : bikers) {
            b.start(); // Start all threads at the same time
        }

        // Wait for all bikers to finish
        for (Biker b : bikers) {
            b.join();
        }

        // Sort bikers by time taken (ascending order)
        Arrays.sort(bikers, Comparator.comparingLong(b -> b.timeTaken));

        // Print the rankings
        System.out.println("\nRankings:");
        for (int i = 0; i < bikers.length; i++) {
            System.out.println("Rank " + (i + 1) + ": " + bikers[i]);
        }

        scanner.close();
    }
}
