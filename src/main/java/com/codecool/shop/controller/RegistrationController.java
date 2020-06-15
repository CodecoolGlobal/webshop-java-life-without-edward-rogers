package com.codecool.shop.controller;

import com.codecool.shop.config.ConnectDB;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.jdbc.UserDaoJDBC;
import com.codecool.shop.model.User;
import com.codecool.shop.util.HashPassword;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationController extends HttpServlet {
    DataSource datasource = ConnectDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("product/registration.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDaoJDBC userDaoJDBC = new UserDaoJDBC(datasource);
        HashPassword hashPassword = HashPassword.getInstance();
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String address = req.getParameter("address");
        int zip = Integer.parseInt(req.getParameter("zip"));
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phone");

        try {
            byte[] hashedPassword = hashPassword.hash(password);
            userDaoJDBC.add(new User(userName, hashedPassword, address,
                    zip, country, city, email, phoneNumber));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/");
    }
}
