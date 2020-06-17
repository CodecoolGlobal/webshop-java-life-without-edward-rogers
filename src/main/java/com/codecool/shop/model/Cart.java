package com.codecool.shop.model;

import java.util.ArrayList;

public class Cart extends BaseModel {

    private int userID;
    private ArrayList<Product> productsInCart = new ArrayList<>();


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * This will return a list which holds the products
     * @return - a cart ArrayList
     */
    public ArrayList<Product> getProductsInCart() {
        return productsInCart;
    }

    /**
     * Set the value of the arrayList with an other one
     * @param newProductsInCart - Another arrayList which holds Products
     */
    public void setProductsInCart(ArrayList<Product> newProductsInCart) {
        productsInCart = newProductsInCart;
    }


    /**
     * Constructor of cart, we set cart's name and description also.
     * @param name - Name of new cart.
     * @param description - Description of new cart.
     */
    public Cart(String name, String description) {
        super(name, description);
    }


    /**
     * Returns the actual size of cart's list.
     * @return Size of cart's list.
     */
    public int getCartListSize(){
        int sum = 0;
        for(Product product: productsInCart ){
            sum += product.getQuantity();
        }
        return sum;
    }

    /**
     * Returns the actual price of cart.
     * @return Price of cart.
     */
    public  double getCartPrice(){
        double price = 0.0;
        for(Product product: productsInCart){
             price += product.getDefaultPrice() * product.getQuantity();
        }
        return price;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userID=" + userID +
                ", productsInCart=" + productsInCart +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
