package com.trilogyed.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.productservice.model.Product;
import com.trilogyed.productservice.service.ProductServiceLayer;
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
    private ProductServiceLayer Product;

    private static final Product Product_NO_ID = new Product( "name", "description", 4.20, 3.50,1);
    private static final Product Product_ID = new Product(1, "name", "description", 4.20, 3.50,1);
    private static final List<Product> Product_LIST = new ArrayList<>(Arrays.asList(Product_ID));
    private static final Product Product_UPDATED = new Product(1, "updated", "description", 4.20, 3.50,1);
    private static final Product Product_BAD_UPDATE = new Product(7, "name", "description", 4.20, 3.50,1);
    private static final String SUCCESS = "Success";
    private static final String FAIL = "Fail";

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUpMock() { //might need to be changed to fit with jpa methods
        when(Product.saveProduct(Product_NO_ID)).thenReturn(Product_ID);
        when(Product.getProduct(1)).thenReturn(Product_ID);
        when(Product.getAllProducts()).thenReturn(Product_LIST);

        //success and failure messages sent from service layer if applicable
        //when(Product.updateProduct(Product_UPDATED)).thenReturn("Update: "+ SUCCESS);
        //when(Product.deleteProduct(1)).thenReturn("Delete: " + SUCCESS);
        //when(Product.updateProduct(Product_BAD_UPDATE)).thenReturn("Update: "+ FAIL);
        //when(Product.deleteProduct(1)).thenReturn("Delete: " + FAIL);

        //exceptions
        //when(Product.updateProduct(Product_BAD_UPDATE)).thenThrow(new IdNotFound("bad thing"));
        //when(Product.deleteProduct(7)).thenThrow(new IdNotFound("bad thing"));        
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

    @Test
    public void updateProductInventory() throws Exception {
        mvc.perform(put("/products/inventory?id=1&inventory=17")
/*                .sessionAttr()
                .param("id", 1)
                .param("inventory",11111)*/
        )
                .andDo(print())
                .andExpect(status().isOk());

        //for things with random or json parsing errors
        //.andExpect(jsonPath("$.id").value("" + REAL_LOCATION.getId()))
        //.andExpect(jsonPath("$.description").value(REAL_LOCATION.getDescription()))
        //.andExpect(jsonPath("$.location").value(REAL_LOCATION.getLocation()));
    }

    //exception test
    /*
    @Test
    public void exceptionTest() throws IdNotFound throws Exception {
        String input_json = mapper.writeValueAsString(Product_BAD_UPDATE);
        mvc.perform(put("/product")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()) //or whatever status code you set your exception to be, this is a default value
                .andExpect(content().string(containsString("bad thing")));
    }
    */
}
