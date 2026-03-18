package br.com.inspectflow.domain.Inspection_item.models;

import br.com.inspectflow.domain.Inspection_item.enums.InspectionCategoryItem;
import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

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

    public void update(String title, String description, InspectionCategoryItem category, Boolean impedimentItem){
        Optional.ofNullable(title).ifPresent(t -> this.title = t);
        Optional.ofNullable(description).ifPresent(d -> this.description = d);
        Optional.ofNullable(category).ifPresent(c -> this.category = c);
        Optional.ofNullable(impedimentItem).ifPresent(i -> this.impedimentItem = i);
    }

    public void setEquipmentComponent(EquipmentComponent equipmentComponent) {
        if (equipmentComponent == null) return;
        this.equipmentComponent = equipmentComponent;
    }
}
