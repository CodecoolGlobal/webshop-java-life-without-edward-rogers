package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {

    private DataSource dataSource;
    private static ProductCategoryDaoJDBC instance = null;

    public ProductCategoryDaoJDBC(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
        truncateTable();
    }

    public static ProductCategoryDaoJDBC getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC(dataSource);
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {

        String SQL = "INSERT INTO product_category(name, department, description)"
                + "VALUES(?,?,?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getDepartment());
            pstmt.setString(3, category.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public ProductCategory find(int id) {
        String name = "";
        String description = "";
        String department = "";

        String SQL = "SELECT * FROM product_category WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
                department = rs.getString("department");
                description = rs.getString("description");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        ProductCategory productCategory = new ProductCategory(name, department, description);
        productCategory.setId(id);
        return productCategory;
    }

    @Override
    public int getIdByName(String name) {
        int id = 0;
        String SQL = "SELECT id FROM product_category WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    @Override
    public void remove(int id) {
        String SQL = "DELETE FROM product_category WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> allProductCategory = new ArrayList<>();

        String SQL = "SELECT * FROM product_category";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String department = rs.getString("department");
                String description = rs.getString("description");
                ProductCategory productCategory = new ProductCategory(name, department, description);
                productCategory.setId(id);
                allProductCategory.add(productCategory);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return allProductCategory;
    }

    private void truncateTable() {
        String SQL = "TRUNCATE TABLE product_category RESTART IDENTITY CASCADE";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
