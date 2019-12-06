package com.trilogyed.retailapi.util.feign;

import com.trilogyed.retailapi.exception.IdNotFound;
import com.trilogyed.retailapi.model.Invoice;
import com.trilogyed.retailapi.model.InvoiceItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceClient {
    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public Invoice saveInvoice(@RequestBody Invoice o);

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    public Invoice getInvoice(@PathVariable int id) throws IdNotFound;

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<Invoice> getAllInvoices();

    @RequestMapping(value = "/invoices", method = RequestMethod.PUT)
    public String updateInvoice(@RequestBody Invoice o) throws IdNotFound;

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.DELETE)
    public String deleteInvoice(@PathVariable int id) throws IdNotFound;

    @GetMapping(value = "/invoices/customer/{id}")
    public List<Invoice> getInvoicesByCustomerId(@PathVariable int id) throws IdNotFound;

    @RequestMapping(value = "/invoiceItems", method = RequestMethod.POST)
    public InvoiceItem saveInvoiceItem(@RequestBody InvoiceItem o);

    @RequestMapping(value = "/invoiceItems/{id}", method = RequestMethod.GET)
    public InvoiceItem getInvoiceItem(@PathVariable int id) throws IdNotFound;

    @RequestMapping(value = "/invoiceItems", method = RequestMethod.GET)
    public List<InvoiceItem> getAllInvoiceItems();

    @RequestMapping(value = "/invoiceItems", method = RequestMethod.PUT)
    public String updateInvoiceItem(@RequestBody InvoiceItem o) throws IdNotFound;

    @RequestMapping(value = "/invoiceItems/{id}", method = RequestMethod.DELETE)
    public String deleteInvoiceItem(@PathVariable int id) throws IdNotFound;

    @GetMapping(value = "/invoiceItems/invoice/{id}")
    public List<InvoiceItem> getInvoiceItemsByInvoiceId(@PathVariable int id) throws IdNotFound;
}
