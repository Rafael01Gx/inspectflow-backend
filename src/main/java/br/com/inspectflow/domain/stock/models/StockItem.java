package br.com.inspectflow.domain.stock.models;

import br.com.inspectflow.application.stock.dto.UpdateStockItemRequest;
import br.com.inspectflow.domain.common.enums.PartCategory;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.stock.enums.StockType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "stock_items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StockType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PartCategory partCategory;

    @Column(nullable = false)
    private Integer quantity;

    private String supplierCode;

    @Builder.Default
    @ManyToMany(mappedBy = "partsInStock")
    @Setter
    private Set<Equipment> linkedEquipments = new HashSet<>();

    @Column(nullable = false)
    private String location;

    @Min(0)
    private Integer minQuantity;

    @Setter
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    public void addEquipament(Equipment equipment) {
        if (equipment == null) return;
        this.linkedEquipments.add(equipment);
        equipment.getPartsInStock().add(this);
    }

    public void removeEquipament(Equipment equipment) {
        linkedEquipments.remove(equipment);
        equipment.getPartsInStock().remove(this);
    }

    public void deductStock(Integer quantity){
        this.quantity -= quantity;
    }

    public void update(UpdateStockItemRequest dto) {
        if (dto.name() != null) this.name = dto.name();
        if (dto.type() != null) this.type = dto.type();
        if (dto.partCategory() != null) this.partCategory = dto.partCategory();
        if (dto.quantity() != null) this.quantity = dto.quantity();
        if (dto.supplierCode() != null) this.supplierCode = dto.supplierCode();
        if (dto.location() != null) this.location = dto.location();
        if (dto.minQuantity() != null) this.minQuantity = dto.minQuantity();
    }

    public void update(UpdateStockItemRequest dto, List<Equipment> linkedEquipment ){
        this.update(dto);
        if (linkedEquipment != null) {
            linkedEquipment.forEach(this::addEquipament);
        }
    }

    public void addEquipments(List<Equipment> equipmentsToAdd) {
        if (equipmentsToAdd != null) {
            equipmentsToAdd.forEach(this::addEquipament);
        }
    }

}
