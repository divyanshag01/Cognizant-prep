import java.util.HashMap;

public class InventoryManagement {

    static HashMap<Integer, Product> inventory =
            new HashMap<>();


    // Add Product

    static void addProduct(Product product) {

        inventory.put(product.productId, product);

        System.out.println("Product added successfully.");
    }


    // Update Product

    static void updateProduct(int productId,
                              int newQuantity,
                              double newPrice) {

        if (inventory.containsKey(productId)) {

            Product selectedProduct =
                    inventory.get(productId);

            selectedProduct.quantity = newQuantity;

            selectedProduct.price = newPrice;

            System.out.println("Product updated successfully.");
        }

        else {

            System.out.println("Product not found.");
        }
    }


    // Delete Product

    static void deleteProduct(int productId) {

        if (inventory.containsKey(productId)) {

            inventory.remove(productId);

            System.out.println("Product deleted successfully.");
        }

        else {

            System.out.println("Product not found.");
        }
    }


    public static void main(String[] args) {


        Product keyboard =
                new Product(
                        101,
                        "Keyboard",
                        50,
                        999
                );


        Product mouse =
                new Product(
                        102,
                        "Mouse",
                        80,
                        499
                );


        addProduct(keyboard);

        addProduct(mouse);


        updateProduct(
                101,
                60,
                1099
        );


        deleteProduct(102);

    }
}
