package br.com.inspectflow.domain.equipment_component.models;

import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment_component.enums.EquipmentComponentCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "equipment_components")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EquipmentComponentCategory category;

    @Column(nullable = false)
    private Long checklistId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;
}
