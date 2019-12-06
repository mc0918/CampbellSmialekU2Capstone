package com.trilogyed.adminapi.controller;

//import com.trilogyed.adminapi.controller.ServiceLayer;
//import com.trilogyed.adminapi.controller.Invoice;

import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.Invoice;
import com.trilogyed.adminapi.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    private ServiceLayer service;

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice saveInvoice(@RequestBody Invoice o) {
        return service.saveInvoice(o);
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoice(@PathVariable int id) throws IdNotFound {
        try {
            return service.getInvoice(id);
        } catch (IdNotFound n) {
            throw new IdNotFound("bad thing");
        }
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoices() {
            return service.getAllInvoices();
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String updateInvoice(@RequestBody Invoice o) throws IdNotFound {
        try {
            service.updateInvoice(o);
            return "Update: Successful";
        } catch (IdNotFound e) {
            throw new IdNotFound("bad thing");
        }
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteInvoice(@PathVariable int id) throws IdNotFound {
        try {
            service.deleteInvoice(id);
            return "Delete: Success";
        } catch (IdNotFound e) {
            throw new IdNotFound("bad thing");
        }
    }
}
