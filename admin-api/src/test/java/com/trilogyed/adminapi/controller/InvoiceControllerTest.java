package com.trilogyed.adminapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.Invoice;
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

import java.time.LocalDate;
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
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceLayer service;

    private static final LocalDate DATE = LocalDate.of(1212,12,12);
    private static final Invoice Invoice_NO_ID = new Invoice(1, DATE);
    private static final Invoice Invoice_ID = new Invoice(1, 1, DATE);
    private static final List<Invoice> Invoice_LIST = new ArrayList<>(Arrays.asList(Invoice_ID));
    private static final Invoice Invoice_UPDATED = new Invoice(1, 1, DATE);
    private static final Invoice Invoice_BAD_UPDATE = new Invoice(7, 1, DATE);
    private static final String SUCCESS = "Success";
    private static final String FAIL = "Fail";

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUpMock() { //might need to be changed to fit with jpa methods
        when(service.saveInvoice(Invoice_NO_ID)).thenReturn(Invoice_ID);
        when(service.getInvoice(1)).thenReturn(Invoice_ID);
        when(service.getAllInvoices()).thenReturn(Invoice_LIST);

        //success and failure messages sent from service layer if applicable
        //when(service.updateInvoice(Invoice_UPDATED)).thenReturn("Update: "+ SUCCESS);
        //when(service.deleteInvoice(1)).thenReturn("Delete: " + SUCCESS);
        //when(service.updateInvoice(Invoice_BAD_UPDATE)).thenReturn("Update: "+ FAIL);
        //when(service.deleteInvoice(1)).thenReturn("Delete: " + FAIL);

        //exceptions
        doThrow(new IdNotFound("bad thing")).when(service).updateInvoice(Invoice_BAD_UPDATE);
        doThrow(new IdNotFound("bad thing")).when(service).deleteInvoice(7);
    }

    @Test
    public void saveInvoice() throws Exception {
        String input_json = mapper.writeValueAsString(Invoice_NO_ID);
        String output_json = mapper.writeValueAsString(Invoice_ID);

        mvc.perform(post("/invoices")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getInvoice() throws Exception {
        String output_json = mapper.writeValueAsString(Invoice_ID);

        mvc.perform(get("/invoices/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getAllInvoices() throws Exception {
        String output_json = mapper.writeValueAsString(Invoice_LIST);

        mvc.perform(get("/invoices"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void updateInvoice() throws Exception {
        String input_json = mapper.writeValueAsString(Invoice_UPDATED);

        mvc.perform(put("/invoices")
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
    public void deleteInvoice() throws Exception {
        mvc.perform(delete("/invoices/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    //exception test
    @Test
    public void exceptionTest() throws Exception {
        String input_json = mapper.writeValueAsString(Invoice_BAD_UPDATE);
        mvc.perform(put("/invoices")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()) //or whatever status code you set your exception to be, this is a default value
                .andExpect(content().string(containsString("bad thing")));
    }
}
