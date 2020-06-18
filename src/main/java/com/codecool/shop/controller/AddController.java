package com.codecool.shop.controller;

import com.codecool.shop.config.ConnectDB;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.database_connection.CartDaoJDBC;
import com.codecool.shop.dao.database_connection.ProductDaoJDBC;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.util.Util;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/add-product"})
public class AddController extends HttpServlet {

    private Integer quantity;
    DataSource dataSource = ConnectDB.getInstance();
    private CartDaoJDBC cartDaoJDBC = CartDaoJDBC.getInstance(dataSource);
    private Cart cart;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();
        cart = Util.returnPreciseCart(session, cartDaoJDBC);

        if ((req.getParameter("quantity")) == null) {
            quantity = null;
        } else {
            quantity = Integer.parseInt(req.getParameter("quantity"));
        }

        int productId = Integer.parseInt(req.getParameter("id"));
        ProductDao productDao = ProductDaoJDBC.getInstance(dataSource);
        List<Product> products = productDao.getAll();
        handleProductsInCart(quantity, productId, products);
    }

    /**
     * Choose to change quantity (add or remove) or add a new one (or increase the quantity).
     *
     * @param quantity  - The amount of the product
     * @param productId - The id of the product to change quantity.
     * @param products  - Product in store.
     */
    private void handleProductsInCart(Integer quantity, int productId, List<Product> products) {
        if (quantity != null) {
            changeQuantity(productId, quantity);
        } else {
            addToCartList(productId, products);
        }
    }

    /**
     * Add product to cart by id
     *
     * @param productId - id of the product
     * @param products  - List of all product that shop has
     */
    private void addToCartList(int productId, List<Product> products) {
        ArrayList<Product> productsInCart = cart.getProductsInCart();
        for (Product product : products) {
            if (product.getId() == productId) {
                raiseQuantityOrAdd(productId, productsInCart, product);
            }
        }
        cart.setProductsInCart(productsInCart);

    }

    /**
     * Checks if product is in cart list. We will use this to print out proper data in /cart route.
     * If yes, we raise the quantity of product in list, if not, we add product to list.
     *
     * @param productId      - ID of added product.
     * @param productsInCart - Products in user's list.
     */
    private void raiseQuantityOrAdd(int productId, List<Product> productsInCart, Product product) {
        boolean changeHappened = false;
        for (Product storedProduct : productsInCart) {
            if (storedProduct.getId() == productId) {
                cartDaoJDBC.editQuantity(productId, cart, storedProduct.getQuantity() + 1);
                storedProduct.setQuantity(storedProduct.getQuantity() + 1);
                changeHappened = true;
            }
        }
        if (!changeHappened) {
            cartDaoJDBC.addProduct(productId, cart);
//            product.setQuantity(product.getQuantity() + 1);
//            productsInCart.add(product);
        }

    }

    /**
     * Change the quantity of the selected product to a custom quantity
     *
     * @param productId - Id of the selected product
     * @param quantity  - Custom quantity for the change
     */
    private void changeQuantity(int productId, int quantity) {
        ArrayList<Product> cartList = new ArrayList<>(cart.getProductsInCart());
        for (Product product : cartList) {
            if (product.getId() == productId) {
                cartDaoJDBC.editQuantity(productId, cart, quantity);
                product.setQuantity(quantity);
                if (quantity == 0) {
                    cartDaoJDBC.removeProduct(productId, cart);
                    ArrayList<Product> newCartList = cart.getProductsInCart();
                    newCartList.remove(product);
                    cart.setProductsInCart(newCartList);
                }
            }
        }
    }

}

