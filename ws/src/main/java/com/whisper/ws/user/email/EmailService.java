package com.whisper.ws.user.email;

import com.whisper.ws.user.configuration.WhisperProperties;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    @Autowired
    WhisperProperties whisperProperties;



    JavaMailSenderImpl javaMailSender;

    @PostConstruct
    public void initialize(){

        this.javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(whisperProperties.getEmail().host());
        javaMailSender.setPort(whisperProperties.getEmail().port());
        javaMailSender.setUsername(whisperProperties.getEmail().username());
        javaMailSender.setPassword(whisperProperties.getEmail().password());

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", true);

    }

    String activationEmail = """
            <html>
                <body>
                    <h1> Activate Account </h1>
                    <a href="${url}">Click Here</a>
                </body>
            </html>
            """;
    public void sendActivationEmail(String email, String activationToken){
        var activationUrl = whisperProperties.getClient().host() +"/activation/" + activationToken ;
        var mailBody = activationEmail.replace("${url}", activationUrl);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

        try{
            message.setFrom(whisperProperties.getEmail().from());
            message.setTo(email);
            message.setSubject("Account Activation");
            message.setText(mailBody, true);
        }catch (MessagingException e){
            e.printStackTrace();
        }
        this.javaMailSender.send(mimeMessage);
    }
}
