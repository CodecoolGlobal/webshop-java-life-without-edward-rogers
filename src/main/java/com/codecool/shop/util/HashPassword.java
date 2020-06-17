package com.codecool.shop.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class HashPassword {
    private static HashPassword instance;
    SecureRandom random;

    private HashPassword() {
        random = new SecureRandom();
    }

    public static HashPassword getInstance() {
        if (instance == null) {
            instance = new HashPassword();
        }
        return instance;
    }

    public String hash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String passwordToHash = "password";
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public boolean verifyPassword(String loginPassword, String savedPassword) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return savedPassword.equals(hash(loginPassword));
    }
}
