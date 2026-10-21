abstract class Shape {
    public String type;

    abstract double area();
}

class Circle extends Shape {
    double radius;

    Circle(double radius) {
        type = "Circle";
        this.radius = radius;
    }

    public double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length;
    double breadth;

    Rectangle(double length, double breadth) {
        type = "Rectangle";
        this.length = length;
        this.breadth = breadth;
    }

    public double area() {
        return length * breadth;
    }
}

class Triangle extends Shape {
    double length;
    double breadth;

    Triangle(double length, double breadth) {
        type = "Triangle";
        this.length = length;
        this.breadth = breadth;
    }

    public double area() {
        return 0.5 * length * breadth;
    }

}

class Driver {

    public static void main(String[] args) {

        Shape[] shapes = new Shape[5];
        shapes[0] = new Circle(5);
        shapes[1] = new Rectangle(5, 4);
        shapes[2] = new Triangle(5, 4);
        shapes[3] = new Rectangle(5, 10);
        shapes[4] = new Circle(4);

        double max = 0;
        double total = 0;

        for (int i = 0; i < shapes.length; i++) {
            double area = shapes[i].area();
            if (max < area) {
                max = area;
            }

            total += area;

            System.out.printf("%s area: %.2f%n", shapes[i].type, area);
            System.out.printf("Running total: %.2f%n%n", total);
        }

        System.out.printf("Largest Area: %.2f%n", max);
        System.out.printf("Total Area: %.2f%n", total);

    }
}
