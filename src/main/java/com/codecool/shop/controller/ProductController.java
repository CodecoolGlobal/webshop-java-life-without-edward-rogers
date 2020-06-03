package com.codecool.shop.controller;

import com.codecool.shop.config.ConnectDB;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.jdbc.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.jdbc.ProductDaoJDBC;
import com.codecool.shop.dao.jdbc.SupplierDaoJDBC;
import com.codecool.shop.model.Cart;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.util.Filter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    DataSource datasource = ConnectDB.getInstance();

    private final ProductDao productDataStore = ProductDaoJDBC.getInstance(datasource);
    private final ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance(datasource);
    private final SupplierDao supplierDataStore = SupplierDaoJDBC.getInstance(datasource);

    public ProductController() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Filter filter = new Filter(productDataStore, productCategoryDataStore, supplierDataStore, req);

        List<Product> products = filter.filterProduct();
        String categoryNames = filter.getCategoryNames();
        String supplierNames = filter.getSupplierNames();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());
        addVariablesToContext(context, products, categoryNames, supplierNames);

        engine.process("product/index.html", context, resp.getWriter());
    }

    private void addVariablesToContext(WebContext context, List<Product> products, String categoryNames, String supplierNames) {
        context.setVariable("allCategory", productCategoryDataStore.getAll());
        context.setVariable("allSupplier", supplierDataStore.getAll());
        context.setVariable("categories", categoryNames);
        context.setVariable("suppliers", supplierNames);
        context.setVariable("products", products);

        context.setVariable("cartListLength", Cart.getCartListSize());
    }
}