import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    static ProductCategory productCategory = new ProductCategory("test", "test", "test");
    static Supplier supplier = new Supplier("test", "test");
    static Product product = new Product("test", 1, "USD", "Test description", productCategory, supplier);
    static Product product1 = new Product("test1", 2, "USD", "Test description1", productCategory, supplier);
    static ProductDao productDao = ProductDaoMem.getInstance();

    @BeforeAll
    public static void addProducts(){
        System.out.println("hello");
        productDao.add(product);
        productDao.add(product1);
    }

    @Test
    public void testAddToProduct() {
        System.out.println(productDao.getAll());
        assertEquals(2, productDao.getAll().size());
    }

    @Test
    public void testFindById() {
        Product findNemoProduct = productDao.find(1);
        assertEquals(1, product.getId());
        assertEquals(product, findNemoProduct);
    }

    @Test
    @AfterAll
    public static void testRemoveProduct() {
        productDao.remove(2);
        assertEquals(1, productDao.getAll().size());
    }

    @Test
    public void testForGetAll() {
        assertEquals(2, productDao.getAll().size());
    }

    @Test
    public void testGetProductBySupplier(){
        assertEquals(2, productDao.getBy(supplier).size());
    }

    @Test
    public void testGetProductByCategory(){
        assertEquals(2, productDao.getBy(productCategory).size());
    }
}