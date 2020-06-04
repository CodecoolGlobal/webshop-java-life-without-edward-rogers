package testJDBC;

import com.codecool.shop.config.ConnectDB;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.jdbc.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.jdbc.ProductDaoJDBC;
import com.codecool.shop.dao.jdbc.SupplierDaoJDBC;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDaoJDBCTest {

    static ProductCategory productCategory = new ProductCategory("test", "test", "test");
    static Supplier supplier = new Supplier("test", "test");
    static Product product = new Product("test", 1, "USD", "Test description", productCategory, supplier);
    static Product product1 = new Product("test1", 2, "USD", "Test description1", productCategory, supplier);
    static ProductDao productDao;
    static SupplierDao supplierDao;
    static ProductCategoryDao productCategoryDao;


    @BeforeAll
    public static void addProducts(){
        DataSource dataSource = ConnectDB.getInstance();
        productDao = ProductDaoJDBC.getInstance(dataSource);
        supplierDao = SupplierDaoJDBC.getInstance(dataSource);
        productCategoryDao = ProductCategoryDaoJDBC.getInstance(dataSource);

        productCategoryDao.add(productCategory);
        supplierDao.add(supplier);
        productDao.add(product);
        productDao.add(product1);
    }

    @Test
    @Order(1)
    public void testAddToProduct() {
        assertEquals(2, productDao.getAll().size());
    }

    @Test
    @Order(2)
    public void testFindById() {
        int id = 1;
        Product foundProduct = productDao.find(id);
        assertEquals(id, foundProduct.getId());
        product.setId(id);
        System.out.println(product);
        System.out.println(foundProduct);
        assertTrue(EqualsBuilder.reflectionEquals(product, productDao.find(id)));
    }

    @Test
    @Order(3)
    public void testForGetAll() {
        assertEquals(2, productDao.getAll().size());
    }

    @Test
    @Order(4)
    public void testGetProductBySupplier(){
        System.out.println(productDao.getBy(supplier));
        assertEquals(2, productDao.getBy(supplier).size());
    }

    @Test
    @Order(5)
    public void testGetProductByCategory(){
        assertEquals(2, productDao.getBy(productCategory).size());
    }

    @Test
    @Order(6)
    public void testRemoveProduct() {
        productDao.remove(2);
        assertEquals(1, productDao.getAll().size());
    }
}
