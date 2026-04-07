package org.inventorymaster.repository.collection;

import org.inventorymaster.model.Product;
import org.inventorymaster.repository.ProductRepository;

import java.util.ArrayList;

/**
 * In-memory (Collection-based) implementation of ProductRepository.
 * Useful for testing or when no database is available.
 */
public class ProductRepositoryImpl implements ProductRepository {

    private final ArrayList<Product> products = new ArrayList<>();

    @Override
    public Product addProduct(Product product) {
        products.add(product);
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        int index = products.indexOf(product);
        if (index == -1) return null;
        products.set(index, product);
        return product;
    }

    @Override
    public Product deleteProduct(Integer id) {
        int index = products.indexOf(new Product(id, "", 0, 0, ""));
        if (index == -1) return null;
        return products.remove(index);
    }

    @Override
    public Product findById(Integer id) {
        int index = products.indexOf(new Product(id, "", 0, 0, ""));
        if (index < 0) return null;
        return products.get(index);
    }

    @Override
    public ArrayList<Product> allProduct() {
        return products;
    }
}
