package com.trilogyed.invoiceservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "InvoiceItem")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "invoice_item")
@Proxy(lazy=false)
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer invoice_item_id;

//    @ManyToOne(cascade = CascadeType.ALL)
    private Integer invoiceId;

    private Integer inventory_id;

    private int quantity;

    @NotNull
    private BigDecimal unitPrice;

    public Integer getInvoice_item_id() {
        return invoice_item_id;
    }

    public void setInvoice_item_id(Integer invoice_item_id) {
        this.invoice_item_id = invoice_item_id;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(Integer inventory_id) {
        this.inventory_id = inventory_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        return invoice_item_id.equals(that.invoice_item_id) &&
                invoiceId.equals(that.invoiceId) &&
                inventory_id.equals(that.inventory_id) &&
                quantity == that.quantity &&
                unitPrice.equals(that.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoice_item_id, invoiceId, inventory_id, quantity, unitPrice);
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + invoice_item_id +
                ", invoiceId=" + invoiceId +
                ", inventory_id=" + inventory_id +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
