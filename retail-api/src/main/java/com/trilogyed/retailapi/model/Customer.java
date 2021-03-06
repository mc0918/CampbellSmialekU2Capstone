package com.trilogyed.retailapi.model;

import java.util.Objects;

public class Customer {

    private Integer customer_id;
    private String first_name;
    private String last_name;
    private String street;
    private String city;
    private String zip;
    private String email;
    private String phone;

    public Customer() {
    }

    public Customer( String first_name, String last_name, String street, String city, String zip, String email, String phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.email = email;
        this.phone = phone;
    }

    public Customer(Integer customer_id, String first_name, String last_name, String street, String city, String zip, String email, String phone) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customer_id, customer.customer_id) &&
                Objects.equals(first_name, customer.first_name) &&
                Objects.equals(last_name, customer.last_name) &&
                Objects.equals(street, customer.street) &&
                Objects.equals(city, customer.city) &&
                Objects.equals(zip, customer.zip) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(phone, customer.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer_id, first_name, last_name, street, city, zip, email, phone);
    }

    public Integer getcustomer_id() {
        return customer_id;
    }

    public void setcustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getfirst_name() {
        return first_name;
    }

    public void setfirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getlast_name() {
        return last_name;
    }

    public void setlast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getstreet() {
        return street;
    }

    public void setstreet(String street) {
        this.street = street;
    }

    public String getcity() {
        return city;
    }

    public void setcity(String city) {
        this.city = city;
    }

    public String getzip() {
        return zip;
    }

    public void setzip(String zip) {
        this.zip = zip;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }


}
