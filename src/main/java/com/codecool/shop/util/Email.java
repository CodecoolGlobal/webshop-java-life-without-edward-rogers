package com.codecool.shop.util;

import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.*;

public class Email {
    private final String username = "lwhedwardrogers";
    private final String password = "lifewithoutheroin";
    private final String fromEmail = "lwhedwardrogers@gmail.com";

    private String toEmail;
    private Order order;


    public Email(Order order) {
        this.order = order;
        this.toEmail = order.getEmail();
    }


    public void sendEmail() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "false");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            msg.setSubject("Order Confirmation - Codecool WebShop");

            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();

            textBodyPart.setText(writeTextContent());
            emailContent.addBodyPart(textBodyPart);
            attachFilesToEmail(emailContent);
            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Message sent.");
        } catch (MessagingException e) {
            System.out.println("Trouble at sending the e-mail message. " + e);
        }
    }

    private String writeTextContent() {
        StringBuilder context = new StringBuilder();
        context.append("Dear ").append(order.getName()).append(",\n");
        context.append("\nWe would like to inform you that you ordered the following items from the CodeCool WebShop: \n");
        for (Product product : order.getProducts()) {
            context.append("- ").append(product.getSupplier().getName()).append(" ")
                    .append(product.getName()).append(", Quantity: ").append(product.getQuantity())
                    .append("\n");
        }
        context.append("Total price: ").append("" + calculateTotalPrice()).append("$\n");
        context.append("\nThank you for choosing us! \n" +
                "We will contact you as soon as possible about the development of your Order.\n");
        context.append("\nAddress details: \n");
        context.append(order.getShippingCountry()).append(", ").append(order.getShippingZip()).append(", ")
                .append(order.getShippingCity()).append(", ").append(order.getShippingAddress()).append(".\n");
        context.append("\nBest regards, \nYour favourite superstar, Edward Rogers.");
        return context.toString();
    }

    private void attachFilesToEmail(Multipart emailContent) {
        List<Product> productsInOrder = order.getProducts();
        Set<Product> productSet = new HashSet(productsInOrder);
        ArrayList<Integer> productIDs = new ArrayList<>();
        for (Product product : productSet) {
            productIDs.add(product.getId());
        }
        createAttachments(productIDs, emailContent);
    }

    private void createAttachments(List<Integer> productIDs, Multipart emailContent) {
        try {
            for (Integer productID : productIDs) {
                MimeBodyPart jpgAttachment = new MimeBodyPart();
                jpgAttachment.attachFile("./src/main/webapp/static/img/product_" + productID + ".jpg");
                emailContent.addBodyPart(jpgAttachment);
            }
        } catch (IOException | MessagingException ex){
            System.err.println("There was a problem with attach files. Type: " + ex.getMessage());
        }
    }

    private double calculateTotalPrice(){
        List<Product> productsInOrder = order.getProducts();
        double totalPrice = 0;
        for (Product product : productsInOrder){
            totalPrice += product.getDefaultPrice() * product.getQuantity();
        }
        return totalPrice;
    }
}
