package org.example.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class MailingService {
   // @Autowired
    //private JavaMailSender mailSender;  // Injected from Spring

    /*public void sendEmail(User user, String subject, String token) throws MessagingException {
        // Create MIME message
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Set details
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        // Build activation link (adjust URL for gateway)
        String activationLink = "http://localhost:8090/api/auth/activate-account?token=" + token;
        helper.setText("Click to activate: <a href='" + activationLink + "'>Activate</a>", true);

        // Send email
        mailSender.send(message);
    }*/
}
