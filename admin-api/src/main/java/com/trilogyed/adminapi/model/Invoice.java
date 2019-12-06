package com.trilogyed.adminapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Invoice {
    private Integer id;
    private Integer customerId;
    @JsonFormat(pattern="dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull
    private LocalDate purchaseDate;
    @NotNull
    private List<InvoiceItem> invoiceItems;

    public Invoice() {
    }

    public Invoice(Integer customerId, @NotNull LocalDate purchaseDate) {
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
        this.invoiceItems = new ArrayList<>();
    }

    public Invoice(Integer id, Integer customerId, @NotNull LocalDate purchaseDate) {
        this.id = id;
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
        this.invoiceItems = new ArrayList<>();
    }

    public Invoice(Integer customerId, @NotNull LocalDate purchaseDate, @NotNull List<InvoiceItem> invoiceItems) {
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
        this.invoiceItems = invoiceItems;
    }

    public Invoice(Integer id, Integer customerId, @NotNull LocalDate purchaseDate, @NotNull List<InvoiceItem> invoiceItems) {
        this.id = id;
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
        this.invoiceItems = invoiceItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) &&
                customerId.equals(invoice.customerId) &&
                purchaseDate.equals(invoice.purchaseDate) &&
                invoiceItems.equals(invoice.invoiceItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, purchaseDate, invoiceItems);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", purchaseDate=" + purchaseDate +
                ", invoiceItems=" + invoiceItems +
                '}';
    }
}