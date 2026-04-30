package br.com.inspectflow.application.order.validators;

import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkOrderUpdatePermissionValidator {
    private final WorkOrderBelongsToUserValidator belongsToUserValidator;

    public void execute(WorkOrder order, User user) {

       if (isPrivileged(user)) return;

       belongsToUserValidator.execute(order, user);
    }

    private boolean isPrivileged(User user) {
        return switch (user.getRole()) {
            case ADMINISTRADOR, SUPERVISOR, LIDER, GESTOR -> true;
            default -> false;
        };
    }

}
