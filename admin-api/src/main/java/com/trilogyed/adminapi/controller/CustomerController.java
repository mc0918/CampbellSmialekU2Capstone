package com.trilogyed.adminapi.controller;

//import com.trilogyed.adminapi.controller.ServiceLayer;
//import com.trilogyed.adminapi.controller.Customer;

import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.Customer;
import com.trilogyed.adminapi.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private ServiceLayer service;

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Customer saveCustomer(@RequestBody Customer o) {
        return service.saveCustomer(o);
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomer(@PathVariable int id) throws IllegalArgumentException {
        try {
            return service.getCustomer(id);
        } catch (NullPointerException n) {
            throw new IllegalArgumentException("illegal argument or another exception idk");
        }
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @RequestMapping(value = "/customers", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String updateCustomer(@RequestBody Customer o) throws IdNotFound {
        try {
            service.updateCustomer(o);
            return "Update: Successful";
        } catch (IdNotFound e) {
            throw new IdNotFound("bad thing");
        }
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCustomer(@PathVariable int id) {
        try {
            service.deleteCustomer(id);
            return "Delete: Success";
        } catch (Exception e) {
            return "Delete: Fail";
        }
    }
}
