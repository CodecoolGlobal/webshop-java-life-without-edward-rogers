import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductCategoryDaoTest {

    static ProductCategory productCategory = new ProductCategory("test", "test", "test category");
    static ProductCategory productCategory1 = new ProductCategory("test1", "test", "test category");
    static ProductCategoryDao productCategoryDao = ProductCategoryDaoMem.getInstance();

    @BeforeAll
    public static void init(){
        productCategoryDao.add(productCategory);
        productCategoryDao.add(productCategory1);
    }

    @Test
    @Order(1)
    void testAdd() {
        assertEquals(2, productCategoryDao.getAll().size());
    }

    @Test
    @Order(2)
    void testFind() {
        assertEquals(productCategory1, productCategoryDao.find(2));
    }

    @Test
    @Order(3)
    void testGetAll() {
        assertEquals(2, productCategoryDao.getAll().size());
    }

    @Test
    @Order(4)
    void testRemove(){
        productCategoryDao.remove(1);
        assertEquals(1, productCategoryDao.getAll().size());
    }
}