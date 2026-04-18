package br.com.inspectflow.application.order.validators;

import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkOrderUpdatePermissionValidator {
    private final WorkOrderBelongsToUserValidator belongsToUserValidator;

    public void execute(WorkOrder order, User user) {
        if (!user.getRole().equals(Role.ADMINISTRADOR)) {
            belongsToUserValidator.execute(order, user);
        }
    }

}
