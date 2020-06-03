package com.codecool.shop.controller;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;
import com.codecool.shop.util.Email;
import com.codecool.shop.util.Intermittent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/save-order-details"})
public class OrderDetailsController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            resp.sendRedirect(req.getContextPath() + "/payment");
            Intermittent.setOrder(createOrder(req));
        } catch (Exception e){
            resp.sendRedirect("/");
            System.err.println("There was a problem with setting the order sending. Type: " + e.getMessage());
        }
    }

    /**
     * This method create a new Order object and fill it with the details of the actual Order.
     * @param req http request
     * @return a new Order object with the customer details and with the Cart content.
     */
    Order createOrder(HttpServletRequest req){
        return new Order(req.getParameter("inputName"), req.getParameter("inputEmail"),
                req.getParameter("inputPhone"), req.getParameter("inputCountryB"),
                req.getParameter("inputCityB"), req.getParameter("inputZipB"),
                req.getParameter("inputAddressB"), req.getParameter("inputCountryS"),
                req.getParameter("inputCityS"), req.getParameter("inputZipS"),
                req.getParameter("inputAddressS"), Cart.getProductsInCart());
    }

}