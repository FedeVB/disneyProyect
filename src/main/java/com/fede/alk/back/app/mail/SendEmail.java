package com.fede.alk.back.app.mail;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendEmail {

    @Value("${sendgrid.api.key}")
    private String apikey;

    public String sendTextEmail(String email, String username) throws IOException {
        Email from = new Email("youremail@gmail.com");
        String subject = "Bienvenido";
        Email to = new Email(email);
        Content content = new Content("text/plain", "Bienvenido "+username+" , muchas gracias por utilizar nuestra aplicacion");

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sendGrid = new SendGrid(this.apikey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println(response.getBody());
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }
}
