package com.codecool.shop.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ConnectionVariables {
        private String filePath;
        private HashMap<String, String> userData;

        public ConnectionVariables(String filePath) throws IOException {
            this.filePath = filePath;
            read();
        }

        private void read() throws IOException {
            HashMap<String, String> userData = new HashMap<>();
            if (!new FileReader(filePath).ready()){
                throw new IOException();
            }
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            String[] types = {"dbname", "username", "password"};
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (3 < i){
                    break;
                }
                userData.put(types[i], line);
                i++;
            }
            this.userData = userData;
        }

        public String getDBName(){
            return userData.get("dbname");
        }

        public String getUserName(){
            return userData.get("username");
        }

        public String getPassword(){
            return userData.get("password");
        }
}
