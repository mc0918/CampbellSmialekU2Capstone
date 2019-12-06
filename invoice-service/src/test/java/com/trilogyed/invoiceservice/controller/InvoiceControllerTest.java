package com.trilogyed.invoiceservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.invoiceservice.exceptions.IdNotFound;
import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.model.InvoiceItem;
import com.trilogyed.invoiceservice.repository.InvoiceItemRepository;
import com.trilogyed.invoiceservice.repository.InvoiceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @MockBean
    private InvoiceItemRepository itemRepository;

    private ObjectMapper mapper = new ObjectMapper();
//    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private static final LocalDate DATE_OBJECT = LocalDate.now();
    //    private static String DATE = format.format(DATE_OBJECT);

    private static final InvoiceItem INVOICE_ITEM_1_TO_SAVE = new InvoiceItem(1,1, 10, new BigDecimal("20.99"));
    private static final InvoiceItem INVOICE_ITEM_1_SAVED = new InvoiceItem(1, 1, 1, 10, new BigDecimal("20.99"));

    private static final Invoice INVOICE_1_TO_SAVE = new Invoice(1, DATE_OBJECT, new ArrayList<InvoiceItem>());
    private static final Invoice INVOICE_1_SAVED = new Invoice(1, 1, DATE_OBJECT, new ArrayList<InvoiceItem>(Arrays.asList(INVOICE_ITEM_1_SAVED)));

    private static final Integer BAD_ID = 55;

    @Before
    public void setUp() throws Exception {
        when(invoiceRepository.save(INVOICE_1_TO_SAVE)).thenReturn(INVOICE_1_SAVED);
        when(invoiceRepository.findById(1)).thenReturn(java.util.Optional.of(INVOICE_1_SAVED));
        when(invoiceRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(INVOICE_1_SAVED)));
        when(invoiceRepository.findInvoicesByCustomerId(1)).thenReturn(new ArrayList<>(Arrays.asList(INVOICE_1_SAVED)));
        when(invoiceRepository.findById(BAD_ID)).thenThrow(new IdNotFound("Cannot find invoice id " + BAD_ID));
        when(invoiceRepository.findInvoicesByCustomerId(BAD_ID)).thenThrow(new IdNotFound("Cannot find customer id " + BAD_ID));

        when(itemRepository.save(INVOICE_ITEM_1_TO_SAVE)).thenReturn(INVOICE_ITEM_1_SAVED);
        when(itemRepository.findById(1)).thenReturn(java.util.Optional.of(INVOICE_ITEM_1_SAVED));
        when(itemRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(INVOICE_ITEM_1_SAVED)));
        when(itemRepository.findAllByInvoiceId(1)).thenReturn(new ArrayList<>(Arrays.asList(INVOICE_ITEM_1_SAVED)));
        when(itemRepository.findById(BAD_ID)).thenThrow(new IdNotFound("Cannot find invoice item id " + BAD_ID));
        when(itemRepository.findAllByInvoiceId(BAD_ID)).thenThrow(new IdNotFound("Cannot find invoice id " + BAD_ID));

    }

    @Test
    public void submitInvoice() throws Exception {
        String inputJson = mapper.writeValueAsString(INVOICE_1_TO_SAVE);
        String outputJson = mapper.writeValueAsString(INVOICE_1_SAVED);

        mockMvc.perform(post("/invoices")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getInvoiceById() throws Exception {
        String outputJson = mapper.writeValueAsString(Optional.of(INVOICE_1_SAVED).get());

        mockMvc.perform(get("/invoices/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getAllInvoices() throws Exception {
        String outputJson = mapper.writeValueAsString(new ArrayList<Invoice>(Arrays.asList(INVOICE_1_SAVED)));
        mockMvc.perform(get("/invoices"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getInvoicesByCustomerId() throws Exception {
        String outputJson = mapper.writeValueAsString(new ArrayList<Invoice>(Arrays.asList(INVOICE_1_SAVED)));
        mockMvc.perform(get("/invoices"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void updateInvoice() throws Exception {
        String inputJson = mapper.writeValueAsString(INVOICE_1_SAVED);
        mockMvc.perform(put("/invoices")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteInvoice() throws Exception {
        mockMvc.perform(delete("/invoices/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void submitInvoiceItem() throws Exception {
        String inputJson = mapper.writeValueAsString(INVOICE_ITEM_1_TO_SAVE);
        String outputJson = mapper.writeValueAsString(INVOICE_ITEM_1_SAVED);

        mockMvc.perform(post("/invoiceItems")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getInvoiceItemById() throws Exception {
        String outputJson = mapper.writeValueAsString(INVOICE_ITEM_1_SAVED);

        mockMvc.perform(get("/invoiceItems/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getAllInvoiceItems() throws Exception {
        String outputJson = mapper.writeValueAsString(new ArrayList<>(Arrays.asList(INVOICE_ITEM_1_SAVED)));

        mockMvc.perform(get("/invoiceItems"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getInvoiceItemsByInvoiceId() throws Exception {
        String outputJson = mapper.writeValueAsString(new ArrayList<>(Arrays.asList(INVOICE_ITEM_1_SAVED)));

        mockMvc.perform(get("/invoiceItems/invoice/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void updateInvoiceItem() throws Exception {
        String inputJson = mapper.writeValueAsString(INVOICE_ITEM_1_SAVED);

        mockMvc.perform(put("/invoiceItems")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteInvoiceItem() throws Exception {
        mockMvc.perform(delete("/invoiceItems/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void throwIdNotFoundExceptionForBadInvoiceId() throws Exception {
        mockMvc.perform(get("/invoices/{id}", BAD_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Cannot find invoice id")));
    }

    @Test
    public void shouldThrowIdNotFoundExceptionForBadCustomerId() throws Exception {
        mockMvc.perform(get("/invoices/customer/{id}", BAD_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Cannot find customer id")));
    }

    @Test
    public void shouldThrowIdNotFoundExceptionForBadInvoiceItemId() throws Exception {
        mockMvc.perform(get("/invoiceItems/{id}", BAD_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Cannot find invoice item id")));
    }

    @Test
    public void shouldThrowIdNotFoundExceptionForBadInvoiceIdInInvoiceItemController() throws Exception {
        mockMvc.perform(get("/invoiceItems/invoice/{id}", BAD_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Cannot find invoice id")));
    }
}