package com.trilogyed.adminapi.service;

//import com.trilogyed.adminapi.service.CustomerClient;
//import com.trilogyed.adminapi.service.;

import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.*;
import com.trilogyed.adminapi.util.feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceLayer {
    /*
    YOU MIGHT NEED TO CHANGE THINGS (LIKE METHOD NAMES) TO MATCH WITH JPA
    in which case the methods should be:
        save(o), getOne(id), findAll(), save(o) bc update is save again, deleteById(id)
    */
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

    public Customer saveCustomer(Customer o) {
        return customerClient.saveCustomer(o);
    }

    public Customer getCustomer(int id) throws IdNotFound {
        try{
            return customerClient.getCustomer(id);
        } catch(IdNotFound n){
            throw new IdNotFound("bad thing");
        }
    }

    public List<Customer> getAllCustomers() {
        return customerClient.getAllCustomers();
    }

    public void updateCustomer(Customer o) throws IdNotFound {
        try{
            customerClient.getCustomer(o.getcustomer_id());
            customerClient.updateCustomer(o);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public void deleteCustomer(int id) throws IdNotFound {
        try{
            customerClient.getCustomer(id);
            customerClient.deleteCustomer(id);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public Invoice saveInvoice(Invoice o) {
        return invoiceClient.saveInvoice(o);
    }

    public Invoice getInvoice(int id) throws IdNotFound {
        try{
            return invoiceClient.getInvoice(id);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public List<Invoice> getAllInvoices() {
        return invoiceClient.getAllInvoices();
    }

    public void updateInvoice(Invoice o) throws IdNotFound {
        try{
            invoiceClient.getInvoice(o.getId());
            invoiceClient.updateInvoice(o);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public void deleteInvoice(int id) throws IdNotFound {
        try{
            invoiceClient.getInvoice(id);
            invoiceClient.deleteInvoice(id);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public InvoiceItem saveInvoiceItem(InvoiceItem o) {
        return invoiceClient.saveInvoiceItem(o);
    }

    public InvoiceItem getInvoiceItem(int id) {
        try{
            return invoiceClient.getInvoiceItem(id);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public List<InvoiceItem> getAllInvoiceItems() {
        return invoiceClient.getAllInvoiceItems();
    }

    public void updateInvoiceItem(InvoiceItem o) throws IdNotFound {
        try{
            invoiceClient.getInvoiceItem(o.getId());
            invoiceClient.updateInvoiceItem(o);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public void deleteInvoiceItem(int id) throws IdNotFound {
        try{
            invoiceClient.getInvoiceItem(id);
            invoiceClient.deleteInvoiceItem(id);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public LevelUp saveLevelUp(LevelUp o) {
        return levelUpClient.saveLevelUp(o);
    }

    public LevelUp getLevelUp(int id) throws IdNotFound {
        try{
            return levelUpClient.getLevelUp(id);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public List<LevelUp> getAllLevelUps() {
        return levelUpClient.getAllLevelUps();
    }

    public void updateLevelUp(LevelUp o) throws IdNotFound {
        try{
            levelUpClient.getLevelUp(o.getlevel_up_id());
            levelUpClient.updateLevelUp(o);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public void deleteLevelUp(int id) throws IdNotFound {
        try{
            levelUpClient.getLevelUp(id);
            levelUpClient.getLevelUp(id);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public Product saveProduct(Product o) {
        return productClient.saveProduct(o);
    }

    public Product getProduct(int id) throws IdNotFound {
        try{
            return productClient.getProduct(id);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public List<Product> getAllProducts() {
        return productClient.getAllProducts();
    }

    public void updateProduct(Product o) throws IdNotFound {
        try{
            productClient.getProduct(o.getproduct_id());
            productClient.updateProduct(o);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public void deleteProduct(int id) throws IdNotFound {
        try{
            productClient.getProduct(id);
            productClient.deleteProduct(id);
        } catch(IdNotFound i){
            throw new IdNotFound("bad thing");
        }
    }

    public void updateInventory(int id, int inventory) throws IdNotFound {
        try{
            productClient.getProduct(id);
            productClient.updateInventory(id, inventory);
        } catch(IdNotFound i ){
            throw new IdNotFound("bad thing");
        }
    }
}
