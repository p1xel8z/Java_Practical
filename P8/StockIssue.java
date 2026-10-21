
import java.util.*;

class OutOfStockException extends Exception {
    private int shortfall;

    public OutOfStockException(int shortfall) {
        super("Insufficient stock. Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    private Map<String, Integer> stock = new HashMap<>();

    public Warehouse() {
        stock.put("Pen", 10);
        stock.put("Book", 5);
        stock.put("Pencil", 20);
    }

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                    "Quantity must be greater than zero.");
        }

        int available = stock.getOrDefault(item, 0);

        if (qty > available) {
            throw new OutOfStockException(qty - available);
        }

        stock.put(item, available - qty);
        System.out.println("Issued " + qty + " " + item);
    }
}

public class StockIssue {
    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse();

        String[] items = {"Pen", "Book", "Pencil", "Pen", "Book"};
        int[] quantities = {3, 8, 0, 4, 2};

        for (int i = 0; i < items.length; i++) {
            System.out.println("\nRequest: " + items[i]
                    + ", Quantity: " + quantities[i]);

            try {
                warehouse.issue(items[i], quantities[i]);

            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());

            } catch (InvalidQuantityException e) {
                System.out.println("Invalid quantity: " + e.getMessage());
            }
        }

        System.out.println("\nAll requests processed.");
    }
}
