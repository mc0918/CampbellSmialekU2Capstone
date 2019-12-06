package com.trilogyed.adminapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.Product;
import com.trilogyed.adminapi.service.ServiceLayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceLayer service;

    private static final Product Product_NO_ID = new Product("name", "desc", 4.20, 3.50);
    private static final Product Product_ID = new Product(1, "name", "desc", 4.20, 3.50);
    private static final List<Product> Product_LIST = new ArrayList<>(Arrays.asList(Product_ID));
    private static final Product Product_UPDATED = new Product(1, "name", "desc", 4.20, 3.50);
    private static final Product Product_BAD_UPDATE = new Product(7, "name", "desc", 4.20, 3.50);
    private static final String SUCCESS = "Success";
    private static final String FAIL = "Fail";

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUpMock() { //might need to be changed to fit with jpa methods
        when(service.saveProduct(Product_NO_ID)).thenReturn(Product_ID);
        when(service.getProduct(1)).thenReturn(Product_ID);
        when(service.getAllProducts()).thenReturn(Product_LIST);

        doThrow(new IdNotFound("bad thing")).when(service).updateProduct(Product_BAD_UPDATE);

        //success and failure messages sent from service layer if applicable
        //when(service.updateProduct(Product_UPDATED)).thenReturn("Update: "+ SUCCESS);
        //when(service.deleteProduct(1)).thenReturn("Delete: " + SUCCESS);
        //when(service.updateProduct(Product_BAD_UPDATE)).thenReturn("Update: "+ FAIL);
        //when(service.deleteProduct(1)).thenReturn("Delete: " + FAIL);

        //exceptions
        //when(service.updateProduct(Product_BAD_UPDATE)).thenThrow(new IdNotFound("bad thing"));
        //when(service.deleteProduct(7)).thenThrow(new IdNotFound("bad thing"));        
    }

    @Test
    public void saveProduct() throws Exception {
        String input_json = mapper.writeValueAsString(Product_NO_ID);
        String output_json = mapper.writeValueAsString(Product_ID);

        mvc.perform(post("/products")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getProduct() throws Exception {
        String output_json = mapper.writeValueAsString(Product_ID);

        mvc.perform(get("/products/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getAllProducts() throws Exception {
        String output_json = mapper.writeValueAsString(Product_LIST);

        mvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void updateProduct() throws Exception {
        String input_json = mapper.writeValueAsString(Product_UPDATED);

        mvc.perform(put("/products")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        //for things with random or json parsing errors
        //.andExpect(jsonPath("$.id").value("" + REAL_LOCATION.getId()))
        //.andExpect(jsonPath("$.description").value(REAL_LOCATION.getDescription()))
        //.andExpect(jsonPath("$.location").value(REAL_LOCATION.getLocation()));
    }

    @Test
    public void deleteProduct() throws Exception {
        mvc.perform(delete("/products/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    //exception test
    @Test
    public void exceptionTest() throws Exception {
        String input_json = mapper.writeValueAsString(Product_BAD_UPDATE);
        mvc.perform(put("/products")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()) //or whatever status code you set your exception to be, this is a default value
                .andExpect(content().string(containsString("bad thing")));
    }
}
