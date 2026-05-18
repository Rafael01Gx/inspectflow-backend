package br.com.inspectflow.application.email.services;

import br.com.inspectflow.application.email.dto.SendWorkOrderUpdateMailSend;
import br.com.inspectflow.application.email.ports.in.SendWorkOrderUpdateMailUseCase;
import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
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
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendWorkOrderUpdateMailService implements SendWorkOrderUpdateMailUseCase {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final SpringApplicationProperties springApplicationProperties;
    private final UserRepository userRepository;
    private final AppHostProperties appHosts;

    @Override
    @Async
    @Observed(name = "mail.workorder.update", contextualName = "Envia email de atualização da ordem de serviço")
    public void execute(SendWorkOrderUpdateMailSend request) {
        Set<Role> rolesToNotify = Set.of(Role.ADMINISTRADOR, Role.SUPERVISOR, Role.GESTOR, Role.LIDER);
        List<String> recipientEmails = userRepository.findEmailByRoleIn(rolesToNotify);

        if (request.assigneeEmail() != null) {
            recipientEmails.add(request.assigneeEmail());
        }

        List<String> distinctEmails = new java.util.ArrayList<>(new HashSet<>(recipientEmails));

        String urlOrdemServico = appHosts.web() + "/maintenance/" + request.numeroOrdemServico();


        try {
            Context context = new Context();
            context.setVariable("numeroOrdemServico", request.numeroOrdemServico());
            context.setVariable("nomeEquipamento", request.equipmentName()+ "[" + request.equipmentCode() + "]");
            context.setVariable("statusOrdemServico", request.statusOrdemServico());
            context.setVariable("equipmentName", request.equipmentName());
            context.setVariable("tipoDocumento", request.tipoDocumento());
            context.setVariable("nomeArquivo", switch (request.tipoDocumento()){
                case OrderAttachmentType.APR->
                    "Analise preliminar de Risco";
                case OrderAttachmentType.OS->
                    "Ordem de execução de Serviço";
            });
            context.setVariable("urlOrdemServico", urlOrdemServico);

            String htmlBody = templateEngine.process("emails/atualizacao-ordem-servico", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(springApplicationProperties.mail().from());
            helper.setBcc(distinctEmails.toArray(new String[0]));
            helper.setSubject("Atualização na Ordem de Serviço: " + request.equipmentName() + "[" + request.numeroOrdemServico().toUpperCase() + "]");
            helper.setText(htmlBody, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            log.error("Erro ao enviar e-mail... para: {}", distinctEmails, e);
        }
    }


}
