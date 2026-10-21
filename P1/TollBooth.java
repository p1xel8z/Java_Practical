import java.util.Scanner;

record Vehicle(String number, String type) {
}

class TollBooth {
    public static void main(String[] args) {

        int toll_total = 0, bike_counter = 0, car_counter = 0, truck_counter = 0;
        String number, type;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter no. of vehicle: ");
            number = scanner.nextLine();

            if (number.equalsIgnoreCase("done"))
                break;

            System.out.print("Enter type of vehicle: ");
            type = scanner.nextLine();

            Vehicle vehicle = new Vehicle(number, type);

            int toll = switch (vehicle.type()) {
                case "bike" -> {
                    bike_counter++;
                    yield 20;
                }
                case "car" -> {
                    car_counter++;
                    yield 50;
                }
                case "truck" -> {
                    truck_counter++;
                    yield 150;
                }
                default -> 0;
            };

            toll_total += toll;

        }

        String freuqent;
        if (bike_counter > truck_counter && bike_counter > car_counter) {
            freuqent = "bike";
        } else if (truck_counter > bike_counter && truck_counter > car_counter) {
            freuqent = "truck";
        } else {
            freuqent = "car";
        }

        System.out.println("Total toll: " + toll_total);
        System.out.println("Most frequet: " + freuqent);
        scanner.close();

    }
}