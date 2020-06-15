package com.codecool.shop.model;

public class User {
    private String userName;
    private String hashedPassword;
    private String address;
    private int zip;
    private String country;
    private String city;
    private Cart cart;

    public User(String userName, String hashedPassword, String address, int zip, String country, String city){
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.address = address;
        this.zip = zip;
        this.country = country;
        this.city = city;
    }
}
