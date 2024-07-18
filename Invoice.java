package javaproject;

import java.util.List;

public class Invoice {
    private final Customer customer;
    private final List<Item> items;

    public Invoice(Customer customer, List<Item> items) {
        this.customer = customer;
        this.items = items;
    }

    public void printInvoice() {
        double totalAmount = 0;

        System.out.println("Invoice:");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Items Purchased:");

        for (Item item : items) {
            double itemTotal = item.getPrice() * item.getQuantity();
            totalAmount += itemTotal;

            System.out.println("- " + item.getName() + " x " + item.getQuantity() + " : Rs-" + itemTotal);
        }

        System.out.println("Total Amount to Pay: Rs-" + totalAmount);
    }
}
