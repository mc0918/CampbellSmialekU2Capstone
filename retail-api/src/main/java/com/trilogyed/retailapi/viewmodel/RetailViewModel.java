package com.trilogyed.retailapi.viewmodel;

import com.trilogyed.retailapi.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RetailViewModel {
//    Customer Stuff
    private Integer customer_id;
    private String first_name;
    private String last_name;
    private String street;
    private String city;
    private String zip;
    private String email;
    private String phone;

//    Invoice Stuff
    private Integer id;
    private Integer customerId = customer_id; //might not even need this, because duplicate?
    private LocalDate purchaseDate;
    private List<InvoiceItem> invoiceItems;

//    InvoiceItem Stuff
    private Map<Integer, Integer> quantity;

//    LevelUp Stuff
    private Integer levelUpId;
    private Integer points;
    private LocalDate memberDate;

//    Product Stuff
    private Integer product_id;
    private String product_name;
    private String product_description;
    private double list_price;
    private double unit_cost;
    private int inventory;

    public RetailViewModel(){}

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Map<Integer, Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(HashMap<Integer, Integer> quantity) {
        this.quantity = quantity;
    }

    public Integer getLevelUpId() {
        return levelUpId;
    }

    public void setLevelUpId(Integer levelUpId) {
        this.levelUpId = levelUpId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDate getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(LocalDate memberDate) {
        this.memberDate = memberDate;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public double getList_price() {
        return list_price;
    }

    public void setList_price(double list_price) {
        this.list_price = list_price;
    }

    public double getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(double unit_cost) {
        this.unit_cost = unit_cost;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetailViewModel that = (RetailViewModel) o;
        return quantity == that.quantity &&
                Double.compare(that.list_price, list_price) == 0 &&
                Double.compare(that.unit_cost, unit_cost) == 0 &&
                inventory == that.inventory &&
                Objects.equals(customer_id, that.customer_id) &&
                Objects.equals(first_name, that.first_name) &&
                Objects.equals(last_name, that.last_name) &&
                Objects.equals(street, that.street) &&
                Objects.equals(city, that.city) &&
                Objects.equals(zip, that.zip) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(id, that.id) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(purchaseDate, that.purchaseDate) &&
                Objects.equals(invoiceItems, that.invoiceItems) &&
                Objects.equals(levelUpId, that.levelUpId) &&
                Objects.equals(points, that.points) &&
                Objects.equals(memberDate, that.memberDate) &&
                Objects.equals(product_id, that.product_id) &&
                Objects.equals(product_name, that.product_name) &&
                Objects.equals(product_description, that.product_description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer_id, first_name, last_name, street, city, zip, email, phone, id, customerId, purchaseDate, invoiceItems, quantity, levelUpId, points, memberDate, product_id, product_name, product_description, list_price, unit_cost, inventory);
    }


}
