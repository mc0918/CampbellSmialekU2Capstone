package com.trilogyed.customerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.customerservice.exception.IdNotFound;
import com.trilogyed.customerservice.model.Customer;
import com.trilogyed.customerservice.service.CustomerServiceLayer;
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
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerServiceLayer service;

    private static final Customer Customer_NO_ID = new Customer("first name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_ID = new Customer(1,"first name", "last name", "street", "city", "zip", "email", "phone");
    private static final List<Customer> Customer_LIST = new ArrayList<>(Arrays.asList(Customer_ID));
    private static final Customer Customer_UPDATED = new Customer(1, "updated name", "last name", "street", "city", "zip", "email", "phone");
    private static final Customer Customer_BAD_UPDATE = new Customer(7,"first name", "last name", "street", "city", "zip", "email", "phone");
    private static final String SUCCESS = "Success";
    private static final String FAIL = "Fail";

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUpMock() { //might need to be changed to fit with jpa methods
        when(service.saveCustomer(Customer_NO_ID)).thenReturn(Customer_ID);
        when(service.getCustomer(1)).thenReturn(Customer_ID);
        when(service.getAllCustomers()).thenReturn(Customer_LIST);

        //success and failure messages sent from service layer if applicable
        //when(service.updateCustomer(Customer_UPDATED)).thenReturn("Update: "+ SUCCESS);
        //when(service.deleteCustomer(1)).thenReturn("Delete: " + SUCCESS);
        //when(service.updateCustomer(Customer_BAD_UPDATE)).thenReturn("Update: "+ FAIL);
        //when(service.deleteCustomer(1)).thenReturn("Delete: " + FAIL);

        //exceptions
        doThrow(new IdNotFound("bad thing")).when(service).updateCustomer(Customer_BAD_UPDATE);
        //when(service.updateCustomer(Customer_BAD_UPDATE)).thenThrow(new IdNotFound("bad thing"));
        //when(service.deleteCustomer(7)).thenThrow(new IdNotFound("bad thing"));        
    }

    @Test
    public void saveCustomer() throws Exception {
        String input_json = mapper.writeValueAsString(Customer_NO_ID);
        String output_json = mapper.writeValueAsString(Customer_ID);

        mvc.perform(post("/customer")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getCustomer() throws Exception {
        String output_json = mapper.writeValueAsString(Customer_ID);

        mvc.perform(get("/customer/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getAllCustomers() throws Exception {
        String output_json = mapper.writeValueAsString(Customer_LIST);

        mvc.perform(get("/customer"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void updateCustomer() throws Exception {
        String input_json = mapper.writeValueAsString(Customer_UPDATED);

        mvc.perform(put("/customer")
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
    public void deleteCustomer() throws Exception {
        mvc.perform(delete("/customer/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    //exception test

    @Test
    public void exceptionTest() throws Exception{ //is the service layer throwing the exception
        String input_json = mapper.writeValueAsString(Customer_BAD_UPDATE);
        mvc.perform(put("/customer")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()) //or whatever status code you set your exception to be, this is a default value
                .andExpect(content().string(containsString("bad thing")));
    }

}
