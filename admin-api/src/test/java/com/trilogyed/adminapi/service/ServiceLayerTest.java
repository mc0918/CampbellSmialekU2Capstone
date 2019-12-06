package com.trilogyed.adminapi.service;

import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.*;
import com.trilogyed.adminapi.util.feign.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {
    /*
    YOU MIGHT NEED TO CHANGE THINGS (LIKE METHOD NAMES) TO MATCH WITH JPA
    in which case the methods should be:
        save(o), getOne(id), finadAll(), save(o) bc update is save again, deleteById(id)
    */
    private static final int DNE_ID = 7;
    private static final LocalDate DATE = LocalDate.of(1212, 12, 12);
    private static final LocalDate UPDATED_DATE = LocalDate.of(1313, 1, 13);
    private static final BigDecimal UNIT_PRICE = new BigDecimal(3.50);

    private static final Customer Customer_NO_ID = new Customer("first name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_ID = new Customer(1,"first name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_UPDATED = new Customer(1,"updated name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_BAD_UPDATE = new Customer(DNE_ID,"first name", "last name", "street", "city", "zip", "email", "phone");
    private static final List<Customer> Customer_LIST = new ArrayList<>(Arrays.asList(Customer_ID));

    private static final Invoice Invoice_NO_ID = new Invoice(1, DATE);
    private static final Invoice Invoice_ID = new Invoice(1, 1, DATE);
    private static final Invoice Invoice_UPDATED = new Invoice(1, 1, UPDATED_DATE);
    private static final Invoice Invoice_BAD_UPDATE = new Invoice(DNE_ID, 1, DATE);
    private static final List<Invoice> Invoice_LIST = new ArrayList<>(Arrays.asList(Invoice_ID));

    private static final InvoiceItem InvoiceItem_NO_ID = new InvoiceItem(1,1,1, UNIT_PRICE);
    private static final InvoiceItem InvoiceItem_ID = new InvoiceItem(1,1,1,1, UNIT_PRICE);
    private static final InvoiceItem InvoiceItem_UPDATED = new InvoiceItem(1,1,1,3, UNIT_PRICE);
    private static final InvoiceItem InvoiceItem_BAD_UPDATE = new InvoiceItem(DNE_ID,1,1,1, UNIT_PRICE);
    private static final List<InvoiceItem> InvoiceItem_LIST = new ArrayList<>(Arrays.asList(InvoiceItem_ID));

    private static final LevelUp LevelUp_NO_ID = new LevelUp(1,1,DATE);
    private static final LevelUp LevelUp_ID = new LevelUp(1,1,1, DATE);
    private static final LevelUp LevelUp_UPDATED = new LevelUp(1,1,17, DATE);
    private static final LevelUp LevelUp_BAD_UPDATE = new LevelUp(DNE_ID,1,1, DATE);
    private static final List<LevelUp> LevelUp_LIST = new ArrayList<>(Arrays.asList(LevelUp_ID));

    private static final Product Product_NO_ID = new Product("name", "desc", 4.20, 3.50);
    private static final Product Product_ID = new Product(1,"name", "desc", 4.20, 3.50);
    private static final Product Product_UPDATED = new Product(1,"update", "desc", 4.20, 3.50);
    private static final Product Product_BAD_UPDATE = new Product(DNE_ID,"name", "desc", 4.20, 3.50);
    private static final List<Product> Product_LIST = new ArrayList<>(Arrays.asList(Product_ID));

    private ServiceLayer service;
    private CustomerClient customerClient;
    private InvoiceClient invoiceClient;
    private LevelUpClient levelUpClient;
    private ProductClient productClient;


    @Before
    public void setUp() throws Exception {
        setUpCustomerClientMock();
        setUpInvoiceClientMock();
        setUpInvoiceItemClientMock();
        setUpLevelUpClientMock();
        setUpProductClientMock();

        service = new ServiceLayer(customerClient, invoiceClient, levelUpClient, productClient);
    }

    //feign client tests
    private void setUpCustomerClientMock() {
        customerClient = mock(CustomerClient.class);

        doReturn(Customer_ID).when(customerClient).saveCustomer(Customer_NO_ID);
        doReturn(Customer_ID).when(customerClient).getCustomer(1);
        doReturn(Customer_LIST).when(customerClient).getAllCustomers();

        //exceptions
        doThrow(new IdNotFound("bad thing")).when(customerClient).getCustomer(DNE_ID);
        doThrow(new IdNotFound("bad thing")).when(customerClient).updateCustomer(Customer_BAD_UPDATE);
        doThrow(new IdNotFound("bad thing")).when(customerClient).deleteCustomer(DNE_ID);
    }

    private void setUpInvoiceClientMock() {
        invoiceClient = mock(InvoiceClient.class);

        doReturn(Invoice_ID).when(invoiceClient).saveInvoice(Invoice_NO_ID);
        doReturn(Invoice_ID).when(invoiceClient).getInvoice(1);
        doReturn(Invoice_LIST).when(invoiceClient).getAllInvoices();

        //exceptions
        doThrow(new IdNotFound("bad thing")).when(invoiceClient).getInvoice(DNE_ID);
        doThrow(new IdNotFound("bad thing")).when(invoiceClient).updateInvoice(Invoice_BAD_UPDATE);
        doThrow(new IdNotFound("bad thing")).when(invoiceClient).deleteInvoice(DNE_ID);
    }

    private void setUpInvoiceItemClientMock() {
        //invoiceClient = mock(InvoiceClient.class);

        doReturn(InvoiceItem_ID).when(invoiceClient).saveInvoiceItem(InvoiceItem_NO_ID);
        doReturn(InvoiceItem_ID).when(invoiceClient).getInvoiceItem(1);
        doReturn(InvoiceItem_LIST).when(invoiceClient).getAllInvoiceItems();

        //exceptions
        doThrow(new IdNotFound("bad thing")).when(invoiceClient).getInvoiceItem(DNE_ID);
        doThrow(new IdNotFound("bad thing")).when(invoiceClient).updateInvoiceItem(InvoiceItem_BAD_UPDATE);
        doThrow(new IdNotFound("bad thing")).when(invoiceClient).deleteInvoiceItem(DNE_ID);
    }

    private void setUpLevelUpClientMock() {
        levelUpClient = mock(LevelUpClient.class);

        doReturn(LevelUp_ID).when(levelUpClient).saveLevelUp(LevelUp_NO_ID);
        doReturn(LevelUp_ID).when(levelUpClient).getLevelUp(1);
        doReturn(LevelUp_LIST).when(levelUpClient).getAllLevelUps();

        //exceptions
        doThrow(new IdNotFound("bad thing")).when(levelUpClient).getLevelUp(DNE_ID);
        doThrow(new IdNotFound("bad thing")).when(levelUpClient).updateLevelUp(LevelUp_BAD_UPDATE);
        doThrow(new IdNotFound("bad thing")).when(levelUpClient).deleteLevelUp(DNE_ID);     }

    private void setUpProductClientMock() {
        productClient = mock(ProductClient.class);

        doReturn(Product_ID).when(productClient).saveProduct(Product_NO_ID);
        doReturn(Product_ID).when(productClient).getProduct(1);
        doReturn(Product_LIST).when(productClient).getAllProducts();

        //exceptions
        doThrow(new IdNotFound("bad thing")).when(productClient).getProduct(DNE_ID);
        doThrow(new IdNotFound("bad thing")).when(productClient).updateProduct(Product_BAD_UPDATE);
        doThrow(new IdNotFound("bad thing")).when(productClient).deleteProduct(DNE_ID);     }

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

    @Test
    public void saveInvoice() {
        Invoice actual = service.saveInvoice(Invoice_NO_ID);
        assertEquals(Invoice_ID, actual);
    }

    @Test
    public void getInvoice() {
        Invoice actual = service.getInvoice(1);
        assertEquals(Invoice_ID, actual);
    }

    @Test
    public void getAllInvoices() {
        List<Invoice> actual = service.getAllInvoices();
        assertEquals(Invoice_LIST, actual);
    }

    @Test
    public void saveInvoiceItem() {
        InvoiceItem actual = service.saveInvoiceItem(InvoiceItem_NO_ID);
        assertEquals(InvoiceItem_ID, actual);
    }

    @Test
    public void getInvoiceItem() {
        InvoiceItem actual = service.getInvoiceItem(1);
        assertEquals(InvoiceItem_ID, actual);
    }

    @Test
    public void getAllInvoiceItems() {
        List<InvoiceItem> actual = service.getAllInvoiceItems();
        assertEquals(InvoiceItem_LIST, actual);
    }

    @Test
    public void saveLevelUp() {
        LevelUp actual = service.saveLevelUp(LevelUp_NO_ID);
        assertEquals(LevelUp_ID, actual);
    }

    @Test
    public void getLevelUp() {
        LevelUp actual = service.getLevelUp(1);
        assertEquals(LevelUp_ID, actual);
    }

    @Test
    public void getAllLevelUps() {
        List<LevelUp> actual = service.getAllLevelUps();
        assertEquals(LevelUp_LIST, actual);
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

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundCustomerUpdate() {
        service.updateCustomer(Customer_BAD_UPDATE); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundInvoiceUpdate() {
        service.updateInvoice(Invoice_BAD_UPDATE); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundInvoiceItemUpdate() {
        service.updateInvoiceItem(InvoiceItem_BAD_UPDATE); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundLevelUpUpdate() {
        service.updateLevelUp(LevelUp_BAD_UPDATE); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundProductUpdate() {
        service.updateProduct(Product_BAD_UPDATE); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundCustomerDelete() {
        service.deleteCustomer(DNE_ID); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundInvoiceDelete() {
        service.deleteInvoice(DNE_ID); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundInvoiceItemDelete() {
        service.deleteInvoiceItem(DNE_ID); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundLevelUpDelete() {
        service.deleteLevelUp(DNE_ID); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundProductDelete() {
        service.deleteProduct(DNE_ID); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundCustomerGet() {
        service.getCustomer(DNE_ID); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundInvoiceGet() {
        service.getInvoice(DNE_ID); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundInvoiceItemGet() {
        service.getInvoiceItem(DNE_ID); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundLevelUpGet() {
        service.getLevelUp(DNE_ID); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }

    @Test(expected = IdNotFound.class)
    public void throwsIdNotFoundProductGet() {
        service.getProduct(DNE_ID); //or some other method to test that we anticipate throws an exception

        fail("bad thing"); //or whatever the expected exception message is
    }
}