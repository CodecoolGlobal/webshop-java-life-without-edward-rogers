package com.codecool.shop.dao.database_connection;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
            pstmt.setString(2, user.getHashedPassword());
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

    @Override
    public Map<String, String> getDataOfUser(String userName) {
        Map<String, String> userData = new HashMap();
        String SQL = "SELECT country, city, zip, address, email, phone_number FROM users WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                userData.put("country", rs.getString("country"));
                userData.put("city", rs.getString("city"));
                userData.put("zip", "" + rs.getInt("zip"));
                userData.put("address", rs.getString("address"));
                userData.put("email", rs.getString("email"));
                userData.put("phoneNUmber", rs.getString("phoneNumber"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return userData;
    }

    @Override
    public Map<String, String> getNameAndPassword(String userName) {
        Map<String, String> userData = new HashMap();
        String SQL = "SELECT id, name, password FROM users WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                userData.put("username", rs.getString("name"));
                userData.put("password", rs.getString("password"));
                userData.put("id", rs.getString("id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return userData;
    }

}
