package com.codecool.shop.controller;

import com.codecool.shop.config.ConnectDB;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.database_connection.UserDaoJDBC;
import com.codecool.shop.util.HashPassword;
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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private Boolean passwordsAreMatching = null;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("passwordAreMatching", passwordsAreMatching);
        engine.process("user/login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataSource datasource = ConnectDB.getInstance();
        UserDao userDao = new UserDaoJDBC(datasource);

        saveDataToSession(req, userDao, resp);

    }

    private void saveDataToSession(HttpServletRequest req, UserDao userDao, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Map<String, String> userNameAndPass = userDao.getNameAndPassword(username);
        try {
            passwordsAreMatching = HashPassword.getInstance().verifyPassword(password, userNameAndPass.get("password"));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NullPointerException e) {
            passwordsAreMatching = false;
            resp.sendRedirect("/login");
        }

        if(passwordsAreMatching){
            HttpSession session = req.getSession();
            session.setAttribute("userName", username);
            session.setAttribute("userID", userNameAndPass.get("id"));
            resp.sendRedirect("/");
        }
    }


}
