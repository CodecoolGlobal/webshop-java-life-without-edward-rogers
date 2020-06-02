package com.codecool.shop.model;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class Order {

    private int id;
    private final String name;
    private final String email;
    private final long phoneNumber;
    private final String billingCountry;
    private final String billingCity;
    private final int billingZip;
    private final String billingAddress;
    private final String shippingCountry;
    private final String shippingCity;
    private final int shippingZip;
    private final String shippingAddress;
    ArrayList<Product> products;


    public Order(String inputName, String inputEmail, String inputPhone, String inputCountryB, String inputCityB,
                 String inputZipB, String inputAddressB, String inputCountryS, String inputCityS,
                 String inputZipS, String inputAddressS, ArrayList<Product> products) {
        this.name = inputName;
        this.email = inputEmail;
        this.phoneNumber = parseLong(inputPhone);
        this.billingCountry = inputCountryB;
        this.billingCity = inputCityB;
        this.billingZip = parseInt(inputZipB);
        this.billingAddress = inputAddressB;
        this.shippingCountry = inputCountryS;
        this.shippingCity = inputCityS;
        this.shippingZip = parseInt(inputZipS);
        this.shippingAddress = inputAddressS;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public int getBillingZip() {
        return billingZip;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public int getShippingZip() {
        return shippingZip;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
