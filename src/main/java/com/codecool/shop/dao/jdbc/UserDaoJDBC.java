package com.codecool.shop.dao.jdbc;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoJDBC implements UserDao {

    private DataSource dataSource;

    public UserDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(User user) {
        String SQL = "INSERT INTO users(name, password, country, city, zip, address, email, phone_number)"
                + "VALUES(?,?,?,?,?,?,?,?)";


        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setBytes(2, user.getHashedPassword());
            pstmt.setString(3, user.getCountry());
            pstmt.setString(4, user.getCity());
            pstmt.setInt(5, user.getZip());
            pstmt.setString(6, user.getAddress());
            pstmt.setString(7, user.getEmail());
            pstmt.setString(8, user.getPhoneNumber());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
