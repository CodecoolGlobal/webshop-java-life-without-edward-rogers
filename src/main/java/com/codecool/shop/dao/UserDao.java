package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    void add(User user);
    Map<String, String> getDataOfUser(String userName);
    Map<String, String> getNameAndPassword(String userName);

}
