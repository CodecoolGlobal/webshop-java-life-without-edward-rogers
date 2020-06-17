package testJDBC;

import com.codecool.shop.config.ConnectDB;
import com.codecool.shop.dao.ProductCategoryDao;
import org.apache.commons.lang3.builder.EqualsBuilder;
import com.codecool.shop.dao.database_connection.ProductCategoryDaoJDBC;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductCategoryDaoJDBCTest {

    static ProductCategory productCategory = new ProductCategory("test", "test", "test category");
    static ProductCategory productCategory1 = new ProductCategory("test1", "test", "test category");
    static ProductCategoryDao productCategoryDao;

    @BeforeAll
    public static void init(){

        DataSource dataSource = ConnectDB.getInstance();
        productCategoryDao = ProductCategoryDaoJDBC.getInstance(dataSource);
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
        int id = 2;
        productCategory1.setId(id);
        assertTrue(EqualsBuilder.reflectionEquals(productCategory1, productCategoryDao.find(2)));
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