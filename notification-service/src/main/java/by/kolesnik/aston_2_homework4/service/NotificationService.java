package by.kolesnik.aston_2_homework4.service;

import by.kolesnik.aston_2_homework4.validation.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender mailSender;
    private final Validation validation;

    public void sendEmail(String to, String text) {
        validation.validateEmail(to);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setText(text);
        mailSender.send(message);
        log.info("Email sent to: {}", to);
    }
}
