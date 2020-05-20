package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/add-product"})
public class AddController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int productId = Integer.parseInt(req.getParameter("id"));
        ProductDaoMem productDaoMem = ProductDaoMem.getInstance();
        List<Product> products = productDaoMem.getAll();
        checkGetMethodParameters(req, productId, products);

    }
    /**
     *Choose to change quantity (add or remove) or add a new one (or increase the quantity).
     *
     * @param request - HttpServletRequest.
     * @param productId - The id of the product to change quantity.
     * @param products - Product in store.
     */
    private void checkGetMethodParameters(HttpServletRequest request, int productId, List<Product> products) {
        if(request.getParameter("quantity") != null){
            int quantity = Integer.parseInt(request.getParameter("quantity"));
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
        ArrayList<Product> productsInCart = Cart.getProductsInCart();
        for (Product product : products) {
            if (product.getId() == productId) {
                raiseQuantityOrAdd(productId, productsInCart, product);
            }
        }
        Cart.setProductsInCart(productsInCart);
    }

    /**
     * Checks if product is in cart list. We will use this to print out proper data in /cart route.
     * If yes, we raise the quantity of product in list, if not, we add product to list.
     *
     * @param productId      - ID of added product.
     * @param productsInCart - Products in user's list.
     * @param product        - Product in store.
     */
    private void raiseQuantityOrAdd(int productId, List<Product> productsInCart, Product product) {
        boolean changeHappened = false;
        for (Product storedProduct : productsInCart) {
            if (storedProduct.getId() == productId) {
                storedProduct.setQuantity(storedProduct.getQuantity() + 1);
                changeHappened = true;
            }
        }
        if (!changeHappened) {
            product.setQuantity(product.getQuantity() + 1);
            productsInCart.add(product);
        }
    }

    /**
     * Change the quantity of the selected product to a custom quantity
     * @param productId - Id of the selected product
     * @param quantity - Custom quantity for the change
     */
    private void changeQuantity(int productId, int quantity) {
        ArrayList<Product> cartList = new ArrayList<>();
        cartList.addAll(Cart.getProductsInCart());
        for (Product product : cartList) {
            if (product.getId() == productId) {
                product.setQuantity(quantity);
                if (quantity == 0) {
                    ArrayList<Product> newCartList = Cart.getProductsInCart();
                    newCartList.remove(product);
                    Cart.setProductsInCart(newCartList);
                }
            }
        }
    }

}

