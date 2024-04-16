package test;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.sql.DataSource;
import java.util.Properties;

public class SendE_Mail {

    public static void main(String[] args) {
        //1) get the session object

        String user = "silawatchaitanya@gmail.com";
        String password = "Silawat$22";
        String to = "chaitanya.silawat911@gmail.com";
        Properties properties = System.getProperties();
////        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.port", "587");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
//        properties.put("mail.smtp.starttls.required", "true");
//        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//
//        properties.put("mail.smtp.host", "smtp.gmail.com");
////        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.user", user);
        properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });

        //2) compose message
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("From Mobile Automation Project ");

            //3) create MimeBodyPart object and set your message text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("Report");

            //4) create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

//            String filename = report;//change accordingly
//            DataSource source = new FileDataSource(filename);
//            messageBodyPart2.setDataHandler(new DataHandler(source));
//            messageBodyPart2.setFileName(filename);


            //5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
//            multipart.addBodyPart(messageBodyPart2);

            //6) set the multiplart object to the message object
            message.setContent(multipart );

            //7) send message
            Transport.send(message);

            System.out.println("Report has been sent to: "+ to);
        }catch (MessagingException ex) {ex.printStackTrace();}
    }
    }

//    public static void main(String[] args) throws MessagingException {
//        String recipient = "chaitanya.silawat911@gmail.com";
//        String sender = "silawatchaitanya@gmail.com";
//        Properties properties = new Properties();
//        Session session = null;
//
//        String username = "silawatchaitanya@gmail.com";
//        String password = "Silawat$22";
//        properties.put("mail.smtp.host", "smtp.gmail.com");
////        properties.put("mail.smtp.user", username); // User name
////        properties.put("mail.smtp.password", password); // password
//        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.required", "true");
//        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.ssl.enable", "true");
//
//        session = Session.getInstance(properties, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//        try
//        {
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(sender));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
//            message.setSubject("This is Subject");
//            BodyPart messageBodyPart1 = new MimeBodyPart();
//            messageBodyPart1.setText("This is body of the mail");
//            // creating second MimeBodyPart object
////            BodyPart messageBodyPart2 = new MimeBodyPart();
////            String filename = "attachment.txt";
////            DataSource source = (DataSource) new FileDataSource(filename);
////            messageBodyPart2.setDataHandler(new DataHandler((javax.activation.DataSource) source));
////            messageBodyPart2.setFileName(filename);
//
//            // creating MultiPart object
//            Multipart multipartObject = new MimeMultipart();
//            multipartObject.addBodyPart(messageBodyPart1);
////            multipartObject.addBodyPart(messageBodyPart2);
//            message.setContent(multipartObject);
//            Transport.send(message);
//            System.out.println("Mail successfully sent");
//        }
//        catch (MessagingException mex)
//        {
//            mex.printStackTrace();
//        }
//    }


