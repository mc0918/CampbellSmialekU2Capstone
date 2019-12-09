package com.trilogyed.retailapi.service;

import com.trilogyed.retailapi.exception.IdNotFound;
import com.trilogyed.retailapi.model.*;
import com.trilogyed.retailapi.util.feign.CustomerClient;
import com.trilogyed.retailapi.util.feign.InvoiceClient;
import com.trilogyed.retailapi.util.feign.LevelUpClient;
import com.trilogyed.retailapi.util.feign.ProductClient;
import com.trilogyed.retailapi.viewmodel.RetailViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    private static final int DNE_ID = 7;
    private static final LocalDate DATE = LocalDate.of(1212, 12, 12);
    private static final LocalDate UPDATED_DATE = LocalDate.of(1313, 1, 13);
    private static final BigDecimal UNIT_PRICE = new BigDecimal(3.50);

    private static final Customer Customer_NO_ID = new Customer("first name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_ID = new Customer(1,"first name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_UPDATED = new Customer(1,"updated name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_BAD_UPDATE = new Customer(DNE_ID,"first name", "last name", "street", "city", "zip", "email", "phone");
    private static final List<Customer> Customer_LIST = new ArrayList<>(Arrays.asList(Customer_ID));

    private static final InvoiceItem InvoiceItem_NO_ID = new InvoiceItem(1,1,1, UNIT_PRICE);
    private static final InvoiceItem InvoiceItem_ID = new InvoiceItem(1,1,1,1, UNIT_PRICE);
    private static final InvoiceItem InvoiceItem_UPDATED = new InvoiceItem(1,1,1,3, UNIT_PRICE);
    private static final InvoiceItem InvoiceItem_BAD_UPDATE = new InvoiceItem(DNE_ID,1,1,1, UNIT_PRICE);
    private static final List<InvoiceItem> InvoiceItem_LIST = new ArrayList<>(Arrays.asList(InvoiceItem_ID));

    private static final Invoice Invoice_NO_ID = new Invoice(1, DATE, InvoiceItem_LIST);
    private static final Invoice Invoice_ID = new Invoice(1, 1, DATE, InvoiceItem_LIST);
    private static final Invoice Invoice_UPDATED = new Invoice(1, 1, UPDATED_DATE, InvoiceItem_LIST);
    private static final Invoice Invoice_BAD_UPDATE = new Invoice(DNE_ID, 1, DATE, InvoiceItem_LIST);
    private static final List<Invoice> Invoice_LIST = new ArrayList<>(Arrays.asList(Invoice_ID));

    private static final LevelUp LevelUp_NO_ID = new LevelUp(1,1,DATE);
    private static final LevelUp LevelUp_ID = new LevelUp(1,1,1, DATE);
    private static final LevelUp LevelUp_UPDATED = new LevelUp(1,1,17, DATE);
    private static final LevelUp LevelUp_BAD_UPDATE = new LevelUp(DNE_ID,1,1, DATE);
    private static final List<LevelUp> LevelUp_LIST = new ArrayList<>(Arrays.asList(LevelUp_ID));

    private static final Product Product_NO_ID = new Product("name", "desc", 4.20, 3.50,1);
    private static final Product Product_ID = new Product(1,"name", "desc", 4.20, 3.50,1);
    private static final Product Product_UPDATED = new Product(1,"update", "desc", 4.20, 3.50,1);
    private static final Product Product_BAD_UPDATE = new Product(DNE_ID,"name", "desc", 4.20, 3.50,1);
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
    public void shouldSaveInvoice() {
    }

    @Test
    public void shouldBuildRetailViewModel() {
        RetailViewModel model = new RetailViewModel();
        Customer customer = Customer_ID;
        Invoice invoice = Invoice_ID;
        List<InvoiceItem> invoiceItems = InvoiceItem_LIST;
        LevelUp levelUp = LevelUp_ID;
        HashMap<Integer, Integer> itemDoubleMap = new HashMap<>();

        model.setCustomer_id(customer.getcustomer_id()); //from customer model
        model.setFirst_name(customer.getfirst_name());
        model.setLast_name(customer.getlast_name());
        model.setStreet(customer.getstreet());
        model.setCity(customer.getcity());
        model.setZip(customer.getzip());
        model.setEmail(customer.getemail());
        model.setPhone(customer.getphone());

        //PRODUCT
        List<Product> products = new ArrayList<>();
        invoiceItems.stream().forEach(invoiceItem -> {
            products.add(productClient.getProduct(invoiceItem.getInventory_id()));
        });
        model.setProducts(products);

        //LEVELUP
        model.setLevelUpId(levelUp.getLevelUpId());
        model.setPoints(levelUp.getPoints());
        model.setMemberDate(levelUp.getMemberDate());

        //INVOICE
        model.setId(invoice.getId());
        model.setPurchaseDate(invoice.getPurchaseDate());
        model.setInvoiceItems(invoice.getInvoiceItems());

        //INVOICE ITEM
        invoiceItems.stream().forEach(invoiceItem -> {
            itemDoubleMap.put(invoiceItem.getInvoice_item_id(), invoiceItem.getQuantity());
        });
        model.setQuantity(itemDoubleMap);

        assertEquals(model, service.buildRetailViewModel(invoice, customer, levelUp));
    }
}