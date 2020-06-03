package com.codecool.shop.controller;

import com.codecool.shop.util.Email;
import com.codecool.shop.util.Intermittent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/email"})
public class EmailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/");
        Email email = new Email(Intermittent.getOrder());
        email.sendEmail();
    }

}
