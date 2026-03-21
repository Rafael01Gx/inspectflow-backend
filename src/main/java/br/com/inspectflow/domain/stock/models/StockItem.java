package br.com.inspectflow.domain.stock.models;

import br.com.inspectflow.application.stock.dto.UpdateStockItemRequest;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.stock.enums.PartCategory;
import br.com.inspectflow.domain.stock.enums.StockType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.*;

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
    @JsonBackReference
    @Setter
    private Set<Equipment> linkedEquipments = new HashSet<>();

    @Column(nullable = false)
    private String location;

    @Min(0)
    private Integer minQuantity;

    public void addEquipament(Equipment equipment) {
        if (equipment == null) return;
        this.linkedEquipments.add(equipment);
        // IMPORTANTE: Adiciona este StockItem à coleção do Equipment (Lado Dono)
        // Isso garante que o relacionamento seja persistido na tabela de junção.
        equipment.getPartsInStock().add(this);
    }

    public void removeEquipament(Equipment equipment) {
        linkedEquipments.remove(equipment);
        equipment.getPartsInStock().remove(this);
    }

    public void deductStock(Integer quantity){
        this.quantity -= quantity;
    }

    public void update(UpdateStockItemRequest dto){
        Optional.of(dto.name()).ifPresent(name -> this.name = name);
        Optional.of(dto.type()).ifPresent(type -> this.type = type);
        Optional.of(dto.partCategory()).ifPresent(partCategory -> this.partCategory = partCategory);
        Optional.of(dto.quantity()).ifPresent(quantity -> this.quantity = quantity);
        Optional.of(dto.supplierCode()).ifPresent(supplierCode -> this.supplierCode = supplierCode);
        Optional.of(dto.location()).ifPresent(location -> this.location = location);
        Optional.of(dto.minQuantity()).ifPresent(minQuantity -> this.minQuantity = minQuantity);
    }

    public void update(UpdateStockItemRequest dto, List<Equipment> linkedEquipment ){
        this.update(dto);
        // Para update, usamos addEquipament para garantir a sincronização bidirecional
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
