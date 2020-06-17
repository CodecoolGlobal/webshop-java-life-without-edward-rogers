package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.database_connection.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.database_connection.ProductDaoJDBC;
import com.codecool.shop.dao.database_connection.SupplierDaoJDBC;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class Initializer implements ServletContextListener {
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        dataSource = ConnectDB.getInstance();

        SupplierDao supplierDataStore = SupplierDaoJDBC.getInstance(dataSource);
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance(dataSource);
        ProductDao productDataStore = ProductDaoJDBC.getInstance(dataSource);

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier hp = new Supplier("HP", "Computers");
        supplierDataStore.add(hp);
        Supplier toshiba = new Supplier("Toshiba", "Computers");
        supplierDataStore.add(toshiba);
        Supplier sony = new Supplier("Sony", "Television");
        supplierDataStore.add(sony);
        Supplier philips = new Supplier("Philips", "Razor");
        supplierDataStore.add(philips);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory computer = new ProductCategory("Computer", "Hardware", "A computer, PC in short, you can play on them or even work.");
        productCategoryDataStore.add(computer);
        ProductCategory television = new ProductCategory("Television", "Hardware", "A television, TV in short, is a product that helps you consume information in a passive manner.");
        productCategoryDataStore.add(television);
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "A laptop is basically a computer, but smaller. You can play on it, but if you are a cool guy, you'll mostly code.");
        productCategoryDataStore.add(laptop);
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "Phone, or Telephone or Smartphone is a device that makes it possible to play CandyCrush.");
        productCategoryDataStore.add(phone);


        //setting up products and printing it
        productDataStore.add(new Product("Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("IdeaPad Mix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("ND500", 244, "USD", ". Full-size USB ports. Adjustable kickstand.", tablet, amazon));
        productDataStore.add(new Product("401", 50, "USD", "HP the king of IT", computer, hp));
        productDataStore.add(new Product("0501", 89, "USD", "HP the king of IT", computer, hp));
        productDataStore.add(new Product("laptop", 150, "USD", "HP the best laptops", laptop, hp));
        productDataStore.add(new Product("MXE-SuperBeauty-800", 300, "USD", "Sony for your best choice", television, sony));
        productDataStore.add(new Product("BDNW-510", 300, "USD", "Sony for your best choice", television, sony));
        productDataStore.add(new Product("SexyPant", 200, "USD", "Toshiba computer technics", laptop, toshiba));

    }
}
