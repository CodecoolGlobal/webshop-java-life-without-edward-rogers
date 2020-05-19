package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Cart;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
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
    private StringBuffer supplierNames = new StringBuffer();
    private boolean requestForCategory = false;
    private boolean requestForSupplier = false;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        categoryNames.delete(0, categoryNames.length());
        supplierNames.delete(0, supplierNames.length());
        requestForSupplier = false;
        requestForCategory = false;

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        List<Product> products = filterProduct(req, supplierDataStore, productCategoryDataStore, productDataStore);


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("allCategory", productCategoryDataStore.getAll());
        context.setVariable("allSupplier", supplierDataStore.getAll());

        context.setVariable("categories", categoryNames.toString().trim().replaceAll(",$", ""));
        context.setVariable("suppliers", supplierNames.toString().trim().replaceAll(",$", ""));
        context.setVariable("products", products);

        context.setVariable("cartListLength", Cart.getProductsInCart().size());
        engine.process("product/index.html", context, resp.getWriter());
    }

    /**
     * This method will get all products in the given categories and sets the boolean for request true, if there were any.
     *
     * @param request                  http request
     * @param productCategoryDataStore stores the categories
     * @param productDataStore         stores the products
     * @return the filtered product List
     */
    private List<Product> filterProductsByGivenCategories(HttpServletRequest request, ProductCategoryDao productCategoryDataStore,
                                                          ProductDao productDataStore) {

        List<Product> products = new ArrayList<>();
        for (ProductCategory category : productCategoryDataStore.getAll()) {
            if (request.getParameter(category.getName()) != null) {
                requestForCategory = true;
                int id = Integer.parseInt(request.getParameter(category.getName()));
                products.addAll(productDataStore.getBy(productCategoryDataStore.find(id)));
                addCategoryToCategoryNames(productCategoryDataStore.find(id).getName());
            }
        }
        return products;
    }

    /**
     * This method will get all products for the given suppliers and sets the boolean for request true, if there were any.
     *
     * @param request           http request
     * @param supplierDataStore stores the suppliers
     * @param productDataStore  stores the products
     * @return the filtered product List
     */
    private List<Product> filterProductsByGivenSuppliers(HttpServletRequest request, SupplierDao supplierDataStore,
                                                         ProductDao productDataStore) {
        List<Product> products = new ArrayList<>();
        for (Supplier supplier : supplierDataStore.getAll()) {
            if (request.getParameter(supplier.getName()) != null) {
                requestForSupplier = true;
                int id = Integer.parseInt(request.getParameter(supplier.getName()));
                products.addAll(productDataStore.getBy(supplierDataStore.find(id)));
                addSupplierToSupplierNames(supplierDataStore.find(id).getName());
            }
        }
        return products;
    }

    /**
     * This method will filter product by suppliers and categories
     * and controls the message of the search results.
     *
     * @param request                  https request
     * @param supplierDataStore        stores the suppliers
     * @param productCategoryDataStore stores the categories
     * @param productDataStore         stores the products
     * @return List with the filtered products
     */
    public List<Product> filterProduct(HttpServletRequest request, SupplierDao supplierDataStore,
                                       ProductCategoryDao productCategoryDataStore, ProductDao productDataStore) {

        List<Product> filteredByCategories = filterProductsByGivenCategories(request, productCategoryDataStore, productDataStore);
        List<Product> filteredBySuppliers = filterProductsByGivenSuppliers(request, supplierDataStore, productDataStore);

        List<Product> filteredProduct = new ArrayList<>();

        if (filteredByCategories.isEmpty() && filteredBySuppliers.isEmpty()) {

            if (request.getParameterNames().hasMoreElements()) {
                categoryNames.delete(0, categoryNames.length());
                supplierNames.delete(0, supplierNames.length());
                addCategoryToCategoryNames("No");
            }
            filteredProduct = productDataStore.getAll();
        } else if (filteredByCategories.isEmpty()) {
            filteredProduct.addAll(filteredBySuppliers);
            categoryNames.delete(0, categoryNames.length());
            addCategoryToCategoryNames("Empty");
            if (requestForCategory) {
                categoryNames.delete(0, categoryNames.length());
                addCategoryToCategoryNames("Not Found");
            }
        } else if (filteredBySuppliers.isEmpty()) {
            filteredProduct.addAll(filteredByCategories);
            supplierNames.delete(0, supplierNames.length());
            if (requestForSupplier) {
                addSupplierToSupplierNames("No");
            }
        } else {
            for (Product byCategory : filteredByCategories) {
                for (Product bySupplier : filteredBySuppliers) {
                    if (byCategory == bySupplier) {
                        filteredProduct.add(byCategory);
                    }
                }
                if (filteredProduct.isEmpty()) {
                    filteredProduct = productDataStore.getAll();
                    categoryNames.delete(0, categoryNames.length());
                    supplierNames.delete(0, supplierNames.length());
                    addCategoryToCategoryNames("No");
                }
            }
        }
        return filteredProduct;
    }

    /**
     * Concat category names together with comma
     *
     * @param category category to add new category
     */
    private void addCategoryToCategoryNames(String category) {
        categoryNames.append(category).append(", ");
    }

    /**
     * Concat supplier names together with comma
     *
     * @param supplier supplier to add new supplier
     */
    private void addSupplierToSupplierNames(String supplier) {
        supplierNames.append(supplier).append(", ");
    }
}
