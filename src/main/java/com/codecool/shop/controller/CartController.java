package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Cart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("cart", Cart.getProductsInCart());
        context.setVariable("cartListLength", Cart.getCartListSize());
        context.setVariable("totalPrice", Cart.getCartPrice());
        if (Cart.getCartListSize() <= 0){
            context.setVariable("errorMessage", "There is nothing to display as you have nothing in your Cart.");
        } else {
            context.setVariable("errorMessage", "");
        }
        engine.process("product/cart.html", context, resp.getWriter());
    }
}
