package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/add-product"})
public class AddController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int productId = Integer.parseInt(req.getParameter("id"));
        ProductDaoMem productDaoMem = ProductDaoMem.getInstance();
        List<Product> products = productDaoMem.getAll();
        ArrayList<Product> productsInCart = Cart.getProductsInCart();
        for(Product product: products){
            if(product.getId() == productId){
                productsInCart.add(product);
            }
        }
        Cart.setProductsInCart(productsInCart);
        System.out.println(Cart.getProductsInCart());
    }
}

