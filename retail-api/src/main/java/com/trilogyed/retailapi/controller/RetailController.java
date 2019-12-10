package com.trilogyed.retailapi.controller;

import com.trilogyed.retailapi.model.Invoice;
import com.trilogyed.retailapi.model.InvoiceItem;
import com.trilogyed.retailapi.model.LevelUp;
import com.trilogyed.retailapi.model.Product;
import com.trilogyed.retailapi.service.ServiceLayer;
import com.trilogyed.retailapi.util.feign.LevelUpClient;
import com.trilogyed.retailapi.viewmodel.RetailViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RefreshScope
public class RetailController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private LevelUpClient levelUpClient;

    @Autowired
    private ServiceLayer serviceLayer;

    public RetailController(){}

    public RetailController(LevelUpClient client){
        this.levelUpClient = client;
    }

    public RetailController(LevelUpClient client, RabbitTemplate rabbitTemplate) {
        this.levelUpClient = client;
        this.rabbitTemplate = rabbitTemplate;
    }

    private RestTemplate restTemplate;
    public static final String EXCHANGE = "level-up-exchange";
    public static final String ROUTING_KEY = "level-up.add.levelUp.controller";



    // Retail Endpoints

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public RetailViewModel submitInvoice(@RequestBody Invoice invoice) {
        RetailViewModel viewModel = serviceLayer.saveInvoice(invoice);

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(viewModel.getLevelUpId());
        levelUp.setCustomerId(viewModel.getCustomer_id());
        levelUp.setMemberDate(viewModel.getMemberDate());
        levelUp.setPoints(viewModel.getPoints());

        System.out.println("Sending note: " + levelUp.toString());

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, levelUp);

        System.out.println("level up sent :)");

        return viewModel;
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
