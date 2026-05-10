package br.com.inspectflow.application.email.services;

import br.com.inspectflow.application.email.ports.in.SendRecoveryMailUseCase;
import br.com.inspectflow.infrastructure.config.properties.AppHostProperties;
import br.com.inspectflow.infrastructure.config.properties.SpringApplicationProperties;
import io.micrometer.observation.annotation.Observed;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final AppHostProperties appHosts;

    private final SpringApplicationProperties springApplicationProperties;


    @Override
    @Async
    @Observed(name = "mail.recovery",
    contextualName = "Envia email de recuperação")
    public void execute(String to, String name, String token) {
        try {

            String urlBase = appHosts.web() + "/recovery-password";
            String urlCompleta = urlBase + "?token=" + token;

            Context context = new Context();
            context.setVariable("nome", name);
            context.setVariable("urlRecuperacao", urlCompleta);

            String htmlBody = templateEngine.process("emails/recuperar-senha", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(springApplicationProperties.mail().from());
            helper.setTo(to);
            helper.setSubject("InspectFlow - Recuperação de Senha");
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            log.error("Erro ao enviar e-mail de recuperação para: " + to, e);
        }
    }
}
