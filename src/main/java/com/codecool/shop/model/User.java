package com.codecool.shop.model;

public class User {
    private String userName;
    private String hashedPassword;
    private String address;
    private int zip;
    private String country;
    private String city;
    private String email;
    private String phoneNumber;
    private Cart cart;

    public User(String userName, String hashedPassword, String address, int zip, String country,
                String city, String email, String phoneNumber){
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.address = address;
        this.zip = zip;
        this.country = country;
        this.city = city;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getAddress() {
        return address;
    }

    public int getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public Cart getCart() {
        return cart;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
