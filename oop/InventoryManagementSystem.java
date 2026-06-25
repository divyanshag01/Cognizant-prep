package Week1.DSA;

import java.util.HashMap;
import java.util.Map;

class Product {
    private String productId;
    private String productName;
    private int quantity;
    private double price;

    public Product(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=$" + price +
                '}';
    }
}

public class InventoryManagementSystem {
    private Map<String, Product> inventory;

    public InventoryManagementSystem() {
        this.inventory = new HashMap<>();
    }

    // Add Product
    public void addProduct(Product product) {
        if (inventory.containsKey(product.getProductId())) {
            System.out.println("Product with ID " + product.getProductId() + " already exists. Use update instead.");
        } else {
            inventory.put(product.getProductId(), product);
            System.out.println("Added: " + product);
        }
    }

    // Update Product
    public void updateProduct(String productId, String newName, Integer newQuantity, Double newPrice) {
        Product product = inventory.get(productId);
        if (product != null) {
            if (newName != null) product.setProductName(newName);
            if (newQuantity != null) product.setQuantity(newQuantity);
            if (newPrice != null) product.setPrice(newPrice);
            System.out.println("Updated Product: " + product);
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    // Delete Product
    public void deleteProduct(String productId) {
        Product removed = inventory.remove(productId);
        if (removed != null) {
            System.out.println("Deleted Product: " + removed);
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    // Get Product
    public Product getProduct(String productId) {
        return inventory.get(productId);
    }

    // List all
    public void displayAll() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("--- Current Inventory ---");
            for (Product p : inventory.values()) {
                System.out.println(p);
            }
            System.out.println("-------------------------");
        }
    }

    public static void main(String[] args) {
        InventoryManagementSystem ims = new InventoryManagementSystem();

        System.out.println("=== Inventory Management System Demo ===");
        
        // 1. Add Products
        Product p1 = new Product("P001", "Laptop", 10, 999.99);
        Product p2 = new Product("P002", "Smartphone", 50, 499.99);
        Product p3 = new Product("P003", "Wireless Mouse", 100, 25.50);
        
        ims.addProduct(p1);
        ims.addProduct(p2);
        ims.addProduct(p3);
        ims.displayAll();

        // 2. Update Product
        System.out.println("\nUpdating Smartphone quantity and price...");
        ims.updateProduct("P002", null, 45, 479.99);
        ims.displayAll();

        // 3. Retrieve Product
        System.out.println("\nRetrieving Product P001...");
        Product retrieved = ims.getProduct("P001");
        System.out.println("Retrieved: " + retrieved);

        // 4. Delete Product
        System.out.println("\nDeleting Product P003...");
        ims.deleteProduct("P003");
        ims.displayAll();

        // 5. Analysis
        System.out.println("\n=== Complexity Analysis ===");
        System.out.println("Data Structure Used: HashMap (stores Product objects mapped by Product ID)");
        System.out.println("1. Add Operation:");
        System.out.println("   - Time Complexity: O(1) average. HashMap uses hash function to locate the bucket immediately.");
        System.out.println("   - Space Complexity: O(1) auxiliary space.");
        System.out.println("2. Update Operation:");
        System.out.println("   - Time Complexity: O(1) average. Key lookup in HashMap is constant time.");
        System.out.println("   - Space Complexity: O(1) auxiliary space.");
        System.out.println("3. Delete Operation:");
        System.out.println("   - Time Complexity: O(1) average. Removing a entry from HashMap is constant time.");
        System.out.println("   - Space Complexity: O(1) auxiliary space.");
        System.out.println("4. Retrieve/Get Operation:");
        System.out.println("   - Time Complexity: O(1) average. Retrieving entry from HashMap by key is constant time.");
        System.out.println("   - Space Complexity: O(1) auxiliary space.");
        System.out.println("Total Space Complexity: O(N) where N is the number of products stored in the inventory.");
    }
}
