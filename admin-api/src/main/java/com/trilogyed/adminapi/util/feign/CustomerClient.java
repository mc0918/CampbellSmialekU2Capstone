package com.trilogyed.adminapi.util.feign;

import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public Customer saveCustomer(@RequestBody Customer o);

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable int id) throws IllegalArgumentException;

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<Customer> getAllCustomers();

    @RequestMapping(value = "/customers", method = RequestMethod.PUT)
    public String updateCustomer(@RequestBody Customer o) throws IdNotFound;

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public String deleteCustomer(@PathVariable int id);
}
