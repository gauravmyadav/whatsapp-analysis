package com.shefali.cbse.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.file.Path;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    @SneakyThrows
    @Async
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("gauravmyadav@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        Path path = Path.of(pathToAttachment);

        helper.addAttachment("Question Paper", path.toFile());

        javaMailSender.send(message);
    }
}
