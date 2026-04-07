package org.inventorymaster.services;

import org.inventorymaster.model.Product;
import org.inventorymaster.repository.ProductRepository;
import org.inventorymaster.repository.mysql.ProductRepositoryImpl;

import java.util.ArrayList;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository = new ProductRepositoryImpl();

    @Override
    public void closeConnection() {
        repository.closeConnection();
    }

    @Override
    public Boolean addProduct(Product product) {
        return repository.addProduct(product) != null;
    }

    @Override
    public Boolean updateProduct(Product product) {
        return repository.updateProduct(product) != null;
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        return repository.deleteProduct(id) != null;
    }

    @Override
    public Product findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public ArrayList<Product> allProduct() {
        return repository.allProduct();
    }
}
