package br.com.inspectflow.domain.equipment.models;

import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.enums.EquipmentType;
import br.com.inspectflow.domain.stock.models.StockItem;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentType type;

    @Column(nullable = false)
    private String location;

    @Setter
    private LocalDateTime lastInspection;

    @Setter
    private LocalDateTime nextInspection;

    @Builder.Default
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<EquipmentComponent> components = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "equipment_stock_items",
            joinColumns = @JoinColumn(name = "equipment_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_item_id")
    )
    @JsonManagedReference
    private Set<StockItem> partsInStock = new HashSet<>();

    @Setter
    private String checklistId;

    @Builder.Default
    @OneToMany(
            mappedBy = "equipment",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<EquipmentAttachment> attachments = new HashSet<>();


    public void update(String name, EquipmentStatus status, EquipmentType type, String location){
        Optional.of(name).ifPresent(n -> this.name = n);
        Optional.of(status).ifPresent(s -> this.status = s);
        Optional.of(type).ifPresent(t -> this.type = t);
        Optional.of(location).ifPresent(l -> this.location = l);
    }

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

    public void addAttachment(EquipmentAttachment attachment) {
        if (attachment == null) return;
        attachments.add(attachment);
        attachment.setEquipment(this);
    }

    public void removeAttachment(EquipmentAttachment attachment) {
        attachments.remove(attachment);
        attachment.setEquipment(null);
    }


}
