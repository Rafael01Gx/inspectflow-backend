package br.com.inspectflow.application.email.services;

import br.com.inspectflow.application.email.dto.SendWorkOrderCreatedMailRequest;
import br.com.inspectflow.application.email.ports.in.SendWorkOrderCreatedMailUseCase;
import br.com.inspectflow.application.utils.FormatDateUtils;
import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.domain.user.repositories.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendWorkOrderCreatedMailService implements SendWorkOrderCreatedMailUseCase {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final AppHostProperties appHosts;
    private final SpringApplicationProperties springApplicationProperties;
    private final UserRepository userRepository;

    @Override
    @Async
    @Observed(name = "mail.workorder.created", contextualName = "Envia email de ordem de serviço criada")
    @Transactional(readOnly = true)
    public void execute(
            SendWorkOrderCreatedMailRequest request
    ) {
        var to = userRepository.findEmailByRoleIn(Set.of(Role.ADMINISTRADOR,Role.SUPERVISOR,Role.GESTOR,Role.LIDER));


        if (to.isEmpty()) {
            log.warn("Nenhum destinatário encontrado para WorkOrder id={}", request.orderId());
            return;
        }

        String urlOrdemServico = appHosts.web() + "/maintenance/" + request.orderId();
        try {
            Context context = new Context();
            context.setVariable("equipmentName", request.equipmentName());
            context.setVariable("codigoEquipamento", request.codigoEquipamento());
            context.setVariable("orderPriority", request.orderPriority());
            context.setVariable("dueDate", request.dueDate());
            context.setVariable("title", request.title());
            context.setVariable("description", request.description());
            context.setVariable("stockRequisition", request.stockRequisition());
            context.setVariable("assignee", request.assignee());
            context.setVariable("createdAt", request.createdAt() != null ? FormatDateUtils.format(request.createdAt()): FormatDateUtils.format(LocalDateTime.now()));
            context.setVariable("urlOrdemServico", urlOrdemServico);

            String htmlBody = templateEngine.process("emails/nova-ordem-servico", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(springApplicationProperties.mail().from());
            helper.setTo(to.toArray(String[]::new));
            helper.setSubject("Nova Ordem de Serviço: " + request.equipmentName() + " - " + request.codigoEquipamento());
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            log.error("Erro ao enviar e-mail de ordem de serviço criada para: " + to, e);
        }
    }
}
