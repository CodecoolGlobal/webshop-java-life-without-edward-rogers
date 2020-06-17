package com.codecool.shop.dao.database_connection;

import com.codecool.shop.config.ConnectDB;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDaoJDBC implements CartDao {

    DataSource dataSource = ConnectDB.getInstance();
    ProductDaoJDBC productDaoJDBC = ProductDaoJDBC.getInstance(dataSource);
    private static CartDaoJDBC instance = null;

    public CartDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static CartDaoJDBC getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new CartDaoJDBC(dataSource);
        }
        return instance;
    }

    @Override
    public void add(Cart cart, Product product) {

    }

    @Override
    public Cart find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    public Cart getCartByUser(String userID) {
        if(userID == null){ return new Cart("", "");}
        int id = 0;
        ArrayList<Product> products = new ArrayList<>();

        String SQL = "SELECT user_id, cart.id, pic.product_id, pic.quantity FROM cart\n" +
                "JOIN product_in_cart pic on cart.id = pic.cart_id\n" +
                "WHERE ordered is FALSE AND user_id = ?" +
                "GROUP BY cart.id, user_id, pic.product_id, pic.quantity";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
                Product product = productDaoJDBC.find(rs.getInt("product_id"));
                product.setQuantity(rs.getInt("quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }

        Cart cart = new Cart("" + userID, "" + id);
        cart.setId(id);
        cart.setUserID(Integer.parseInt(userID));
        cart.setProductsInCart(products);

        System.out.println(cart.toString());
        return cart;
    }
}

