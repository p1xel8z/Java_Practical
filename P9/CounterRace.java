
class Counter {
    int count = 0;

    void incrementUnsafe() {
        count++;
    }

    synchronized void incrementSafe() {
        count++;
    }
}

public class CounterRace {
    static final int THREADS = 5;
    static final int ITERATIONS = 100000;

    public static void main(String[] args)
            throws InterruptedException {

        Counter unsafe = new Counter();
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) {
                    unsafe.incrementUnsafe();
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Without synchronization: "
                + unsafe.count);
        System.out.println("Expected total: "
                + (THREADS * ITERATIONS));

        Counter safe = new Counter();

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) {
                    safe.incrementSafe();
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("With synchronization: "
                + safe.count);
    }
}
