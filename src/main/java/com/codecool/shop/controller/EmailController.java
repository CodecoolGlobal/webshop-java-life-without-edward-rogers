package com.codecool.shop.controller;

/*import com.google.gson.JsonObject;
import com.google.gson.JsonParser;*/

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

@WebServlet(urlPatterns = {"/e-mail"})
public class EmailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        /*String jsonPostRequest = req.getParameter("jsonPostRequest");
        JsonObject jsonObject = new JsonParser().parse(jsonPostRequest).getAsJsonObject();
        String firstName = jsonObject.getAsJsonObject().get("First name").getAsString();
        String lastName = jsonObject.getAsJsonObject().get("Last name").getAsString();
        String email = jsonObject.getAsJsonObject().get("E-mail").getAsString();
        String address = jsonObject.getAsJsonObject().get("Address").getAsString();
        String country = jsonObject.getAsJsonObject().get("Country").getAsString();
        String zipCode = jsonObject.getAsJsonObject().get("Zip code").getAsString();
        String city = jsonObject.getAsJsonObject().get("City").getAsString();
        String state = jsonObject.getAsJsonObject().get("State").getAsString();
        String paymentMethod = jsonObject.getAsJsonObject().get("Payment method").getAsString();
        String orderId = jsonObject.getAsJsonObject().get("ID").getAsString();*/

        final String username = System.getenv("beviktor95@gmail.com");
        final String password = System.getenv("Hiperkarma3148");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        //Start our mail message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(username));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("cziliannamari@gmail.com"));
            msg.setSubject("In memories of Edward Rogers");

            Multipart emailContent = new MimeMultipart();

            //Text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(
                    "Dear Rogers! Please send your ass."
            );

            //Attach body parts
            emailContent.addBodyPart(textBodyPart);

            //Attach multipart to message
            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}


