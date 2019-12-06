package com.trilogyed.adminapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "InvoiceItem")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "invoice_item")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer invoiceId;

    private Integer inventory_id;

    private int quantity;

    @NotNull
    private BigDecimal unitPrice;

    public InvoiceItem() {
    }

    public InvoiceItem(Integer invoiceId, Integer inventory_id, int quantity, @NotNull BigDecimal unitPrice) {
        this.invoiceId = invoiceId;
        this.inventory_id = inventory_id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public InvoiceItem(Integer id, Integer invoiceId, Integer inventory_id, int quantity, @NotNull BigDecimal unitPrice) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.inventory_id = inventory_id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", inventory_id=" + inventory_id +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        return quantity == that.quantity &&
                Objects.equals(id, that.id) &&
                invoiceId.equals(that.invoiceId) &&
                inventory_id.equals(that.inventory_id) &&
                unitPrice.equals(that.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceId, inventory_id, quantity, unitPrice);
    }
}
