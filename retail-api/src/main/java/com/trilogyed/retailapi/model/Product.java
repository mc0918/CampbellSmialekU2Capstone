package com.trilogyed.retailapi.model;

import java.util.Objects;

public class Product {
    private Integer product_id;
    private String product_name;
    private String product_description;
    private double list_price;
    private double unit_cost;
    private int inventory;

    public Product() {
    }

    public Product( String product_name, String product_description, double list_price, double unit_cost, int inventory) {
        this.product_name = product_name;
        this.product_description = product_description;
        this.list_price = list_price;
        this.unit_cost = unit_cost;
        this.inventory = inventory;
    }

    public Product(Integer product_id, String product_name, String product_description, double list_price, double unit_cost, int inventory) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.list_price = list_price;
        this.unit_cost = unit_cost;
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.list_price, list_price) == 0 &&
                Double.compare(product.unit_cost, unit_cost) == 0 &&
                inventory == product.inventory &&
                Objects.equals(product_id, product.product_id) &&
                Objects.equals(product_name, product.product_name) &&
                Objects.equals(product_description, product.product_description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, product_name, product_description, list_price, unit_cost, inventory);
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", product_description='" + product_description + '\'' +
                ", list_price=" + list_price +
                ", unit_cost=" + unit_cost +
                ", inventory=" + inventory +
                '}';
    }

    public Integer getproduct_id() {
        return product_id;
    }

    public void setproduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getproduct_name() {
        return product_name;
    }

    public void setproduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getproduct_description() {
        return product_description;
    }

    public void setproduct_description(String product_description) {
        this.product_description = product_description;
    }

    public double getlist_price() {
        return list_price;
    }

    public void setlist_price(double list_price) {
        this.list_price = list_price;
    }

    public double getunit_cost() {
        return unit_cost;
    }

    public void setunit_cost(double unit_cost) {
        this.unit_cost = unit_cost;
    }

}
