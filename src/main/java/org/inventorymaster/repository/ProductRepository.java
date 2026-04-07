package org.inventorymaster.repository;

import org.inventorymaster.model.Product;

import java.util.ArrayList;

public interface ProductRepository {

    default void closeConnection() {
    }

    Product addProduct(Product product);

    Product updateProduct(Product product);

    Product deleteProduct(Integer id);

    Product findById(Integer id);

    ArrayList<Product> allProduct();
}
