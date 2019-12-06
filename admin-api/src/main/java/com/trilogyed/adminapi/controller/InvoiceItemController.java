package com.trilogyed.adminapi.controller;

//import com.trilogyed.adminapi.controller.ServiceLayer;
//import com.trilogyed.adminapi.controller.InvoiceItem;

import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.InvoiceItem;
import com.trilogyed.adminapi.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceItemController {

    @Autowired
    private ServiceLayer service;

    @RequestMapping(value = "/invoiceItems", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceItem saveInvoiceItem(@RequestBody InvoiceItem o) {
        return service.saveInvoiceItem(o);
    }

    @RequestMapping(value = "/invoiceItems/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public InvoiceItem getInvoiceItem(@PathVariable int id) throws IdNotFound {
        try {
            return service.getInvoiceItem(id);
        } catch (IdNotFound n) {
            throw new IdNotFound("bad thing");
        }
    }

    @RequestMapping(value = "/invoiceItems", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItem> getAllInvoiceItems() {
        return service.getAllInvoiceItems();
    }

    @RequestMapping(value = "/invoiceItems", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String updateInvoiceItem(@RequestBody InvoiceItem o) throws IdNotFound {
        try {
            service.updateInvoiceItem(o);
            return "Update: Successful";
        } catch (IdNotFound e) {
            throw new IdNotFound("bad thing");
        }
    }

    @RequestMapping(value = "/invoiceItems/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteInvoiceItem(@PathVariable int id) throws IdNotFound {
        try {
            service.deleteInvoiceItem(id);
            return "Delete: Success";
        } catch (IdNotFound e) {
            throw new IdNotFound("bad thing");
        }
    }
}
