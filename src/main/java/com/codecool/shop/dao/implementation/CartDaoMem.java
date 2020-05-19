package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import java.util.ArrayList;

public class CartDaoMem implements CartDao {

    /**
     * Add a product to mentioned cart.
     * @param cart - Add a product to this cart.
     * @param product - Element which we add to that cart.
     */
    @Override
    public void add(Cart cart, Product product) {
        ArrayList<Product> cartProducts = cart.getProductsInCart();
        cartProducts.add(product);
        cart.setProductsInCart(cartProducts);
    }

    /**
     * Find cart according to his id.
     * @param id - Cart's id, we are searching according to that.
     * @return Cart object.
     */
    @Override
    public Cart find(int id) {
        return null;
    }

    /**
     * Remove a cart according to his id.
     * @param id - Cart's id, we remove cart according to that.
     */
    @Override
    public void remove(int id) {

    }
}
