
class Account {
    String name;

    Account(String name) {
        this.name = name;
    }
}

public class DeadlockDemo {

    static final Object lockA = new Object();
    static final Object lockB = new Object();

    static void unsafeTransfer(boolean reverse) {
        Object first = reverse ? lockB : lockA;
        Object second = reverse ? lockA : lockB;

        synchronized (first) {
            System.out.println(Thread.currentThread().getName()
                    + " locked first account");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            synchronized (second) {
                System.out.println(Thread.currentThread().getName()
                        + " completed transfer");
            }
        }
    }

    static void safeTransfer() {
        synchronized (lockA) {
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName()
                        + " completed safe transfer");
            }
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        System.out.println("=== Unsafe: possible deadlock ===");

        Thread t1 = new Thread(
                () -> unsafeTransfer(false), "Thread-1");

        Thread t2 = new Thread(
                () -> unsafeTransfer(true), "Thread-2");

        t1.start();
        t2.start();

        t1.join(1000);
        t2.join(1000);

        if (t1.isAlive() || t2.isAlive()) {
            System.out.println("Deadlock detected: threads are blocked.");
        } else {
            System.out.println("No deadlock occurred this run.");
        }

        System.out.println("\n=== Safe: consistent lock order ===");

        Thread t3 = new Thread(
                DeadlockDemo::safeTransfer, "Thread-3");

        Thread t4 = new Thread(
                DeadlockDemo::safeTransfer, "Thread-4");

        t3.start();
        t4.start();

        t3.join();
        t4.join();

        System.out.println("Safe transfers completed.");
    }
}
