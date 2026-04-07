package org.inventorymaster.services;

import org.inventorymaster.model.Product;

import java.util.ArrayList;

public interface ProductService {
    void closeConnection();
    Boolean addProduct(Product product);
    Boolean updateProduct(Product product);
    Boolean deleteProduct(Integer id);
    Product findById(Integer id);
    ArrayList<Product> allProduct();
}
