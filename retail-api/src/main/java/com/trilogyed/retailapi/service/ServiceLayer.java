package com.trilogyed.retailapi.service;

import com.trilogyed.retailapi.exception.IdNotFound;
import com.trilogyed.retailapi.exception.ProductOutOfStock;
import com.trilogyed.retailapi.model.*;
import com.trilogyed.retailapi.util.feign.CustomerClient;
import com.trilogyed.retailapi.util.feign.InvoiceClient;
import com.trilogyed.retailapi.util.feign.LevelUpClient;
import com.trilogyed.retailapi.util.feign.ProductClient;
import com.trilogyed.retailapi.viewmodel.RetailViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    //    INVOICE
    public RetailViewModel saveInvoice(Invoice invoice) throws ProductOutOfStock {

        invoice = invoiceClient.saveInvoice(invoice); //this should go after we check to see if customer id/product id/quantity ordered is valid
        List<InvoiceItem> testList = invoice.getInvoiceItems();

        RetailViewModel model = buildRetailViewModel(invoice,
                customerClient.getCustomer(invoice.getCustomerId()),
                levelUpClient.findLevelUpByCustomerId(invoice.getCustomerId())
        );

        //Calculate total cost and points earned
        double totalCost = 0;
        for (InvoiceItem item : invoice.getInvoiceItems()) {
            totalCost += item.getQuantity() * item.getUnitPrice().doubleValue();
        }
        int pointsToAdd = model.getPoints();
        pointsToAdd += (int) ((totalCost / 50) * 10);
        model.setPoints(pointsToAdd);
        //Todo: Submit points to level-up-service through queue


        //Subtract quantity ordered from product database
//        for (InvoiceItem item : invoice.getInvoiceItems()) {
//            for (Product product : model.getProducts()) {
//                if (item.getInventory_id().equals(product.getproduct_id())) {
//                    if (item.getQuantity() > product.getInventory() || item.getQuantity() < 1) {
//                        throw new ProductOutOfStock("You tried to order " + item.getQuantity() + " but only " + product.getInventory() + " of " + product.getproduct_name() + " remain.");
//                    } else {
//                        product.setInventory(product.getInventory() - item.getQuantity());
//                        productClient.updateProduct(product); //If there is no product in DB a 500 error will be thrown
//                    }
//                }
//            }
//        }

        //After all the logic is complete, save the invoice and return the viewmodel
//        invoiceClient.saveInvoice(invoice);
        return model;
    }

    public RetailViewModel getInvoiceById(int id) {
        Invoice invoice = invoiceClient.getInvoice(id);
        RetailViewModel model = buildRetailViewModel(invoice,
                customerClient.getCustomer(invoice.getCustomerId()),
                levelUpClient.findLevelUpByCustomerId(invoice.getCustomerId())
        );
        return model;
    }

    public List<RetailViewModel> getAllInvoices() {
        List<Invoice> invoices = invoiceClient.getAllInvoices();
        List<RetailViewModel> retailViewModels = new ArrayList<>();

        for (Invoice invoice : invoices) {
            RetailViewModel model = buildRetailViewModel(invoice,
                    customerClient.getCustomer(invoice.getCustomerId()),
                    levelUpClient.findLevelUpByCustomerId(invoice.getCustomerId())
            );
            retailViewModels.add(model);
        }

        return retailViewModels;
    }

    public List<RetailViewModel> getInvoicesByCustomerId(int id) {
        List<Invoice> invoices = invoiceClient.getInvoicesByCustomerId(id);
        List<RetailViewModel> retailViewModels = new ArrayList<>();

        for (Invoice invoice : invoices) {
            RetailViewModel model = buildRetailViewModel(invoice,
                    customerClient.getCustomer(invoice.getCustomerId()),
                    levelUpClient.findLevelUpByCustomerId(invoice.getCustomerId())
            );
            retailViewModels.add(model);
        }

        return retailViewModels;
    }

    public void updateInvoice(Invoice invoice) {
        invoiceClient.updateInvoice(invoice);
    }

    public void deleteInvoiceById(int id) {
        invoiceClient.deleteInvoice(id);
    }

    //    PRODUCT
    public Product saveProduct(Product o) {
        return productClient.saveProduct(o);
    }

    public Product getProduct(int id) throws IdNotFound {
        try {
            return productClient.getProduct(id);
        } catch (IdNotFound i) {
            throw new IdNotFound("bad thing");
        }
    }

    public List<Product> getAllProducts() {
        return productClient.getAllProducts();
    }

    public List<Product> getProductsByInvoiceId(int id) {

        List<Product> products = new ArrayList<>();
        Invoice invoice = invoiceClient.getInvoice(id);
        invoice.getInvoiceItems()
                .stream().forEach(invoiceItem -> productClient.getAllProducts()
                .stream().forEach(product -> {
                            if (product.getproduct_id().equals(invoiceItem.getInventory_id())) {
                                products.add(product);
                            }
                        }
                )
        );
        return products;
    }

    public void updateProduct(Product o) throws IdNotFound {
        try {
            productClient.getProduct(o.getproduct_id());
            productClient.updateProduct(o);
        } catch (IdNotFound i) {
            throw new IdNotFound("bad thing");
        }
    }

    public void deleteProduct(int id) throws IdNotFound {
        try {
            productClient.getProduct(id);
            productClient.deleteProduct(id);
        } catch (IdNotFound i) {
            throw new IdNotFound("bad thing");
        }
    }

    //    LEVEL UP
    public Integer getLevelUpPointsByCustomerId(int id) {
        LevelUp levelUp = levelUpClient.findLevelUpByCustomerId(id);
        return levelUp.getPoints();
    }

    public RetailViewModel buildRetailViewModel(Invoice invoice, Customer customer, LevelUp levelUp) {
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
//        model.setProducts(products);

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
