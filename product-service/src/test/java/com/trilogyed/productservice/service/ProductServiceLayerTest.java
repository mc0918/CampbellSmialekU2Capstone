package com.trilogyed.productservice.service;

import com.trilogyed.productservice.dao.ProductRepository;
import com.trilogyed.productservice.exception.IdNotFound;
import com.trilogyed.productservice.model.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ProductServiceLayerTest {
/*
YOU MIGHT NEED TO CHANGE THINGS (LIKE METHOD NAMES) TO MATCH WITH JPA
in which case the methods should be:
    save(o), getOne(id), finadAll(), save(o) bc update is save again, deleteById(id)
*/

    private static final Product Product_NO_ID = new Product("name", "description", 4.20, 3.50);
    private static final Product Product_ID = new Product(1, "name", "description", 4.20, 3.50);
    private static final Product Product_UPDATED = new Product(1, "updated", "description", 4.20, 3.50);
    private static final Product Product_BAD_UPDATE = new Product(7,"updated", "description", 4.20, 3.50);
    private static final List<Product> Product_LIST = new ArrayList<>(Arrays.asList(Product_ID));

    private static final int DNE_ID = 7;

    private ProductServiceLayer service;
    private ProductRepository repository;

    @Before
    public void setUp() throws Exception {
        setUpRepositoryMock();
        service = new ProductServiceLayer(repository);
    }


    //dao or repo tests
    private void setUpRepositoryMock() {
        repository = mock(ProductRepository.class);

        doReturn(Product_ID).when(repository).save(Product_NO_ID);
        doReturn(Product_ID).when(repository).getOne(1);
        doReturn(Product_LIST).when(repository).findAll();

        doThrow(new IdNotFound("bad thing")).when(repository).save(Product_BAD_UPDATE);
    }

    @Test
    public void saveProduct() {
        Product actual = service.saveProduct(Product_NO_ID);
        assertEquals(Product_ID, actual);
    }

    @Test
    public void getProduct() {
        Product actual = service.getProduct(1);
        assertEquals(Product_ID, actual);
    }

    @Test
    public void getAllProducts() {
        List<Product> actual = service.getAllProducts();
        assertEquals(Product_LIST, actual);
    }
    
    /*@Test
    public void shouldUpdateProduct() {
       Product expected = service.updateProduct(EQUIPMENT_LOCATION_1);
       assertEquals(expected, actual);
    }*/ //a sample test for update just in case
    ////////////////////////////////////////////////////////////

/*    @Test(expected = NoSuchEquipmentException.class)
    public void shouldThrowExceptionWhenUpdateObjectWithBadId() {
      
        //arrange (all the setup above)
        //act
        Object whatWeGot = service.updateObject(STATIC_FINAL_OBJECT);
        fail("Should have thrown an exception");
    }*/

    //test for custom exception
    @Test(expected = IdNotFound.class)
    public void throwsIdNotFound() {
        Product actual = service.saveProduct(Product_BAD_UPDATE); //or some other method to test that we anticipate throws an exception
        fail("bad thing"); //or whatever the expected exception message is
    }
}