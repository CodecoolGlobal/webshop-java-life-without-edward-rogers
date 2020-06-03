package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Cart;
import com.codecool.shop.util.Email;
import com.codecool.shop.util.Intermittent;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/payment"})
public class PayController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (Cart.getCartListSize() > 0 && Intermittent.getOrder() != null) {
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("cartListLength", Cart.getCartListSize());
            context.setVariable("totalPrice", Cart.getCartPrice());
            engine.process("product/payment.html", context, resp.getWriter());
        } else {
            resp.sendRedirect("/");
        }
    }
}
