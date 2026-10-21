
import java.util.Scanner;

class DivideByZeroException extends Exception {
    public DivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {

    public static double calculate(double a, double b, char op)
            throws DivideByZeroException {

        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new DivideByZeroException(
                            "Cannot divide by zero.");
                }
                return a / b;
            default:
                throw new IllegalArgumentException(
                        "Invalid operator. Use +, -, *, or /.");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean success = false;
        int attempt = 0;

        while (!success) {
            attempt++;

            try {
                System.out.print("Enter first number: ");
                double a = Double.parseDouble(sc.nextLine());

                System.out.print("Enter operator (+, -, *, /): ");
                char op = sc.nextLine().trim().charAt(0);

                System.out.print("Enter second number: ");
                double b = Double.parseDouble(sc.nextLine());

                double result = calculate(a, b, op);

                System.out.println("Result: " + result);
                success = true;

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid number. Please enter numeric values.");

            } catch (DivideByZeroException e) {
                System.out.println(e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Operator cannot be empty.");

            } finally {
                System.out.println("Attempt " + attempt + " logged.");
                System.out.println("--------------------");
            }
        }

        sc.close();
    }
}
