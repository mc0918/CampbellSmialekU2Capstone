package com.trilogyed.invoiceservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.annotations.Proxy;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity(name = "Invoice") //just in case we have some other "Invoice" entity in the future, this defines the name
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "invoice")
@Proxy(lazy=false)
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer customerId;

    @NotNull
    @JsonSerialize (using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //JPA2 thing: removes an invoice item AUTOMATICALLY when the invoice id it's associated with is deleted
    private List<InvoiceItem> invoiceItems;

    public Invoice(){}

    public Invoice(Integer customerId, @NotNull LocalDate purchaseDate, List<InvoiceItem> invoiceItems) {
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
        this.invoiceItems = invoiceItems;
    }

    public Invoice(Integer id, Integer customerId, @NotNull LocalDate purchaseDate, List<InvoiceItem> invoiceItems) {
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
                Objects.equals(customerId, invoice.customerId) &&
                Objects.equals(purchaseDate, invoice.purchaseDate) &&
                Objects.equals(invoiceItems, invoice.invoiceItems);
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
//                ", invoiceItems=" + invoiceItems +
                '}';
    }
}
