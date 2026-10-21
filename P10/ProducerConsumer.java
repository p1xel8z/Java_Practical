
class Buffer {
    private Integer item = null;
    private boolean finished = false;

    public synchronized void produce(int value)
            throws InterruptedException {

        while (item != null) {
            wait();
        }

        item = value;
        System.out.println("Produced: " + value);
        notifyAll();
    }

    public synchronized Integer consume()
            throws InterruptedException {

        while (item == null && !finished) {
            wait();
        }

        if (item == null && finished) {
            return null;
        }

        int value = item;
        item = null;

        System.out.println("Consumed: " + value);
        notifyAll();

        return value;
    }

    public synchronized void finish()
            throws InterruptedException {

        while (item != null) {
            wait();
        }

        finished = true;
        notifyAll();
    }
}

public class ProducerConsumer {

    public static void main(String[] args)
            throws InterruptedException {

        Buffer buffer = new Buffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    buffer.produce(i);
                }

                buffer.finish();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                Integer value;

                while ((value = buffer.consume()) != null) {
                    // The buffer prints each consumed value.
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Production and consumption completed.");
    }
}
