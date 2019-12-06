package com.trilogyed.retailapi.util.feign;

import com.trilogyed.retailapi.exception.IdNotFound;
import com.trilogyed.retailapi.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public Product saveProduct(@RequestBody Product o);

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable int id) throws IllegalArgumentException;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> getAllProducts();

    @RequestMapping(value = "/products", method = RequestMethod.PUT)
    public String updateProduct(@RequestBody Product o) throws IdNotFound;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public String deleteProduct(@PathVariable int id);
}
