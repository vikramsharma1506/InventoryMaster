package org.inventorymaster.repository.mysql;

import org.inventorymaster.model.Product;
import org.inventorymaster.repository.ProductRepository;

import java.sql.*;
import java.util.ArrayList;

/**
 * MySQL JDBC implementation of ProductRepository.
 *
 * Database setup (run once in MySQL):
 * -------------------------------------------------------
 *   CREATE DATABASE IF NOT EXISTS inventory_master;
 *   USE inventory_master;
 *   CREATE TABLE IF NOT EXISTS product (
 *       id    INT PRIMARY KEY,
 *       name  VARCHAR(100) NOT NULL,
 *       price INT NOT NULL,
 *       stock INT NOT NULL,
 *       unit  VARCHAR(50)  NOT NULL
 *   );
 * -------------------------------------------------------
 *
 * Update DB_URL, DB_USER, DB_PASSWORD below to match your setup.
 */
public class ProductRepositoryImpl implements ProductRepository {

    // ── Configuration ─────────────────────────────────────────────────────────
    private static final String DB_URL      = "jdbc:mysql://localhost:3306/inventory_master"
                                             + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DB_USER     = "root";
    private static final String DB_PASSWORD = "Your_password";   // <-- change this
    // ──────────────────────────────────────────────────────────────────────────

    private Connection connection;

    public ProductRepositoryImpl() {
        try {
            // Driver is auto-registered via SPI in MySQL Connector/J 8.x — no Class.forName needed.
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("[DB] Connected to MySQL successfully.");
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Could not connect to the database: " + e.getMessage());
            System.err.println("Make sure MySQL is running and credentials in ProductRepositoryImpl are correct.");
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DB] Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Failed to close connection: " + e.getMessage());
        }
    }

    // ── CRUD Operations ───────────────────────────────────────────────────────

    @Override
    public Product addProduct(Product product) {
        String sql = "INSERT INTO product (id, name, price, stock, unit) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, product.getId());
            ps.setString(2, product.getName());
            ps.setInt(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setString(5, product.getUnit());
            if (ps.executeUpdate() > 0) return product;
        } catch (SQLException e) {
            System.err.println("[DB ERROR] addProduct: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        String sql = "UPDATE product SET name=?, price=?, stock=?, unit=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setString(4, product.getUnit());
            ps.setInt(5, product.getId());
            if (ps.executeUpdate() > 0) return product;
        } catch (SQLException e) {
            System.err.println("[DB ERROR] updateProduct: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Product deleteProduct(Integer id) {
        // Fetch first so the caller can show what was deleted
        Product existing = findById(id);
        if (existing == null) return null;

        String sql = "DELETE FROM product WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) return existing;
        } catch (SQLException e) {
            System.err.println("[DB ERROR] deleteProduct: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Product findById(Integer id) {
        String sql = "SELECT id, name, price, stock, unit FROM product WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[DB ERROR] findById: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Product> allProduct() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, price, stock, unit FROM product ORDER BY id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("[DB ERROR] allProduct: " + e.getMessage());
        }
        return products;
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private Product mapRow(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getInt("stock"),
                rs.getString("unit")
        );
    }
}
