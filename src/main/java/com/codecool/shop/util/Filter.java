package com.codecool.shop.util;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class Filter {

    private ProductDao productDataStore;
    private ProductCategoryDao productCategoryDataStore;
    private SupplierDao supplierDataStore;
    private HttpServletRequest request;
    private boolean requestForCategory = false;
    private boolean requestForSupplier = false;
    private StringBuffer categoryNames = new StringBuffer();
    private StringBuffer supplierNames = new StringBuffer();

    public Filter(ProductDao productDataStore, ProductCategoryDao productCategoryDataStore, SupplierDao supplierDataStore, HttpServletRequest request) {
        this.productDataStore = productDataStore;
        this.productCategoryDataStore = productCategoryDataStore;
        this.supplierDataStore = supplierDataStore;
        this.request = request;
    }

    /**
     * This method will get all products in the given categories and sets the boolean for request true, if there were any.
     *
     * @return the filtered product List
     */
    private List<Product> filterProductsByGivenCategories() {
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
     * @return the filtered product List
     */
    private List<Product> filterProductsByGivenSuppliers() {
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
     * @return List with the filtered products
     */
    public List<Product> filterProduct() {

        List<Product> filteredByCategories = filterProductsByGivenCategories();
        List<Product> filteredBySuppliers = filterProductsByGivenSuppliers();
        List<Product> filteredProduct = new ArrayList<>();

        if (filteredByCategories.isEmpty() && filteredBySuppliers.isEmpty()) {
            filteredProduct = showAllProducts();

        } else if (filteredByCategories.isEmpty()) {
            showProductsBySuppliers(filteredProduct, filteredBySuppliers);

        } else if (filteredBySuppliers.isEmpty()) {
            showProductsByCategories(filteredProduct, filteredByCategories);

        } else {
            filteredProduct = showFinalResultOfSearch(filteredProduct, filteredByCategories, filteredBySuppliers);

        }
        return filteredProduct;
    }


    private List<Product> showFinalResultOfSearch(List<Product> filteredProduct, List<Product> filteredByCategories,
                                                  List<Product> filteredBySuppliers) {
        for (Product byCategory : filteredByCategories) {
            for (Product bySupplier : filteredBySuppliers) {
                if (byCategory == bySupplier) {
                    filteredProduct.add(byCategory);
                }
            }
        }
        if (filteredProduct.isEmpty()) {
            filteredProduct = productDataStore.getAll();
            deleteListsAndAddCategoryName();

        }
        return filteredProduct;
    }

    private void showProductsByCategories(List<Product> filteredProduct, List<Product> filteredByCategories) {
        filteredProduct.addAll(filteredByCategories);
        supplierNames.delete(0, supplierNames.length());
        if (requestForSupplier) {
            addSupplierToSupplierNames("No");
        }
    }

    private void showProductsBySuppliers(List<Product> filteredProduct, List<Product> filteredBySuppliers) {
        filteredProduct.addAll(filteredBySuppliers);
        deleteAndAddCategoryName("Empty");
        if (requestForCategory) {
            deleteAndAddCategoryName("Not found");
        }
    }

    private List<Product> showAllProducts() {
        if (request.getParameterNames().hasMoreElements()) {
            deleteListsAndAddCategoryName();
        }
        return productDataStore.getAll();
    }

    private void deleteAndAddCategoryName(String categoryType) {
        categoryNames.delete(0, categoryNames.length());
        addCategoryToCategoryNames(categoryType);
    }

    private void deleteListsAndAddCategoryName() {
        categoryNames.delete(0, categoryNames.length());
        supplierNames.delete(0, supplierNames.length());
        addCategoryToCategoryNames("No");
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

    public String getSupplierNames(){
        return supplierNames.toString().trim().replaceAll(",$", "");
    }

    public String getCategoryNames(){
        return categoryNames.toString().trim().replaceAll(",$", "");
    }
}
