package com.trilogyed.retailapi.service;

import com.trilogyed.retailapi.model.*;
import com.trilogyed.retailapi.util.feign.CustomerClient;
import com.trilogyed.retailapi.util.feign.InvoiceClient;
import com.trilogyed.retailapi.util.feign.LevelUpClient;
import com.trilogyed.retailapi.util.feign.ProductClient;
import com.trilogyed.retailapi.viewmodel.RetailViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceLayer {
    private CustomerClient customerClient;
    private InvoiceClient invoiceClient;
    private LevelUpClient levelUpClient;
    private ProductClient productClient;

    @Autowired
    public ServiceLayer(CustomerClient customerClient, InvoiceClient invoiceClient, LevelUpClient levelUpClient, ProductClient productClient) {
        this.customerClient = customerClient;
        this.invoiceClient = invoiceClient;
        this.levelUpClient = levelUpClient;
        this.productClient = productClient;
    }

    public Invoice saveInvoice(Invoice invoice) {

            RetailViewModel model = buildRetailViewModel(invoice,
                customerClient.getCustomer(invoice.getCustomerId()),
                levelUpClient.findLevelUpsByCustomerId(invoice.getCustomerId()),
                productClient.getProduct(invoice.getInvoiceItems().get(0).getInventory_id()));



    }

    public RetailViewModel buildRetailViewModel(Invoice invoice, Customer customer, LevelUp levelUp, Product product) {
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
        model.setProduct_id(product.getproduct_id());
        model.setProduct_name(product.getproduct_name());
        model.setProduct_description(product.getproduct_description());
        model.setList_price(product.getlist_price());
        model.setUnit_cost(product.getunit_cost());
        model.setInventory(product.getInventory());

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
