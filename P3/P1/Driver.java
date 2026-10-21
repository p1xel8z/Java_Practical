class Driver {

    public static void main(String[] args) {
        Point points[] = new Point[5];
        points[0] = new Point(0, 1);
        points[1] = new Point(1, 0);
        points[2] = new Point(2, 3);
        points[3] = new Point(0, 1);
        points[4] = new Point(5, 4);

        int counter = 0;
        for (int i = 0; i < points.length; i++) {

            boolean appeared = false;

            for (int j = 0; j < i; j++) {
                if (points[i].equals(points[j])) {
                    appeared = true;
                    break;
                }
            }

            if (!appeared) {
                counter++;
            }
        }

        System.out.println("Distinct Points: " + counter);
    }
}