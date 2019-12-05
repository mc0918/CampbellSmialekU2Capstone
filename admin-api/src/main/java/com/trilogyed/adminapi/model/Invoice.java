package com.trilogyed.adminapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "invoice")
public class Invoice {
    /*
    !!!!!!!!!!!!!!!!!!!!!!!
        DO
            NOT
        FORGET
            TO
        GENERATE
            EQUALS
        TOSTRING
            AND 
        HASHCODE
    !!!!!!!!!!!!!!!!!!!!!!!
    */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer invoiceId;
    private Integer invoice_id;
    private int customer_id;
    private LocalDate purchase_date;
    //@OneToMany(mappedBy = note_id, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //private Set<Note> notes;

    public Invoice() {
    }

    public Invoice(Integer invoice_id, int customer_id, LocalDate purchase_date) {
        this.invoice_id = invoice_id;
        this.customer_id = customer_id;
        this.purchase_date = purchase_date;
    }

    public Invoice(Integer InvoiceId, Integer invoice_id, int customer_id, LocalDate purchase_date) {
        this.invoiceId = invoiceId;
        this.invoice_id = invoice_id;
        this.customer_id = customer_id;
        this.purchase_date = purchase_date;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getinvoice_id() {
        return invoice_id;
    }

    public void setinvoice_id(Integer invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getcustomer_id() {
        return customer_id;
    }

    public void setcustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public LocalDate getpurchase_date() {
        return purchase_date;
    }

    public void setpurchase_date(LocalDate purchase_date) {
        this.purchase_date = purchase_date;
    }


}
