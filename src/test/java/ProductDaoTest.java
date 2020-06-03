import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDaoTest {

//    DataSource dataSource = ConnectDB.getInstance();
    static ProductCategory productCategory = new ProductCategory("test", "test", "test");
    static Supplier supplier = new Supplier("test", "test");
    static Product product = new Product("test", 1, "USD", "Test description", productCategory, supplier);
    static Product product1 = new Product("test1", 2, "USD", "Test description1", productCategory, supplier);
    static ProductDao productDao = ProductDaoMem.getInstance();


    @BeforeAll
    public static void addProducts(){
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
        Product findNemoProduct = productDao.find(1);
        assertEquals(1, product.getId());
        assertEquals(product, findNemoProduct);
    }

    @Test
    @Order(3)
    public void testForGetAll() {
        assertEquals(2, productDao.getAll().size());
    }

    @Test
    @Order(4)
    public void testGetProductBySupplier(){
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