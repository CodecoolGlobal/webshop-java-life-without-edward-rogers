package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJDBC implements SupplierDao {

    private DataSource dataSource;
    private static SupplierDaoJDBC instance = null;

    public SupplierDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
        truncateTable();
    }

    public static SupplierDaoJDBC getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new SupplierDaoJDBC(dataSource);
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {

        String SQL = "INSERT INTO suppliers(name, description)"
                + "VALUES(?,?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, supplier.getName());
            pstmt.setString(2, supplier.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public int getIdByName(String name) {
        int id = 0;
        String SQL = "SELECT id FROM suppliers WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }return id;

    }

    @Override
    public Supplier find(int id) {
        String name = "";
        String description = "";

        String SQL = "SELECT * FROM suppliers WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
                description = rs.getString("description");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        Supplier supplier = new Supplier(name, description);
        supplier.setId(id);
        return supplier;
    }

    @Override
    public void remove(int id) {
        String SQL = "DELETE FROM suppliers WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> allSupplier = new ArrayList<>();

        String SQL = "SELECT * FROM suppliers";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Supplier supplier = new Supplier(name, description);
                supplier.setId(id);
                allSupplier.add(supplier);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return allSupplier;
    }

    private void truncateTable() {
        String SQL = "TRUNCATE TABLE suppliers RESTART IDENTITY CASCADE";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
