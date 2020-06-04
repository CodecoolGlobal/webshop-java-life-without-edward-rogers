package testJDBC;

import com.codecool.shop.config.ConnectDB;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.jdbc.SupplierDaoJDBC;
import com.codecool.shop.model.Supplier;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SupplierDaoJDBCTest {

    static Supplier supplier = new Supplier("test", "test description");
    static Supplier supplier1 = new Supplier("test1", "test description1");
    static SupplierDao supplierDao;

    @BeforeAll
    public static void init(){
        DataSource dataSource = ConnectDB.getInstance();
        supplierDao = SupplierDaoJDBC.getInstance(dataSource);
        supplierDao.add(supplier);
        supplierDao.add(supplier1);
    }

    @Test
    @Order(1)
    void testAdd() {
        assertEquals(2, supplierDao.getAll().size());
    }

    @Test
    @Order(2)
    void testFind() {

        int id = 1;
        supplier.setId(id);
        assertTrue(EqualsBuilder.reflectionEquals(supplier, supplierDao.find(id)));
    }

    @Test
    @Order(3)
    void testGetAll() {
        assertEquals(2, supplierDao.getAll().size());
    }

    @Test
    @Order(4)
    void testRemove() {
        supplierDao.remove(1);
        assertEquals(1, supplierDao.getAll().size());
    }

}