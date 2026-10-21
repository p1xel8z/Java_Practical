

class Thermostat {

    private String location;
    private int temperature;
    private static final int MIN = 16, MAX = 30;
    private static int activeCount = 0;

    Thermostat(String _location,int _temperature)
    {
        location = _location;
        if(_temperature>MIN && _temperature<MAX)
            temperature = _temperature;
        else
            temperature = 22;

        activeCount++;
    }

    Thermostat(String _location)
    {
        this(_location,22);
    }

     public  void raise()
       {
          if(temperature < MAX)
            temperature += 1;
        else
            System.out.println("Already at maximum(30)");
       }

        void lower()
       {
          if(temperature > MIN)
            temperature -= 1;
        else
            System.out.println("Already at minimum(16)");
       }

       int getTemperature() {return temperature;}
       
       static int getActiveCount() {return activeCount;}

    public static void main(String [] args)
    {
        Thermostat thermostat1 = new Thermostat("Living Room",25);
        Thermostat thermostat2 = new Thermostat("Bedroom");

        for(int i = 0;i<10;i++)
        {
            thermostat1.raise();
            thermostat2.raise();
            System.out.println("Thermostat 1 Temperature: " + thermostat1.getTemperature() );
            System.out.println("Thermostat 2 Temperature: " + thermostat2.getTemperature() );
            
            thermostat1.lower();
            thermostat2.lower();
            System.out.println("Thermostat 1 Temperature: " + thermostat1.getTemperature() );
            System.out.println("Thermostat 2 Temperature: " + thermostat2.getTemperature() );
            
            System.out.println("Active Count : " + Thermostat.getActiveCount() );
            
        }
        


    }
};