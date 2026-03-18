package br.com.inspectflow.application.equipment.validators;

import br.com.inspectflow.application.http.handlers.EquipmentCodeInvalidException;
import org.springframework.stereotype.Component;

@Component
public class CodeStringValidator implements EquipmentValidator<String> {

    @Override
    public void execute(String code) {
        if(code == null || !(code.matches("^[a-zA-Z]{1,5}-[0-9]{1,5}$")))
            throw new EquipmentCodeInvalidException();
    }
}
