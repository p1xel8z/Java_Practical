
class Theatre {
    int seatsLeft = 5;
    int successfulBookings = 0;

    void bookUnsafe() {
        if (seatsLeft > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            seatsLeft--;
            successfulBookings++;
            System.out.println("Booking successful (unsafe)");
        } else {
            System.out.println("No seats available (unsafe)");
        }
    }

    synchronized void bookSafe() {
        if (seatsLeft > 0) {
            seatsLeft--;
            successfulBookings++;
            System.out.println("Booking successful (safe)");
        } else {
            System.out.println("No seats available (safe)");
        }
    }
}

public class SeatBooking {
    static void runBookings(Theatre theatre, boolean safe)
            throws InterruptedException {

        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                if (safe) {
                    theatre.bookSafe();
                } else {
                    theatre.bookUnsafe();
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        Theatre unsafe = new Theatre();
        runBookings(unsafe, false);

        System.out.println("\nUnsafe successful bookings: "
                + unsafe.successfulBookings);
        System.out.println("Unsafe seats left: "
                + unsafe.seatsLeft);

        System.out.println("\n--- Synchronized booking ---");

        Theatre safe = new Theatre();
        runBookings(safe, true);

        System.out.println("\nSafe successful bookings: "
                + safe.successfulBookings);
        System.out.println("Safe seats left: "
                + safe.seatsLeft);
    }
}
