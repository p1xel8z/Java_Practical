abstract class Media {

    String title;
    int lateDays;

    Media(String title, int lateDays) {
        this.title = title;
        this.lateDays = lateDays;
    }

    abstract double lateFee();
}

class Book extends Media {

    Book(String title, int lateDays) {
        super(title, lateDays);
    }

    double lateFee() {
        return lateDays * 2;
    }
}

class DVD extends Media {

    DVD(String title, int lateDays) {
        super(title, lateDays);
    }

    double lateFee() {
        return lateDays * 5;
    }
}

class Magazine extends Media {

    Magazine(String title, int lateDays) {
        super(title, lateDays);
    }

    double lateFee() {
        return lateDays * 1;
    }
}

class MediaMain {

    public static void main(String[] args) {

        Media[] returned = new Media[5];

        returned[0] = new Book("Java Programming", 3);
        returned[1] = new DVD("Avengers", 2);
        returned[2] = new Magazine("Tech Monthly", 5);
        returned[3] = new Book("Data Structures", 4);
        returned[4] = new DVD("Inception", 1);

        double total = 0;

        for (int i = 0; i < returned.length; i++) {

            double fee = returned[i].lateFee();

            System.out.println(
                returned[i].title + " late fee: " + fee
            );

            total += fee;
        }

        System.out.println("Total late fees: " + total);
    }
}
