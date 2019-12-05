package com.trilogyed.adminapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Invoice {
    private Integer id;
    private Integer customerId;
    @NotNull
    private LocalDate purchaseDate;
    @NotNull
    private List<InvoiceItem> invoiceItems;

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
        return id.equals(invoice.id) &&
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