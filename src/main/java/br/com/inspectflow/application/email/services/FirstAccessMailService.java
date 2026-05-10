package br.com.inspectflow.application.email.services;

import br.com.inspectflow.application.email.ports.in.FirstAccessMailUseCase;
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
public class FirstAccessMailService implements FirstAccessMailUseCase {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final AppHostProperties appHosts;

    private final SpringApplicationProperties springApplicationProperties;




    @Override
    @Async
    @Observed(name = "mail.first-access",
    contextualName = "Enviar e-mail de primeiro acesso")
    public void execute(String to, String nome, String tempPassword) {
        try {
            Context context = new Context();
            context.setVariable("nome", nome);
            context.setVariable("email", to);
            context.setVariable("tempPassword", tempPassword);
            context.setVariable("urlLogin", appHosts.web());

            String htmlBody = templateEngine.process("emails/cadastro-usuario", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(springApplicationProperties.mail().from());
            helper.setTo(to);
            helper.setSubject("Bem-vindo ao InspectFlow - Seus Dados de Acesso");
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {

            log.error("Falha ao enviar e-mail de cadastro para: " + to, e);
        }
    }
}
