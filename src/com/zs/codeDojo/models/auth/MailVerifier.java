package com.zs.codeDojo.models.auth;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.zs.codeDojo.models.DAO.Response;
import com.zs.codeDojo.models.DAO.User;
import com.zs.codeDojo.properties.Settings;

public class MailVerifier {

    public static Response sendMail(User user) {


        Settings settings = new Settings("code.dojo.zs@gmail.com", "viwctluwbitydjda");

        Session session = Session.getInstance(settings.getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(settings.getUsername(), settings.getAppPassword());
            }
        });

        try {
            String code = generateCode();

            Message message = createMessage(session, settings.getUsername(), user.getEmail(), "Verification code for christmas project", "Your verification code is ZS-" + code);
            Transport.send(message);


            return new Response(code, new AuthStatus("204"));
        } catch (MessagingException e) {
            e.printStackTrace();
            return new Response("", new AuthStatus("408"));
        }
        
    }

    private static Message createMessage(Session session, String from, String to, String sub, String content) throws MessagingException {
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

        message.setSubject(sub);
        message.setText(content);
        return message;
    }

    private static String generateCode(){
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append((int) Math.round(Math.random() * 9));
        }
        return code.toString();
    }
}
