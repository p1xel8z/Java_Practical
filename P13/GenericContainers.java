
class Box<T> {
    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}

class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}

public class GenericContainers {
    // Returns the first element of an array of any reference type
    public static <T> T firstElement(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }

        return array[0];
    }

    public static void main(String[] args) {
        Box<Integer> intBox = new Box<>();
        intBox.set(100);
        System.out.println("Integer Box: " + intBox.get());

        Box<String> stringBox = new Box<>();
        stringBox.set("Hello Java");
        System.out.println("String Box: " + stringBox.get());

        Pair<String, Integer> student =
                new Pair<>("Rahul", 21);

        System.out.println("\nPair values:");
        System.out.println("Name: " + student.getFirst());
        System.out.println("Age: " + student.getSecond());

        String[] names = {"Aman", "Riya", "Neha"};
        Integer[] numbers = {10, 20, 30};

        System.out.println("\nFirst name: " + firstElement(names));
        System.out.println("First number: " + firstElement(numbers));

         
    }
}
