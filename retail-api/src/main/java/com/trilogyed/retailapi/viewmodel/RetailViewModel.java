package com.trilogyed.retailapi.viewmodel;

import com.trilogyed.retailapi.model.InvoiceItem;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RetailViewModel {
//    Customer Stuff
    private Integer customer_id;
    private String first_name;
    private String last_name;
    private String street;
    private String city;
    private String zip;
    private String email;
    private String phone;

//    Invoice Stuff
    private Integer id;
    private Integer customerId = customer_id; //might not even need this, because duplicate?
    private LocalDate purchaseDate;
    private List<InvoiceItem> invoiceItems;

//    InvoiceItem Stuff
    private int quantity;

//    LevelUp Stuff
    private Integer levelUpId;
    private Integer points;
    private LocalDate memberDate;

//    Product Stuff
    private Integer product_id;
    private String product_name;
    private String product_description;
    private double list_price;
    private double unit_cost;
    private int inventory;
}
