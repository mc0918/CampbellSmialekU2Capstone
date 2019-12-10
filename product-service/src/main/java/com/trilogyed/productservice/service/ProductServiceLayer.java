package com.trilogyed.productservice.service;

//import com.trilogyed.productservice.service.;
//import com.trilogyed.productservice.service.ProductRepository;

import com.trilogyed.productservice.dao.ProductRepository;
import com.trilogyed.productservice.exception.IdNotFound;
import com.trilogyed.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceLayer {
    /*
    YOU MIGHT NEED TO CHANGE THINGS (LIKE METHOD NAMES) TO MATCH WITH JPA
    in which case the methods should be:
        save(o), getOne(id), finadAll(), save(o) bc update is save again, deleteById(id)
    */
    private ProductRepository repository;

    @Autowired
    public ProductServiceLayer(ProductRepository repository) {
        this.repository = repository;
    }

    public Product saveProduct(Product o) {
        return repository.save(o);
    }

    public Product getProduct(int id) throws IdNotFound {
        try{
            return repository.getOne(id);
        } catch(NullPointerException n){
            throw new IdNotFound("bad thing");
        }
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void updateProduct(Product o) throws IdNotFound {
        try{
//            repository.getOne(o.getproduct_id());
            repository.save(o);
        } catch(NullPointerException n){
            throw new IdNotFound("bad thing");
        }
    }

    public void deleteProduct(int id) throws IdNotFound {
        try{
            repository.getOne(id);
            repository.deleteById(id);
        } catch(NullPointerException n){
            throw new IdNotFound("bad thing");
        }
    }

    public void updateInventory(int id, int inventory) throws IdNotFound {
        try{
            Product p = repository.getOne(id);
            p.setInventory(inventory);
            repository.save(p);
        } catch(NullPointerException n){
            throw new IdNotFound("bad thing");
        }
    }
}
