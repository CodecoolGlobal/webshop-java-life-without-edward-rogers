package com.codecool.shop.util;

import com.codecool.shop.dao.database_connection.CartDaoJDBC;
import com.codecool.shop.model.Cart;

import javax.servlet.http.HttpSession;

public class Util {

    public static Cart returnPreciseCart(HttpSession session, CartDaoJDBC cartDaoJDBC) {
        if (session != null) {
            return cartDaoJDBC.getCart(Integer.parseInt((String) session.getAttribute("userID")));
        }
        return new Cart("", "");
    }
}
