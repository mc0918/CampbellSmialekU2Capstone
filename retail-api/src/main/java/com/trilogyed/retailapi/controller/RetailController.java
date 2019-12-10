package com.trilogyed.retailapi.controller;

import com.trilogyed.retailapi.model.Invoice;
import com.trilogyed.retailapi.model.InvoiceItem;
import com.trilogyed.retailapi.model.Product;
import com.trilogyed.retailapi.service.ServiceLayer;
import com.trilogyed.retailapi.viewmodel.RetailViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class RetailController {

    @Autowired
    private ServiceLayer serviceLayer;

    // Retail Endpoints

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public RetailViewModel submitInvoice(@RequestBody Invoice invoice) {
        return serviceLayer.saveInvoice(invoice);
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public RetailViewModel getInvoiceById(@PathVariable int id) {
        return serviceLayer.getInvoiceById(id);
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<RetailViewModel> getAllInvoices() {
        return serviceLayer.getAllInvoices();
    }

    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<RetailViewModel> getInvoicesByCustomerId(@PathVariable int id) {
        return serviceLayer.getInvoicesByCustomerId(id);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable int id) {
        return serviceLayer.getProduct(id);
    }

    @RequestMapping(value = "/products/invoice/{id}", method = RequestMethod.GET) /* Hmmm......? Necessary? */
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductByInvoiceId(@PathVariable int id) {
        return serviceLayer.getProductsByInvoiceId(id);
    }

    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Integer getLevelUpPointsByCustomerId(int id) {
        return serviceLayer.getLevelUpPointsByCustomerId(id);
    }

    // Admin Endpoints

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return serviceLayer.saveProduct(product);
    }

    // getProductById is above in the Retail endpoints

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return serviceLayer.getAllProducts();
    }

    @RequestMapping(value = "/products", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody Product product) {
        serviceLayer.updateProduct(product);

    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id) {
        serviceLayer.deleteProduct(id);
    }
}
