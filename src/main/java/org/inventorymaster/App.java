package org.inventorymaster;

import org.inventorymaster.model.Product;
import org.inventorymaster.services.ProductService;
import org.inventorymaster.services.ProductServiceImpl;

import java.util.Scanner;

public class App {

    private static final String MENU =
            "\n╔══════════════════════════════════════════════╗" +
            "\n║         InventoryMaster - Main Menu         ║" +
            "\n╠══════════════════════════════════════════════╣" +
            "\n║  1. Add Product                              ║" +
            "\n║  2. Update Product                           ║" +
            "\n║  3. Delete Product                           ║" +
            "\n║  4. Find Product by ID                       ║" +
            "\n║  5. Display All Products                     ║" +
            "\n║  6. Exit                                     ║" +
            "\n╚══════════════════════════════════════════════╝" +
            "\nEnter your choice: ";

    public static void main(String[] args) {
        ProductService service = new ProductServiceImpl();
        Scanner scan = new Scanner(System.in);
        int ch;

        do {
            System.out.print(MENU);
            ch = readInt(scan);
            scan.nextLine();

            switch (ch) {
                case 1 -> addProduct(service, scan);
                case 2 -> updateProduct(service, scan);
                case 3 -> deleteProduct(service, scan);
                case 4 -> findProduct(service, scan);
                case 5 -> displayAll(service);
                case 6 -> {
                    service.closeConnection();
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid choice! Please enter 1-6.");
            }
        } while (ch != 6);
    }

    // ── Menu Actions ──────────────────────────────────────────────────────────

    private static void addProduct(ProductService service, Scanner scan) {
        System.out.println("\n── Add Product ──");
        System.out.print("Enter Product ID   : ");
        int id = readInt(scan);
        scan.nextLine();

        if (service.findById(id) != null) {
            System.out.println("A product with ID " + id + " already exists.");
            return;
        }

        Product product = new Product();
        product.setId(id);
        product.setName(readNonEmpty(scan, "Enter Product Name  : "));
        System.out.print("Enter Product Price : ");
        product.setPrice(readInt(scan));
        scan.nextLine();
        System.out.print("Enter Product Stock : ");
        product.setStock(readInt(scan));
        scan.nextLine();
        product.setUnit(readNonEmpty(scan, "Enter Product Unit  : "));

        System.out.println(service.addProduct(product)
                ? "Product added successfully."
                : "Unable to add product.");
    }

    private static void updateProduct(ProductService service, Scanner scan) {
        System.out.println("\n── Update Product ──");
        System.out.print("Enter Product ID   : ");
        int id = readInt(scan);
        scan.nextLine();

        if (service.findById(id) == null) {
            System.out.println("No product found with ID " + id + ".");
            return;
        }

        Product product = new Product();
        product.setId(id);
        product.setName(readNonEmpty(scan, "Enter New Name      : "));
        System.out.print("Enter New Price     : ");
        product.setPrice(readInt(scan));
        scan.nextLine();
        System.out.print("Enter New Stock     : ");
        product.setStock(readInt(scan));
        scan.nextLine();
        product.setUnit(readNonEmpty(scan, "Enter New Unit      : "));

        System.out.println(service.updateProduct(product)
                ? "Product updated successfully."
                : "Unable to update product.");
    }

    private static void deleteProduct(ProductService service, Scanner scan) {
        System.out.println("\n── Delete Product ──");
        System.out.print("Enter Product ID: ");
        int id = readInt(scan);
        scan.nextLine();

        Product product = service.findById(id);
        if (product == null) {
            System.out.println("No product found with ID " + id + ".");
            return;
        }

        if (service.deleteProduct(id)) {
            System.out.println("Deleted product:");
            System.out.println(product);
        } else {
            System.out.println("Unable to delete product.");
        }
    }

    private static void findProduct(ProductService service, Scanner scan) {
        System.out.println("\n── Find Product ──");
        System.out.print("Enter Product ID: ");
        int id = readInt(scan);
        scan.nextLine();

        Product product = service.findById(id);
        if (product == null)
            System.out.println("No product found with ID " + id + ".");
        else
            System.out.println(product);
    }

    private static void displayAll(ProductService service) {
        System.out.println("\n── All Products ──");
        var products = service.allProduct();
        if (products.isEmpty())
            System.out.println("No products in the inventory.");
        else
            products.forEach(System.out::println);
    }

    // ── Input Helpers ─────────────────────────────────────────────────────────

    private static int readInt(Scanner scan) {
        while (!scan.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scan.next();
        }
        return scan.nextInt();
    }

    private static String readNonEmpty(Scanner scan, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scan.nextLine().trim();
            if (input.isEmpty()) System.out.println("This field cannot be empty.");
        } while (input.isEmpty());
        return input;
    }
}
