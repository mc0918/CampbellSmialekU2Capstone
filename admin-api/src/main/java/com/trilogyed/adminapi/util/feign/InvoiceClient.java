package com.trilogyed.adminapi.util.feign;

import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.Invoice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "invoices-service")
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
}
