package br.com.inspectflow.application.equipment.validators;

import br.com.inspectflow.application.http.handlers.EquipmentCodeInvalidException;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEquipmentCodeValidation implements EquipmentValidator<String> {
    private final EquipmentRepository repository;
    private final CodeStringValidator codeStringValidator;

    @Override
    public void execute(String code) {
        codeStringValidator.execute(code);
        if (repository.existsByCode(code)) {
            throw new EquipmentCodeInvalidException("Já existe um equipamento com esse código");
        }

    }
}
