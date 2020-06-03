import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SupplierDaoTest {

    static Supplier supplier = new Supplier("test", "test description");
    static Supplier supplier1 = new Supplier("test1", "test description1");
    static SupplierDao supplierDao = SupplierDaoMem.getInstance();

    @BeforeAll
    public static void init(){
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
        assertEquals(supplier, supplierDao.find(1));
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