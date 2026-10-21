
class Customer {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private static long customerCounter = 0;

    Customer (String name,String email,String mobile)
    {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        customerId = generateCustomerId();
    }

    private String generateCustomerId()
    {
        return "CUST" + String.valueOf(++customerCounter);

    }

    public String getCustomerId() {return customerId;}
    public String getName() {return name;}
    public String getEmail() { return email;}
    public String getMobile() {return mobile;}



}