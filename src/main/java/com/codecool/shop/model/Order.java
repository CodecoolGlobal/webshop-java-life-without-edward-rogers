package com.codecool.shop.model;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Order {

    private int id;
    private String name;
    private String email;
    private int phoneNumber;
    private String billingCountry;
    private String billingCity;
    private int billingZip;
    private String billingAddress;
    private String shippingCountry;
    private String shippingCity;
    private int shippingZip;
    private String shippingAddress;
    ArrayList<Product> products = new ArrayList<>();


    public Order(String inputName, String inputEmail, String inputPhone, String inputCountryB, String inputCityB,
                 String inputZipB, String inputAddressB, String inputCountryS, String inputCityS,
                 String inputZipS, String inputAddressS, ArrayList<Product> products) {
        this.name = inputName;
        this.email = inputEmail;
        this.phoneNumber = parseInt(inputPhone);
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

}
