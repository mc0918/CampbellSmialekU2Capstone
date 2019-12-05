package com.trilogyed.invoiceservice.controller;

import com.trilogyed.invoiceservice.exceptions.IdNotFoundException;
import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.repository.InvoiceRepository;
import com.trilogyed.invoiceservice.viewmodel.InvoiceItemViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @PostMapping(value = "/invoices")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice submitInvoice(@RequestBody @Valid InvoiceItemViewModel viewModel) {
        return null;
    }

    @GetMapping(value = "/invoices/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoiceById(@PathVariable int id) throws IdNotFoundException {
        return null;
    }

    @GetMapping(value = "/invoices")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoices() {
        return null;
    }

    @GetMapping(value = "/invoices/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getInvoicesByCustomerId(@PathVariable int id) {
        return null;
    }

    @PutMapping(value = "/invoices")
    @ResponseStatus(HttpStatus.OK)
    public void updateInvoice(@RequestBody @Valid InvoiceItemViewModel viewModel) {
        //nada
    }

    @DeleteMapping(value = "/invoice/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable int id) {
        //nada
    }
}


