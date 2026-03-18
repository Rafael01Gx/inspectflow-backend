package br.com.inspectflow.domain.equipment_component.models;

import br.com.inspectflow.domain.Inspection_item.models.InspectionItem;
import br.com.inspectflow.domain.checklist.models.Checklist;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment_component.enums.EquipmentComponentCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "equipment_components")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class EquipmentComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private EquipmentComponentCategory category;

    @Setter
    @Builder.Default
    @Column(nullable = false)
    @OneToMany(mappedBy = "equipmentComponent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<InspectionItem> inspectionItem = new HashSet<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    @JsonBackReference
    private Equipment equipment;


    public void addInspectionItem(InspectionItem item) {
        if (item == null) return;
        this.inspectionItem.add(item);
        item.setEquipmentComponent(this);
        }

    public void removeInspectionItem(InspectionItem item) {
        if (item == null) return;
        this.inspectionItem.remove(item);
    }
}
