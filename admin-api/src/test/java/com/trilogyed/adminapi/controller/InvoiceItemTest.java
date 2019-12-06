package com.trilogyed.adminapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.InvoiceItem;
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

import java.math.BigDecimal;
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
@WebMvcTest(InvoiceItemController.class)
public class InvoiceItemTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceLayer service;

    private static final BigDecimal UNIT_PRICE = new BigDecimal(3.50);
    private static final InvoiceItem InvoiceItem_NO_ID = new InvoiceItem(1,1,3, UNIT_PRICE);
    private static final InvoiceItem InvoiceItem_ID = new InvoiceItem(1, 1,1,3, UNIT_PRICE);
    private static final List<InvoiceItem> InvoiceItem_LIST = new ArrayList<>(Arrays.asList(InvoiceItem_ID));
    private static final InvoiceItem InvoiceItem_UPDATED = new InvoiceItem(1, 1,1,7, UNIT_PRICE);
    private static final InvoiceItem InvoiceItem_BAD_UPDATE = new InvoiceItem(7,1,1,3, UNIT_PRICE);
    private static final String SUCCESS = "Success";
    private static final String FAIL = "Fail";

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUpMock() { //might need to be changed to fit with jpa methods
        when(service.saveInvoiceItem(InvoiceItem_NO_ID)).thenReturn(InvoiceItem_ID);
        when(service.getInvoiceItem(1)).thenReturn(InvoiceItem_ID);
        when(service.getAllInvoiceItems()).thenReturn(InvoiceItem_LIST);

        doThrow(new IdNotFound("bad thing")).when(service).updateInvoiceItem(InvoiceItem_BAD_UPDATE);
        //success and failure messages sent from service layer if applicable
        //when(service.updateInvoiceItem(InvoiceItem_UPDATED)).thenReturn("Update: "+ SUCCESS);
        //when(service.deleteInvoiceItem(1)).thenReturn("Delete: " + SUCCESS);
        //when(service.updateInvoiceItem(InvoiceItem_BAD_UPDATE)).thenReturn("Update: "+ FAIL);
        //when(service.deleteInvoiceItem(1)).thenReturn("Delete: " + FAIL);

        //exceptions
        //when(service.updateInvoiceItem(InvoiceItem_BAD_UPDATE)).thenThrow(new IdNotFound("bad thing"));
        //when(service.deleteInvoiceItem(7)).thenThrow(new IdNotFound("bad thing"));        
    }

    @Test
    public void saveInvoiceItem() throws Exception {
        String input_json = mapper.writeValueAsString(InvoiceItem_NO_ID);
        String output_json = mapper.writeValueAsString(InvoiceItem_ID);

        mvc.perform(post("/invoiceItems")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getInvoiceItem() throws Exception {
        String output_json = mapper.writeValueAsString(InvoiceItem_ID);

        mvc.perform(get("/invoiceItems/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getAllInvoiceItems() throws Exception {
        String output_json = mapper.writeValueAsString(InvoiceItem_LIST);

        mvc.perform(get("/invoiceItems"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void updateInvoiceItem() throws Exception {
        String input_json = mapper.writeValueAsString(InvoiceItem_UPDATED);

        mvc.perform(put("/invoiceItems")
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
    public void deleteInvoiceItem() throws Exception {
        mvc.perform(delete("/invoiceItems/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    //exception test
    @Test
    public void exceptionTest() throws Exception {
        String input_json = mapper.writeValueAsString(InvoiceItem_BAD_UPDATE);
        mvc.perform(put("/invoiceItems")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()) //or whatever status code you set your exception to be, this is a default value
                .andExpect(content().string(containsString("bad thing")));
    }
}
