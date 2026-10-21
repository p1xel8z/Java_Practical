package model;

import java.io.Serializable;


public class Customer implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private static long customerCounter = 0;

    private Address address;

    public Customer(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerId = generateCustomerId();
    }

    private static String generateCustomerId() {
        return "CUST" + (++customerCounter);
    }

    public String getCustomerId() { return customerId; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public String getMobile()     { return mobile; }

    public void setAddress(Address address) { this.address = address; }
    public Address getAddress()             { return address; }

    public static class Address implements Serializable {

        private static final long serialVersionUID = 1L;

        private String line;
        private String city;
        private String pincode;

        public Address(String line, String city, String pincode) {
            this.line    = line;
            this.city    = city;
            this.pincode = pincode;
        }

        public String getLine()    { return line; }
        public String getCity()    { return city; }
        public String getPincode() { return pincode; }
    }

    @Override
    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}