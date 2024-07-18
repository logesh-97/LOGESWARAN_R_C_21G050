package javaproject;

public class Item {
    private final String name;
    private int quantity;
    private final double price;

    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void reduceQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
