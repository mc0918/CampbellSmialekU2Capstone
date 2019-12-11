package com.trilogyed.retailapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.retailapi.exception.IdNotFound;
import com.trilogyed.retailapi.model.*;
import com.trilogyed.retailapi.service.ServiceLayer;
import com.trilogyed.retailapi.viewmodel.RetailViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RetailController.class)
public class RetailControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceLayer service;

    private ObjectMapper mapper;

    private static final Customer Customer_NO_ID = new Customer("first name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_ID = new Customer(1,"first name", "last name", "street", "city", "zip", "email", "phone");
    private static final List<Customer> Customer_LIST = new ArrayList<>(Arrays.asList(Customer_ID));
    private static final Customer Customer_UPDATED = new Customer(1,"updated name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_BAD_UPDATE = new Customer(7,"first name", "last name", "street", "city", "zip", "email", "phone");
    private static final String SUCCESS = "Success";
    private static final String FAIL = "Fail";

    private static final BigDecimal UNIT_PRICE = new BigDecimal(3.50);
    private static final InvoiceItem InvoiceItem_NO_ID = new InvoiceItem(1,1,3, UNIT_PRICE);
    private static final InvoiceItem InvoiceItem_ID = new InvoiceItem(1, 1,1,3, UNIT_PRICE);
    private static final List<InvoiceItem> InvoiceItem_LIST = new ArrayList<>(Arrays.asList(InvoiceItem_ID));
    private static final InvoiceItem InvoiceItem_UPDATED = new InvoiceItem(1, 1,1,7, UNIT_PRICE);
    private static final InvoiceItem InvoiceItem_BAD_UPDATE = new InvoiceItem(7,1,1,3, UNIT_PRICE);

    private static final LocalDate DATE = LocalDate.of(1212,12,12);
    private static final Invoice Invoice_NO_ID = new Invoice(1, DATE, InvoiceItem_LIST);
    private static final Invoice Invoice_ID = new Invoice(1, 1, DATE, InvoiceItem_LIST);
    private static final List<Invoice> Invoice_LIST = new ArrayList<>(Arrays.asList(Invoice_ID));
    private static final Invoice Invoice_UPDATED = new Invoice(1, 1, DATE, InvoiceItem_LIST);
    private static final Invoice Invoice_BAD_UPDATE = new Invoice(7, 1, DATE, InvoiceItem_LIST);

    private static final LevelUp LevelUp_NO_ID = new LevelUp(1, 10, DATE);
    private static final LevelUp LevelUp_ID = new LevelUp(1, 1, 10, DATE);
    private static final List<LevelUp> LevelUp_LIST = new ArrayList<>(Arrays.asList(LevelUp_ID));
    private static final LevelUp LevelUp_UPDATED = new LevelUp(1, 1, 13, DATE);
    private static final LevelUp LevelUp_BAD_UPDATE = new LevelUp(7, 1, 10, DATE);

    private static final Product Product_NO_ID = new Product("name", "desc", 4.20, 3.50,1);
    private static final Product Product_ID = new Product(1, "name", "desc", 4.20, 3.50,1);
    private static final List<Product> Product_LIST = new ArrayList<>(Arrays.asList(Product_ID));
    private static final Product Product_UPDATED = new Product(1, "name", "desc", 4.20, 3.50,1);
    private static final Product Product_BAD_UPDATE = new Product(7, "name", "desc", 4.20, 3.50,1);

    @Before
    public void setUp() throws Exception {

        when(service.saveInvoice(Invoice_NO_ID)).thenReturn(generateViewModel());

        when(service.getProduct(1)).thenReturn(Product_ID);
        when(service.getAllProducts()).thenReturn(Product_LIST);

        doThrow(new IdNotFound("bad thing")).when(service).updateProduct(Product_BAD_UPDATE);
        doThrow(new IdNotFound("bad thing")).when(service).deleteProduct(7);
        doThrow(new IdNotFound("bad thing")).when(service).getProduct(7);


    }

    @Test
    public void submitInvoice() throws Exception {
        RetailViewModel viewModel = generateViewModel();

        String outputJson = mapper.writeValueAsString(viewModel);
        String inputJson = mapper.writeValueAsString(Invoice_NO_ID);

        mvc.perform(post("/invoices")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void getInvoice() throws Exception {
        String output_json = mapper.writeValueAsString(Invoice_ID);

        mvc.perform(get("/invoices/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getAllInvoices() throws Exception {
        String output_json = mapper.writeValueAsString(Invoice_LIST);

        mvc.perform(get("/invoices"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getInvoicesByCustomerId() throws Exception{
        String output_json = mapper.writeValueAsString(Invoice_LIST);

        mvc.perform(get("/invoices/customer/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));

    }

    @Test
    public void getProduct() throws Exception {
        String output_json = mapper.writeValueAsString(Product_ID);

        mvc.perform(get("/products/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getProductByInvoiceId() throws Exception{
        String outputJson = mapper.writeValueAsString(Product_LIST);

        mvc.perform(get("/products/invoice/{id}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getLevelUpPointsByCustomerId() throws Exception {
        String outputJson = mapper.writeValueAsString(LevelUp_ID);

        mvc.perform(get("/levelup/customer/{id}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void saveProduct() throws Exception {
        String input_json = mapper.writeValueAsString(Product_NO_ID);
        String output_json = mapper.writeValueAsString(Product_ID);

        mvc.perform(post("/products")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getAllProducts() throws Exception {
        String outputJson = mapper.writeValueAsString(Product_LIST);

        mvc.perform(get("/products/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void updateProduct() throws Exception {
        String input_json = mapper.writeValueAsString(Product_UPDATED);

        mvc.perform(put("/products")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        //for things with random or json parsing errors
        //.andExpect(jsonPath("$.id").value("" + REAL_LOCATION.getId()))
        //.andExpect(jsonPath("$.description").value(REAL_LOCATION.getDescription()))
        //.andExpect(jsonPath("$.location").value(REAL_LOCATION.getLocation()));
    }

    @Test
    public void deleteProduct() throws Exception {
        mvc.perform(delete("/products/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private static RetailViewModel generateViewModel(){

        Invoice invoice = Invoice_ID;
        Customer customer = Customer_ID;
        LevelUp levelUp = LevelUp_ID;

        RetailViewModel model = new RetailViewModel();
        List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
        HashMap<Integer, Integer> itemDoubleMap = new HashMap<>();

        //CUSTOMER
        model.setCustomer_id(customer.getcustomer_id()); //from customer model
        model.setFirst_name(customer.getfirst_name());
        model.setLast_name(customer.getlast_name());
        model.setStreet(customer.getstreet());
        model.setCity(customer.getcity());
        model.setZip(customer.getzip());
        model.setEmail(customer.getemail());
        model.setPhone(customer.getphone());

        //PRODUCT
//        List<Product> products = new ArrayList<>();
//        invoiceItems.stream().forEach(invoiceItem -> {
//            products.add(productClient.getProduct(invoiceItem.getInventory_id()));
//        });
        model.setProducts(Product_LIST);

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

        return model;
    }
}