package br.com.inspectflow.application.order.validators;

import br.com.inspectflow.application.http.handlers.UnauthorizedException;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.user.models.User;
import org.springframework.stereotype.Component;

@Component
public class WorkOrderBelongsToUserValidator {

    public void execute(WorkOrder order, User user) {
        var ownership = order.getAssignee().getId().equals(user.getId());
        if (!ownership) throw new UnauthorizedException("Você não tem permissão para alterar esta ordem de serviço.");
    }
}
