package br.com.inspectflow.application.email.services;

import br.com.inspectflow.application.email.ports.in.SendRecoveryMailUseCase;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendRecoveryMailService implements SendRecoveryMailUseCase {


    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.from:contato@inspectflow.com.br}")
    private String emailFrom;

    @Value("${app.api_host}")
    private String apiHost;

    @Override
    @Async
    public void execute(String to, String name, String token) {
        try {

            String urlBase = apiHost + "/recovery-password";
            String urlCompleta = urlBase + "?token=" + token;

            Context context = new Context();
            context.setVariable("nome", name);
            context.setVariable("urlRecuperacao", urlCompleta);

            String htmlBody = templateEngine.process("emails/recuperar-senha", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(emailFrom);
            helper.setTo(to);
            helper.setSubject("InspectFlow - Recuperação de Senha");
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            log.error("Erro ao enviar e-mail de recuperação para: " + to, e);
        }
    }
}
