package br.com.inspectflow.application.email.services;

import br.com.inspectflow.application.email.ports.in.SendEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendEmailService implements SendEmailUseCase {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.from:contato@inspectflow.com.br}")
    private String emailFrom;

    @Override
    public void execute(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
