interface Switchable {
    
    void on();
    void off();
    boolean isOn();
    default void toggle()
    {
        if(isOn()){
            off();
        }
        else
        {
            on();
        }

    }
}

class Light implements Switchable {

    boolean state = false;

    public void on() {
        state = true;
        System.out.println("Light is switched on");
    }

    public void off() {
        state = false;
        System.out.println("Light is switched off");
    }

    public boolean isOn() {
        return state;
    }
}

class Fan implements Switchable {

    boolean state = false;

    public void on() {
        state = true;
        System.out.println("Fan is switched on");
    }

    public void off() {
        state = false;
        System.out.println("Fan is switched off");
    }

    public boolean isOn() {
        return state;
    }
}


@FunctionalInterface
interface SwitchPolicy {
    boolean maySwitchOn(Switchable device, int hour);
}

class RemoteMain {

    public static void main(String[] args) {

        Switchable[] devices = new Switchable[2];

        devices[0] = new Light();
        devices[1] = new Fan();

        for (Switchable device : devices) {
            device.toggle();
        }

        SwitchPolicy policy1 = new SwitchPolicy() {
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 8 && hour <= 22;
            }
        };

        SwitchPolicy policy2 = (device, hour) -> hour >= 8 && hour <= 22;

        System.out.println("Anonymous class: "
                + policy1.maySwitchOn(devices[0], 10));

        System.out.println("Lambda: "
                + policy2.maySwitchOn(devices[1], 23));
    }
}


