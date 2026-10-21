
class MyResource implements AutoCloseable {

    public MyResource() {
        System.out.println("Resource opened.");
    }

    @Override
    public void close() {
        System.out.println("Resource closed.");
    }

    public void performTask() {
        throw new RuntimeException("Original task error");
    }
}

public class AutoCloseableResource {
    public static void main(String[] args) {

        try (MyResource resource = new MyResource()) {
            resource.performTask();

        } catch (Exception e) {
            System.out.println("Reported error: " + e.getMessage());
        }
    }
}
