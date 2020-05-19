package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

public interface CartDao {

    void add(Cart cart, Product product);
    Cart find(int id);
    void remove(int id);
}
