class A {
    public void print1to10() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Range : " + i);
        }
    }
}

class B {
    public void evenTill50() {
        for (int i = 0; i <= 50; i += 2) {
            System.out.println("Even : " + i);
        }
    }
}

class C {
    public void fibonacciFrom1to1000() {
        int a = 0, b = 1;
        System.out.println("Fibonacci : " + a);
        while (b <= 1000) {
            System.out.println("Fibonacci : " + b);
            int temp = b;
            b = a + b;
            a = temp;
        }
    }
}

public class Multithreading {
    public static void main(String args[]) {
        A a = new A();
        B b = new B();
        C c = new C();

        // Create thread for A using lambda
        Thread threadA = new Thread(() -> a.print1to10());

        // Create thread for B using lambda
        Thread threadB = new Thread(() -> b.evenTill50());

        // Create thread for C using lambda
        Thread threadC = new Thread(() -> c.fibonacciFrom1to1000());

        // Start threads
        threadA.start();
        threadB.start();
        threadC.start();

        try {
            // Wait for all threads to finish before main thread ends
            threadA.join();
            threadB.join();
            threadC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
