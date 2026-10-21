@FunctionalInterface
interface Notifier {
    void send(String message);
}

interface Urgent {
}

@FunctionalInterface
interface UrgentNotifier extends Notifier, Urgent {
}

class Notification {

    public static void main(String[] args) {

        UrgentNotifier emailNotifier = (msg) -> {
            System.out.println("Email sent: " + msg);
        };

       
        Notifier smsNotifier = (msg) -> {
            System.out.println("SMS sent: " + msg);
        };

        Notifier[] notifiers = new Notifier[2];

        notifiers[0] = emailNotifier;
        notifiers[1] = smsNotifier;

        String message = "Hello Everyone";

        for (Notifier n : notifiers) {

            n.send(message);

            if (n instanceof Urgent) {
                n.send(message);
            }
        }
    }
}

