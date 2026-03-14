package br.com.inspectflow.domain.inspection.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Document(collection = "inspections")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inspection {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @NotBlank
    private String equipmentId;

    @NotBlank
    private String equipmentName;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String status;

    @NotBlank
    private String technician;


    private String notes;

    @NotEmpty
    private List<ComponentResults> componentResults;
}
