package com.trilogyed.customerservice.controller;

//import com.trilogyed.customerservice.controller.CustomerServiceLayer;
//import com.trilogyed.customerservice.controller.Customer;

import com.trilogyed.customerservice.exception.IdNotFound;
import com.trilogyed.customerservice.model.Customer;
import com.trilogyed.customerservice.service.CustomerServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CacheConfig(cacheNames = {"customers"})
public class CustomerController {

    @Autowired
    private CustomerServiceLayer service;

    @CachePut(key = "#result.getcustomer_id()")
    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Customer saveCustomer(@RequestBody Customer o) {
        return service.saveCustomer(o);
    }

    @Cacheable
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomer(@PathVariable int id) throws IdNotFound {
        try {
            return service.getCustomer(id);
        } catch (IdNotFound n) {
            throw new IdNotFound("bad thing");
        }
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @CacheEvict(key = "#o.getcustomer_id()")
    @RequestMapping(value = "/customers", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String updateCustomer(@RequestBody Customer o) throws IdNotFound {
        try {
            service.updateCustomer(o);
            return "Update: Successful";
        } catch (IdNotFound e) {
            throw new IdNotFound("bad thing");
            //return "Update: Fail";
        }
    }

    @CacheEvict
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCustomer(@PathVariable int id) throws IdNotFound {
        try {
            service.deleteCustomer(id);
            return "Delete: Success";
        } catch (Exception e) {
            throw new IdNotFound("bad thing");
            //return "Delete: Fail";
        }
    }
}
