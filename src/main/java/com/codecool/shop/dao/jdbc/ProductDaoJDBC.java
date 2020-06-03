package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {

    private DataSource dataSource;
    private ProductCategoryDao productCategoryDao;
    private SupplierDao supplierDao;
    private static ProductDaoJDBC instance = null;

    public ProductDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
        productCategoryDao = new ProductCategoryDaoJDBC(dataSource);
        supplierDao = new SupplierDaoJDBC(dataSource);
        truncateTable();
    }

    public static ProductDaoJDBC getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new ProductDaoJDBC(dataSource);
        }
        return instance;
    }


    @Override
    public void add(Product product) {
        String SQL = "INSERT INTO products(name, description, default_price, currency, supplier_id, product_category_id, picture)"
                + "VALUES(?,?,?,?,?,?,?)";


        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setFloat(3, product.getDefaultPrice());
            pstmt.setString(4, String.valueOf(product.getDefaultCurrency()));
            pstmt.setInt(5, supplierDao.getIdByName(product.getSupplier().getName()));
            pstmt.setInt(6, productCategoryDao.getIdByName(product.getProductCategory().getName()));
            pstmt.setString(7, "product_" + product.getId() + ".jpg");

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Product find(int id) {
        String name = "";
        String description = "";
        float default_price = 0;
        String currency = "";
        int supplier_id = 0;
        int product_category_id = 0;

        String SQL = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
                description = rs.getString("description");
                default_price = rs.getFloat("default_price");
                currency = rs.getString("currency");
                supplier_id = rs.getInt("supplier_id");
                product_category_id = rs.getInt("product_category_id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        Supplier supplier = supplierDao.find(supplier_id);
        ProductCategory productCategory = productCategoryDao.find(product_category_id);
        Product product = new Product(name, default_price, currency, description, productCategory, supplier);
        product.setId(id);
        return product;
    }

    @Override
    public void remove(int id) {
        String SQL = "DELETE FROM products WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Product> getAll() {

        List<Product> allProducts = new ArrayList<>();

        String SQL = "SELECT * FROM products";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                float default_price = rs.getFloat("default_price");
                String currency = rs.getString("currency");
                int supplier_id = rs.getInt("supplier_id");
                int product_category_id = rs.getInt("product_category_id");
                Supplier supplier = supplierDao.find(supplier_id);
                ProductCategory productCategory = productCategoryDao.find(product_category_id);
                Product product = new Product(name, default_price, currency, description, productCategory, supplier);
                product.setId(id);
                allProducts.add(product);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return allProducts;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        List<Product> allProductsBySupplier = new ArrayList<>();
        String SQL = "SELECT * FROM products WHERE supplier_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, supplier.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                float default_price = rs.getFloat("default_price");
                String currency = rs.getString("currency");
                int product_category_id = rs.getInt("product_category_id");
                ProductCategory productCategory = productCategoryDao.find(product_category_id);
                Product product = new Product(name, default_price, currency, description, productCategory, supplier);
                product.setId(id);
                allProductsBySupplier.add(product);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return allProductsBySupplier;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> allProductsByCategory = new ArrayList<>();
        String SQL = "SELECT * FROM products WHERE product_category_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, productCategory.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                float default_price = rs.getFloat("default_price");
                String currency = rs.getString("currency");
                int supplier_id = rs.getInt("supplier_id");
                Supplier supplier = supplierDao.find(supplier_id);
                Product product = new Product(name, default_price, currency, description, productCategory, supplier);
                product.setId(id);
                allProductsByCategory.add(product);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return allProductsByCategory;
    }

    private void truncateTable() {
        String SQL = "TRUNCATE TABLE products RESTART IDENTITY CASCADE";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
