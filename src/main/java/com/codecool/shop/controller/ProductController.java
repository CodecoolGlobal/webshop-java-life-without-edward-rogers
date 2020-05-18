package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    private StringBuffer categoryNames = new StringBuffer();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        categoryNames.delete(0, categoryNames.length());

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        ArrayList<Product> products = filterProductsByGivenCategories(req, productCategoryDataStore, productDataStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("allCategory", productCategoryDataStore.getAll());


        context.setVariable("categories", "Categories: " + categoryNames.toString().trim().replaceAll(",$", ""));
        context.setVariable("products", products);
        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
    }

    /**
     * This method will get all product in the given categories.
     * @param request http request
     * @param productCategoryDataStore stores the categories
     * @param productDataStore stores the products
     * @return the filtered product ArrayList
     */
    private ArrayList<Product> filterProductsByGivenCategories(HttpServletRequest request, ProductCategoryDao productCategoryDataStore,
                                            ProductDao productDataStore) {

        ArrayList<Product> products = new ArrayList<>();
        for (ProductCategory items : productCategoryDataStore.getAll()) {
            if (request.getParameter(items.getName()) != null) {
                int id = Integer.parseInt(request.getParameter(items.getName()));
                products.addAll(productDataStore.getBy(productCategoryDataStore.find(id)));
                addCategoryToCategoryNames(productCategoryDataStore.find(id).getName());
            }
        } if (products.isEmpty()) {
            products.addAll(productDataStore.getBy(productCategoryDataStore.find(1)));
            addCategoryToCategoryNames(productCategoryDataStore.find(1).getName());
        }
        return products;
    }

    /**
     * Concat category names together with comma
     * @param category category to add new category
     */
    private void addCategoryToCategoryNames(String category){
        categoryNames.append(category).append(", ");
    }
}

