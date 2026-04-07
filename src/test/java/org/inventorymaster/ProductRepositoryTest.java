package org.inventorymaster;

import org.inventorymaster.model.Product;
import org.inventorymaster.repository.collection.ProductRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the in-memory (Collection) repository.
 * No database required.
 */
class ProductRepositoryTest {

    private ProductRepositoryImpl repo;

    @BeforeEach
    void setUp() {
        repo = new ProductRepositoryImpl();
    }

    @Test
    void testAddProduct() {
        Product p = new Product(1, "Rice", 50, 100, "kg");
        Product result = repo.addProduct(p);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testFindById() {
        repo.addProduct(new Product(2, "Sugar", 40, 50, "kg"));
        Product found = repo.findById(2);
        assertNotNull(found);
        assertEquals("Sugar", found.getName());
    }

    @Test
    void testFindByIdNotFound() {
        assertNull(repo.findById(999));
    }

    @Test
    void testUpdateProduct() {
        repo.addProduct(new Product(3, "Salt", 20, 30, "kg"));
        Product updated = new Product(3, "Salt Refined", 25, 30, "kg");
        Product result = repo.updateProduct(updated);
        assertNotNull(result);
        assertEquals("Salt Refined", repo.findById(3).getName());
    }

    @Test
    void testDeleteProduct() {
        repo.addProduct(new Product(4, "Oil", 120, 20, "litre"));
        Product deleted = repo.deleteProduct(4);
        assertNotNull(deleted);
        assertNull(repo.findById(4));
    }

    @Test
    void testAllProducts() {
        repo.addProduct(new Product(5, "Wheat", 35, 200, "kg"));
        repo.addProduct(new Product(6, "Milk", 60, 50, "litre"));
        assertEquals(2, repo.allProduct().size());
    }
}
