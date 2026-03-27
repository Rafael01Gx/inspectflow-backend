package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.user.models.User;

import java.util.List;

public interface CreateSystemWorkOrderUseCase {

    void execute(User user, Equipment equipment, List<String> description);
}
