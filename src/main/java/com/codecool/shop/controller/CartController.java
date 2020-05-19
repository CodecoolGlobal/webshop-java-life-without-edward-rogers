package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
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
        context.setVariable("cartListLength", Cart.getProductsInCart().size());
        context.setVariable("totalPrice", calculateTotalSum());
        engine.process("product/cart.html", context, resp.getWriter());
    }

    private double calculateTotalSum() {
        double sum = 0;
        for (Product product : Cart.getProductsInCart()){
            sum += product.getDefaultPrice() * product.getQuantity();
        }
        return sum;
    }
}
