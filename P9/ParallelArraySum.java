
import java.util.concurrent.atomic.AtomicLong;

public class ParallelArraySum {

    static long sharedTotal = 0;
    static final AtomicLong atomicTotal = new AtomicLong(0);

    static void addUnsafe(long value) {
        sharedTotal += value;
    }

    static synchronized void addSafe(long value) {
        sharedTotal += value;
    }

    public static void main(String[] args)
            throws InterruptedException {

        int size = 1_000_000;
        int threadCount = 4;
        long[] numbers = new long[size];

        for (int i = 0; i < size; i++) {
            numbers[i] = 1;
        }

        long expected = size;
        Thread[] threads = new Thread[threadCount];

        // Method 1: Unsafe shared total
        sharedTotal = 0;
        long start = System.nanoTime();

        for (int t = 0; t < threadCount; t++) {
            final int from = t * size / threadCount;
            final int to = (t + 1) * size / threadCount;

            threads[t] = new Thread(() -> {
                for (int i = from; i < to; i++) {
                    addUnsafe(numbers[i]);
                }
            });
            threads[t].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long unsafeTime = System.nanoTime() - start;
        System.out.println("Unsafe total: " + sharedTotal);
        System.out.println("Expected total: " + expected);
        System.out.println("Unsafe time: "
                + unsafeTime / 1_000_000.0 + " ms");

        // Method 2: Synchronized shared total
        sharedTotal = 0;
        start = System.nanoTime();

        for (int t = 0; t < threadCount; t++) {
            final int from = t * size / threadCount;
            final int to = (t + 1) * size / threadCount;

            threads[t] = new Thread(() -> {
                for (int i = from; i < to; i++) {
                    addSafe(numbers[i]);
                }
            });
            threads[t].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long syncTime = System.nanoTime() - start;
        System.out.println("\nSynchronized total: " + sharedTotal);
        System.out.println("Synchronized time: "
                + syncTime / 1_000_000.0 + " ms");

        // Method 3: AtomicLong
        atomicTotal.set(0);
        start = System.nanoTime();

        for (int t = 0; t < threadCount; t++) {
            final int from = t * size / threadCount;
            final int to = (t + 1) * size / threadCount;

            threads[t] = new Thread(() -> {
                for (int i = from; i < to; i++) {
                    atomicTotal.addAndGet(numbers[i]);
                }
            });
            threads[t].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long atomicTime = System.nanoTime() - start;
        System.out.println("\nAtomic total: " + atomicTotal.get());
        System.out.println("Atomic time: "
                + atomicTime / 1_000_000.0 + " ms");
    }
}
