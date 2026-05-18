package br.com.inspectflow.application.order.validators;

import br.com.inspectflow.application.equipment.ports.out.FileDetectedType;
import br.com.inspectflow.application.http.handlers.AttachmentException;
import br.com.inspectflow.application.order.dto.OrderAttachmentRequest;
import br.com.inspectflow.domain.order.repositories.OrderAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderAttachmentFileIsValid implements WorkOrderValidator<OrderAttachmentRequest> {

    private final FileDetectedType fileDetectedType;
    private final OrderAttachmentRepository repository;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    private final List<String> acceptedType = List.of(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );


    @Override
    public void execute(OrderAttachmentRequest dto) {
        MultipartFile file = dto.file();

        if (file.isEmpty()) error("Arquivo não foi encontrado ou carregado!");
        if (file.getSize() > MAX_FILE_SIZE) error("Arquivo excedeu o limite máximo permitido de 10MB") ;
        if (!fileDetectedType.execute(file,acceptedType)) error("Formato de arquivo inválido!");
        if (repository.existsByWorkOrderIdAndType(dto.orderId(), dto.type())) error("Já existe um arquivo relacionado a " + dto.type().name() + " para esta OS!");
    }

    public void execute(MultipartFile file) {

        if (file.isEmpty()) error("Arquivo não foi encontrado ou carregado!");
        if (file.getSize() > MAX_FILE_SIZE) error("Arquivo excedeu o limite máximo permitido de 10MB") ;
        if (!fileDetectedType.execute(file,acceptedType)) error("Formato de arquivo inválido!");
    }

    private void error(String message){
        throw new AttachmentException(message);
    }
}
