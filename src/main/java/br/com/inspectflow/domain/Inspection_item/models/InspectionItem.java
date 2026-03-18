package br.com.inspectflow.domain.Inspection_item.models;

import br.com.inspectflow.domain.Inspection_item.enums.InspectionCategoryItem;
import br.com.inspectflow.domain.Inspection_item.enums.InspectionStatus;
import br.com.inspectflow.domain.checklist.models.Checklist;
import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inspection_items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InspectionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String title;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private InspectionCategoryItem category;

    @Column(nullable = false)
    private boolean impedimentItem;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_component_id")
    @JsonBackReference
    private EquipmentComponent equipmentComponent;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    @JsonBackReference
    private Checklist checklist;

    public void setEquipmentComponent(EquipmentComponent equipmentComponent) {
        if (equipmentComponent == null) return;
        this.equipmentComponent = equipmentComponent;
    }

    public void setChecklist(Checklist checklist) {
        if (checklist == null) return;
        this.checklist = checklist;
    }
}
