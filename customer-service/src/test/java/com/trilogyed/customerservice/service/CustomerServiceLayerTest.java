package com.trilogyed.customerservice.service;

import com.trilogyed.customerservice.dao.CustomerRepository;
import com.trilogyed.customerservice.exception.IdNotFound;
import com.trilogyed.customerservice.model.Customer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class CustomerServiceLayerTest {
/*
YOU MIGHT NEED TO CHANGE THINGS (LIKE METHOD NAMES) TO MATCH WITH JPA
in which case the methods should be:
    save(o), getOne(id), finadAll(), save(o) bc update is save again, deleteById(id)
*/

    private static final Customer Customer_NO_ID = new Customer("first name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_ID = new Customer(1,"first name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_UPDATED = new Customer(1,"updated name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_BAD_UPDATE = new Customer(7,"first name", "last name", "street", "city", "zip", "email", "phone");
    private static final List<Customer> Customer_LIST = new ArrayList<>(Arrays.asList(Customer_ID));

    private static final int DNE_ID = 7;

    private CustomerServiceLayer service;
    private CustomerRepository repository;

    @Before
    public void setUp() throws Exception {
        setUpRepositoryMock();
        service = new CustomerServiceLayer(repository);
    }


    //dao or repo tests
    private void setUpRepositoryMock() {
        repository = mock(CustomerRepository.class);

        doReturn(Customer_ID).when(repository).save(Customer_NO_ID);
        doReturn(Customer_ID).when(repository).getOne(1);
        doReturn(Customer_LIST).when(repository).findAll();
        doThrow(new IdNotFound("bad thing")).when(repository).save(Customer_BAD_UPDATE);
    }

    @Test
    public void saveCustomer() {
        Customer actual = service.saveCustomer(Customer_NO_ID);
        assertEquals(Customer_ID, actual);
    }

    @Test
    public void getCustomer() {
        Customer actual = service.getCustomer(1);
        assertEquals(Customer_ID, actual);
    }

    @Test
    public void getAllCustomers() {
        List<Customer> actual = service.getAllCustomers();
        assertEquals(Customer_LIST, actual);
    }
    
    /*@Test
    public void shouldUpdateCustomer() {
       Customer expected = service.updateCustomer(EQUIPMENT_LOCATION_1);
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
        Customer actual = service.saveCustomer(Customer_BAD_UPDATE); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }
}