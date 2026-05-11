package br.com.inspectflow.domain.equipment.models;

import br.com.inspectflow.domain.equipment.enums.InspectionFrequency;
import br.com.inspectflow.domain.inspection.enums.InspectionCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "equipment_health_sheets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EquipmentHealthSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    private InspectionFrequency mechanicalInspectionFrequency ;
    private LocalDateTime lastMechanicalInspection;
    private LocalDateTime nextMechanicalInspection;

    private InspectionFrequency electricalInspectionFrequency ;
    private LocalDateTime lastElectricalInspection;
    private LocalDateTime nextElectricalInspection;

    private InspectionFrequency calibrationInspectionFrequency ;
    private LocalDateTime lastCalibration;
    private LocalDateTime nextCalibration;


    public void updateInspectionDate(InspectionCategory category) {
        var dateNow = LocalDateTime.now();
        switch (category) {
            case MECANICA -> {
                this.lastMechanicalInspection = dateNow;
                this.nextMechanicalInspection  = dateNow.plusDays(this.mechanicalInspectionFrequency.getDias());
            }
            case ELETRICA -> {
                this.lastElectricalInspection = dateNow;
                this.nextElectricalInspection  = dateNow.plusDays(this.electricalInspectionFrequency.getDias());
            }
            case AFERICAO -> {
                this.lastCalibration = dateNow;
                this.nextCalibration  = dateNow.plusDays(this.calibrationInspectionFrequency.getDias());
            }
            default -> throw new IllegalArgumentException("Categoria não suportada para atualização automática");
        }
    }

    public void updateInspectionFrequency(Map<InspectionCategory,InspectionFrequency> frequency) {
        if (frequency == null) return;
        this.mechanicalInspectionFrequency = frequency.get(InspectionCategory.MECANICA);
        this.electricalInspectionFrequency = frequency.get(InspectionCategory.ELETRICA);
        this.calibrationInspectionFrequency = frequency.get(InspectionCategory.AFERICAO);

    }

}
