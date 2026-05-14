package br.com.inspectflow.application.email.services;

import br.com.inspectflow.application.email.ports.in.SendEmailUseCase;
import br.com.inspectflow.infrastructure.config.properties.SpringApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendEmailService implements SendEmailUseCase {

    private final JavaMailSender mailSender;
    private final SpringApplicationProperties springApplicationProperties;

    @Override
    public void execute(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(springApplicationProperties.mail().from());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
