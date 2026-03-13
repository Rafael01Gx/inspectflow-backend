package br.com.inspectflow.domain.estoque.models;

import br.com.inspectflow.domain.equipamento.models.Equipment;
import br.com.inspectflow.domain.estoque.enums.PartCategory;
import br.com.inspectflow.domain.estoque.enums.StockType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stock_items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
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
    private List<Equipment> linkedEquipmentIds = new ArrayList<>();

    @Column(nullable = false)
    private String location;

    @Min(0)
    private Integer minQuantity;

    public void addEquipament(Equipment equipment) {
        if (equipment == null) return;
        linkedEquipmentIds.add(equipment);
    }

    public void removeEquipament(Equipment equipment) {
        linkedEquipmentIds.remove(equipment);
    }

}
