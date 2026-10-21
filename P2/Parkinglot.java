import java.util.Scanner;

class Parkinglot {

    private int twoWheelers = 0;
    private int fourWheelers = 0;
    private final int twoCap;
    private final int fourCap;
    private static long revenue = 0;

    Parkinglot(int _twoCap, int _fourCap) {
        twoCap = _twoCap;
        fourCap = _fourCap;
    }

    void park(String type) {
        if (type.equalsIgnoreCase("two")) {
            if (twoWheelers < twoCap) {
                twoWheelers++;
                revenue += 20;
            } else
                System.out.println("Full");
        } else if (type.equalsIgnoreCase("four")) {
            if (fourWheelers < fourCap) {
                fourWheelers++;
                revenue += 40;
            } else
                System.out.println("Full");
        }
    }

    void leave(String type) {
        if (type.equalsIgnoreCase("two")) {
            if (twoWheelers - 1 >= 0) {
                twoWheelers--;

            }

        } else if (type.equalsIgnoreCase("four")) {
            if (fourWheelers - 1 >= 0) {
                fourWheelers--;

            }
        }
    }

    int getTwoWheelers() {
        return twoWheelers;
    }

    int getFourWheelers() {
        return fourWheelers;
    }

    long getRevenue() {
        return revenue;
    }

    public static void main(String[] args) {
        Parkinglot parkingLot = new Parkinglot(10, 20);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter no. of events: ");
        int numEvents = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numEvents; i++) {
            System.out.print("Enter type of event (park/leave): ");
            String event = scanner.nextLine();

            if (event.equalsIgnoreCase("park")) {
                System.out.print("Enter vehicle type (two/four wheelers): ");
                String vehicle = scanner.nextLine();
                if (vehicle.equalsIgnoreCase("two")) {
                    parkingLot.park("two");
                    System.out.println("Two wheelers parked: " + parkingLot.getTwoWheelers());

                } else if (vehicle.equalsIgnoreCase("four")) {
                    parkingLot.park("four");
                    System.out.println("Four wheelers parked: " + parkingLot.getFourWheelers());

                }
            } else {
                System.out.print("Enter vehicle type (two/four wheelers): ");
                String vehicle = scanner.nextLine();
                if (vehicle.equalsIgnoreCase("two")) {
                    parkingLot.leave("two");
                    System.out.println("Two wheelers parked: " + parkingLot.getTwoWheelers());

                } else if (vehicle.equalsIgnoreCase("four")) {
                    parkingLot.leave("four");
                    System.out.println("Four wheelers parked: " + parkingLot.getFourWheelers());

                }

            }
        }

        System.out.println("---Final occupancy---");
        System.out.println("Two wheelers parked: " + parkingLot.getTwoWheelers());
        System.out.println("Four wheelers parked: " + parkingLot.getFourWheelers());
        System.out.println("Revenue: " + parkingLot.getRevenue());

        scanner.close();
    }
}