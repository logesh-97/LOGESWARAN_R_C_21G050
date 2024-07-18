package javaproject;


public class Customer {
    private static int nextId = 1;
    private final int customerId;
    private final String customerName;

    public Customer(String customerName) {
        this.customerId = nextId++;
        this.customerName = customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    // Implement getName method to return customerName
    public String getName() {
        return customerName;
    }
}

