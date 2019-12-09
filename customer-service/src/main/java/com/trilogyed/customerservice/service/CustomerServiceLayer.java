package com.trilogyed.customerservice.service;

//import com.trilogyed.customerservice.service.;
//import com.trilogyed.customerservice.service.CustomerRepository;

import com.trilogyed.customerservice.dao.CustomerRepository;
import com.trilogyed.customerservice.exception.IdNotFound;
import com.trilogyed.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceLayer {
    /*
    YOU MIGHT NEED TO CHANGE THINGS (LIKE METHOD NAMES) TO MATCH WITH JPA
    in which case the methods should be:
        save(o), getOne(id), finadAll(), save(o) bc update is save again, deleteById(id)
    */
    private CustomerRepository repository;

    @Autowired
    public CustomerServiceLayer(CustomerRepository repository) {
        this.repository = repository;
    }


    public Customer saveCustomer(Customer o) {
        return repository.save(o);
    }

    public Customer getCustomer(int id) throws IdNotFound {
        try{
            return repository.getOne(id);
        } catch(NullPointerException n){
            throw new IdNotFound("bad thing");
        }
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public void updateCustomer(Customer o) throws IdNotFound {
        try{
            repository.getOne(o.getcustomer_id());
            repository.save(o);
        } catch(NullPointerException n){
            throw new IdNotFound("bad thing");
        }
    }

    public void deleteCustomer(int id) throws IdNotFound {
        try{
            repository.getOne(id);
            repository.deleteById(id);
        } catch(NullPointerException n){
            throw new IdNotFound("bad thing");
        }
    }
}
