package br.com.inspectflow.domain.equipamento.models;

import br.com.inspectflow.domain.equipamento.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipamento.enums.EquipmentType;
import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;
import br.com.inspectflow.domain.estoque.models.StockItem;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "equipments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentType type;

    @Column(nullable = false)
    private String location;

    private LocalDateTime lastInspection;

    private LocalDateTime nextInspection;

    @Builder.Default
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EquipmentComponent> components = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "equipment_stock_items",
            joinColumns = @JoinColumn(name = "equipment_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_item_id")
    )
    @JsonManagedReference
    private List<StockItem> partsInStock = new ArrayList<>();

    public void addComponent(EquipmentComponent component) {
        if (component == null) return;
        components.add(component);
        component.setEquipment(this);
    }

    public void removeComponent(EquipmentComponent component) {
        components.remove(component);
        component.setEquipment(null);
    }

    public void addPart(StockItem part) {
        if (part == null) return;
        partsInStock.add(part);
        part.addEquipament(this);
    }

    public void removePart(StockItem part) {
        partsInStock.remove(part);
        part.removeEquipament(this);
    }
}
