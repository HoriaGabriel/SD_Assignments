package ro.utcluj.foodpanda.controller;

import ro.utcluj.foodpanda.model.Food;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class EmailSender {

    public void sendmail(List<Food> foodList, int finalPrice) throws AddressException, MessagingException, IOException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("rusu.horia@gmail.com", "gabriel20");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("rusu.horia@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("rusu.horia@gmail.com"));
        msg.setSubject("New Order");

        String s = "The Order contains: ";

        String aux = new String();

        for(Food f: foodList){
            String s1 = f.getName();
            aux = aux + s1 + ",";
        }

        aux = aux.substring(0,aux.length()-1);

        s = s + aux;

        s = s + ". ";

        String price = "The Orders price is: ";

        price = price + String.valueOf(finalPrice);

        String fin = s + price;

        msg.setContent(fin, "text/html");
        msg.setSentDate(new Date());

        Transport.send(msg);
    }
}
