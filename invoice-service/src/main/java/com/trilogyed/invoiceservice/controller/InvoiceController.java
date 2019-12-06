package com.trilogyed.invoiceservice.controller;

import com.trilogyed.invoiceservice.exceptions.IdNotFound;
import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.model.InvoiceItem;
import com.trilogyed.invoiceservice.repository.InvoiceItemRepository;
import com.trilogyed.invoiceservice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceItemRepository itemRepository;

    @PostMapping(value = "/invoices")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice submitInvoice(@RequestBody @Valid Invoice invoice) {
        List<InvoiceItem> items = new ArrayList<InvoiceItem>(invoice.getInvoiceItems());
        invoice.setInvoiceItems(new ArrayList<>());

        invoice = invoiceRepository.save(invoice);

        for (InvoiceItem item : items) {
            item.setInvoiceId(invoice.getId());
            itemRepository.save(item);
        }
        invoice.setInvoiceItems(itemRepository.findAllByInvoiceId(invoice.getId()));
        return invoice;
    }

    @GetMapping(value = "/invoices/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoiceById(@PathVariable int id) throws IdNotFound {
        if (invoiceRepository.findById(id).isPresent()) {
            return invoiceRepository.findById(id).get();
        } else {
            throw new IdNotFound("Cannot find invoice id " + id);
        }
    }

    @GetMapping(value = "/invoices")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @GetMapping(value = "/invoices/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getInvoicesByCustomerId(@PathVariable int id) throws IdNotFound {
        try {
            return invoiceRepository.findInvoicesByCustomerId(id);
        } catch (IllegalArgumentException e) {
            throw new IdNotFound("Cannot find customer id " + id);
        }
    }

    @PutMapping(value = "/invoices")
    @ResponseStatus(HttpStatus.OK)
    public void updateInvoice(@RequestBody @Valid Invoice invoice) {
        List<InvoiceItem> items = new ArrayList<>(invoice.getInvoiceItems());

        for (InvoiceItem item : items) {
            item.setInvoiceId(invoice.getId());
            itemRepository.save(item);
        }
        invoice.setInvoiceItems(itemRepository.findAllByInvoiceId(invoice.getId()));
        invoiceRepository.save(invoice);
    }

    @DeleteMapping(value = "/invoices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable int id) {
        if (invoiceRepository.findById(id).isPresent()) {
            invoiceRepository.deleteById(id);
        } else {
            throw new IdNotFound("Cannot find invoice id " + id);
        }
    }

    //    ===========================INVOICE ITEMS======================================
    @PostMapping(value = "/invoiceItems")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceItem submitInvoiceItem(@RequestBody @Valid InvoiceItem item) {
        return itemRepository.save(item);
    }

    @GetMapping(value = "/invoiceItems/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceItem getInvoiceItemById(@PathVariable int id) throws IdNotFound {
        if (itemRepository.findById(id).isPresent()) {
            return itemRepository.findById(id).get();
        } else {
            throw new IdNotFound("Cannot find invoice id " + id);
        }
    }

    @GetMapping(value = "/invoiceItems")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItem> getAllInvoiceItems() {
        return itemRepository.findAll();
    }

    @GetMapping(value = "/invoiceItems/invoice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItem> getInvoiceItemsByInvoiceId(@PathVariable int id) throws IdNotFound {
        try {
            return itemRepository.findAllByInvoiceId(id);
        } catch (IllegalArgumentException e) {
            throw new IdNotFound("Cannot find invoice id " + id);
        }
    }

    @PutMapping(value = "/invoiceItems")
    @ResponseStatus(HttpStatus.OK)
    public void updateInvoiceItem(@RequestBody @Valid InvoiceItem item) {
        itemRepository.save(item);
    }

    @DeleteMapping(value = "/invoiceItems/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoiceItem(@PathVariable int id) {
        if (itemRepository.findById(id).isPresent()) {
            itemRepository.deleteById(id);
        } else {
            throw new IdNotFound("Cannot find invoice item id " + id);
        }
    }
}


