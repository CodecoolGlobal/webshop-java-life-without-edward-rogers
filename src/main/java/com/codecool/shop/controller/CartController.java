package com.codecool.shop.controller;

import com.codecool.shop.config.ConnectDB;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.database_connection.CartDaoJDBC;
import com.codecool.shop.model.Cart;
import com.codecool.shop.util.Util;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    DataSource dataSource = ConnectDB.getInstance();
    private CartDaoJDBC cartDaoJDBC = CartDaoJDBC.getInstance(dataSource);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Cart cart = Util.returnPreciseCart(session, cartDaoJDBC);
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        System.out.println(cart.getProductsInCart());
        context.setVariable("cart", cart.getProductsInCart());
        context.setVariable("cartListLength", cart.getCartListSize());
        context.setVariable("totalPrice", cart.getCartPrice());
        if (cart.getCartListSize() <= 0){
            context.setVariable("errorMessage", "Your cart is empty!");
        } else {
            context.setVariable("errorMessage", "");
        }
        engine.process("product/cart.html", context, resp.getWriter());
    }
}
