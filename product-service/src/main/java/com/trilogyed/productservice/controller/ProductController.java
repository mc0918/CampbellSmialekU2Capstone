package com.trilogyed.productservice.controller;

//import com.trilogyed.productservice.controller.ProductServiceLayer;
//import com.trilogyed.productservice.controller.Product;

import com.trilogyed.productservice.exception.IdNotFound;
import com.trilogyed.productservice.model.Product;
import com.trilogyed.productservice.service.ProductServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CacheConfig(cacheNames = {"products"})
public class ProductController {

    @Autowired
    private ProductServiceLayer service;

    @CachePut(key = "#result.getproduct_id()")
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product o) {
        return service.saveProduct(o);
    }

    @Cacheable
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Product getProduct(@PathVariable int id) throws IllegalArgumentException {
        try {
            return service.getProduct(id);
        } catch (NullPointerException n) {
            throw new IllegalArgumentException("illegal argument or another exception idk");
        }
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }


    @CacheEvict(key = "#o.getproduct_id()")
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

    @CacheEvict
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteProduct(@PathVariable int id) throws IdNotFound {
        try {
            service.deleteProduct(id);
            return "Delete: Success";
        } catch (IdNotFound e) {
            throw new IdNotFound("bad thing");
            //return "Delete: Fail";
        }
    }

    @CacheEvict(key = "#id")
    @RequestMapping(value = "/products/inventory", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String updateInventory(@RequestParam(required = true) int id, @RequestParam int inventory) throws IdNotFound {
        try {
            service.updateInventory(id, inventory);
            return "Update: Successful";
        } catch (IdNotFound e) {
            throw new IdNotFound("bad thing");
        }
    }
}
