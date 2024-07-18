package javaproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GroceryShop {

    private static final String URL = "jdbc:mysql://localhost:3306/GroceryShop";
    private static final String USER = "root";
    private static final String PASSWORD = "Logesh2003";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Are you a customer or employee? (c/e): ");
                String userType = scanner.nextLine().toLowerCase();

                if (userType.equals("c")) {
                    // Customer Mode
                    System.out.println("Enter your name: ");
                    String customerName = scanner.nextLine();
                    Customer customer = new Customer(customerName);

                    List<Item> purchasedItems = new ArrayList<>();

                    while (true) {
                        System.out.println("Enter item name (rice, dhall, ghee) or type 'done' to finish: ");
                        String itemName = scanner.nextLine();

                        if (itemName.equalsIgnoreCase("done")) {
                            break;
                        } else {
                            System.out.println("Enter quantity: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            PreparedStatement psItem = connection.prepareStatement("SELECT * FROM items WHERE name = ?");
                            psItem.setString(1, itemName);
                            ResultSet rsItem = psItem.executeQuery();

                            if (rsItem.next()) {
                                int itemId = rsItem.getInt("id");
                                int availableQuantity = rsItem.getInt("quantity");
                                double price = rsItem.getDouble("price");

                                if (quantity <= availableQuantity) {
                                    Item item = new Item(itemName, quantity, price);
                                    purchasedItems.add(item);

                                    PreparedStatement psUpdate = connection.prepareStatement("UPDATE items SET quantity = ? WHERE id = ?");
                                    psUpdate.setInt(1, availableQuantity - quantity);
                                    psUpdate.setInt(2, itemId);
                                    psUpdate.executeUpdate();
                                } else {
                                    System.out.println("Insufficient stock.");
                                }
                            } else {
                                System.out.println("Item not found.");
                            }
                        }
                    }

                    // Generate and print invoice
                    Invoice invoice = new Invoice(customer, purchasedItems);
                    invoice.printInvoice();

                } else if (userType.equals("e")) {
                    // Employee Mode
                    System.out.println("Employee Functions:");
                    System.out.println("1. View Current Stock Levels");
                    
                    System.out.println("Select an option 1: ");
                    int option = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (option) {
                        case 1:
                            // View Current Stock Levels
                            PreparedStatement psStock = connection.prepareStatement("SELECT * FROM items");
                            ResultSet rsStock = psStock.executeQuery();

                            System.out.println("Current Stock Levels:");
                            while (rsStock.next()) {
                                String itemName = rsStock.getString("name");
                                int quantity = rsStock.getInt("quantity");

                                System.out.println("- " + itemName + ": " + quantity);
                            }
                            break;
                       
                    
                        default:
                            System.out.println("Invalid option.");
                            break;
                    }

                } else {
                    System.out.println("Invalid input.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
