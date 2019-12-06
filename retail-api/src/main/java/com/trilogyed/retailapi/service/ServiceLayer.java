package com.trilogyed.retailapi.service;

import com.trilogyed.retailapi.util.feign.CustomerClient;
import com.trilogyed.retailapi.util.feign.InvoiceClient;
import com.trilogyed.retailapi.util.feign.LevelUpClient;
import com.trilogyed.retailapi.util.feign.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
