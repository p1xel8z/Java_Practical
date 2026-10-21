class CinemaShow {

    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;

    CinemaShow(String _title, int _capacity) {
        title = _title;
        seatsAvailable = _capacity;
        capacity = _capacity;
    }

    CinemaShow(String _title) {
        this(_title, 100);
    }

    boolean book(int n) {
        if (n <= seatsAvailable) {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        } else
            return false;
    }

    void cancel(int n) {
        if (n + seatsAvailable < capacity) {
            seatsAvailable += n;
            totalBooked -= n;
        }
    }

    int getSeatsAvailable() {
        return seatsAvailable;
    }

    static int getTotalBooked() {
        return totalBooked;
    }

    public static void main(String[] args) {
        CinemaShow show1 = new CinemaShow("Odessey", 100);

        System.out.println("Cinema title: " + show1.title);

        show1.book(45);
        System.out.println("Cinema booked: " + CinemaShow.totalBooked);

        show1.cancel(20);
        System.out.println("Cinema booked: " + CinemaShow.totalBooked);

    }
}