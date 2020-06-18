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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void addProduct(int productID, Cart cart) {
        int cartId = Integer.parseInt(cart.getDescription());
        String SQL = "INSERT INTO product_in_cart (cart_id, product_id, quantity)"
                + "VALUES(?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, cartId);
            pstmt.setInt(2, productID);
            pstmt.setInt(3, 1);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void editQuantity(int productID, Cart cart, int quantity) {
        int cartId = Integer.parseInt(cart.getDescription());
        String SQL = "UPDATE product_in_cart SET quantity = ?"
                + "WHERE cart_id= ? AND product_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, cartId);
            pstmt.setInt(3, productID);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Cart getCart(int userID) {
        if (userHasCart(userID)) {
            return getCartByUser(String.valueOf(userID));
        }
        addCartToUser(userID);
        return getCartByUser(String.valueOf(userID));
    }

    public void removeProduct(int productID, Cart cart) {
        int cartId = Integer.parseInt(cart.getDescription());
        String SQL = "DELETE FROM product_in_cart  WHERE product_id = ? AND cart_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, productID);
            pstmt.setInt(2, cartId);
            pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addCartToUser(int userId) {
        String SQL = "INSERT INTO cart(user_id) VALUES(?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
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
        int id = 1;
        ArrayList<Product> products = new ArrayList<>();
        int ID = Integer.parseInt(userID);

        String SQL = "SELECT user_id, cart.id, pic.product_id, pic.quantity FROM cart JOIN product_in_cart pic on cart.id = pic.cart_id WHERE ordered is FALSE AND user_id = ?" +
                "GROUP BY cart.id, user_id, pic.product_id, pic.quantity";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
                Product product = productDaoJDBC.find(rs.getInt("product_id"));
                product.setQuantity(rs.getInt("quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Cart cart = new Cart("" + ID, "" + id);
        cart.setId(id);
        cart.setUserID(ID);
        cart.setProductsInCart(products);

        return cart;
    }

    public boolean userHasCart(int userID) {
        String SQL = "SELECT id FROM cart WHERE user_id = ? AND ordered IS FALSE";
        int id = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id != 0;
    }
}

