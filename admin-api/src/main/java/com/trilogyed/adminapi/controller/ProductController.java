package com.trilogyed.adminapi.controller;

//import com.trilogyed.adminapi.controller.ServiceLayer;
//import com.trilogyed.adminapi.controller.Product;

import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.Product;
import com.trilogyed.adminapi.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ServiceLayer service;

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product o) {
        return service.saveProduct(o);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Product getProduct(@PathVariable int id) throws IdNotFound {
        try {
            return service.getProduct(id);
        } catch (NullPointerException n) {
            throw new IdNotFound("illegal argument or another exception idk");
        }
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @RequestMapping(value = "/products", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String updateProduct(@RequestBody Product o) throws IdNotFound {
        try {
            service.updateProduct(o);
            return "Update: Successful";
        } catch (IdNotFound e) {
            throw new IdNotFound("bad thing");
        }
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteProduct(@PathVariable int id) throws IdNotFound {
        try {
            service.deleteProduct(id);
            return "Delete: Success";
        } catch (IdNotFound e) {
            throw new IdNotFound("bad thing");
        }
    }
}
