
import java.util.Arrays;
import java.util.EmptyStackException;

class GenericStack<T> {
    private Object[] elements;
    private int size;

    public GenericStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Capacity must be positive");
        }

        elements = new Object[capacity];
        size = 0;
    }

    public void push(T value) {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }

        elements[size++] = value;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        int index = --size;
        T value = (T) elements[index];
        elements[index] = null;
        return value;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        return (T) elements[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}

public class GenericStackDemo {
    public static void main(String[] args) {
        GenericStack<Integer> numbers = new GenericStack<>(2);

        numbers.push(10);
        numbers.push(20);
        numbers.push(30); // Automatically expands the array

        System.out.println("Integer stack top: " + numbers.peek());
        System.out.println("Popped: " + numbers.pop());
        System.out.println("Popped: " + numbers.pop());
        System.out.println("Remaining size: " + numbers.size());

        GenericStack<String> names = new GenericStack<>(2);

        names.push("Aman");
        names.push("Riya");

        System.out.println("\nString stack top: " + names.peek());
        System.out.println("Popped: " + names.pop());
        System.out.println("Popped: " + names.pop());

        try {
            names.pop();
        } catch (EmptyStackException e) {
            System.out.println("Cannot pop: stack is empty.");
        }
    }
}
