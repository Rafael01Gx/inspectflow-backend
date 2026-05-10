package br.com.inspectflow.domain.equipment.models;

import br.com.inspectflow.domain.common.enums.PartCategory;
import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.enums.EquipmentType;
import br.com.inspectflow.domain.equipment.enums.InspectionFrequency;
import br.com.inspectflow.domain.inspection.enums.InspectionCategory;
import br.com.inspectflow.domain.stock.models.StockItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentType type;

    @Column(nullable = false)
    private String location;

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


    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "equipment_consignment_codes",joinColumns = @JoinColumn(name = "equipment_id"))
    @MapKeyColumn(name = "consignment_key")
    @Column(name = "consignment_value")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<PartCategory, String> consignmentCodes = new HashMap<>();

    @Setter
    @Column(name = "image_url")
    private String imageUrl;

    private String propertyCode;

    @OneToOne(
            mappedBy = "equipment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private EquipmentHealthSheet healthSheet;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void update(String name, EquipmentStatus status, EquipmentType type, String location, Map<InspectionCategory,InspectionFrequency> inspectionFrequency, String propertyCode){
        if (name != null) this.name = name;
        if (status != null) this.status = status;
        if (type != null) this.type = type;
        if (location != null) this.location = location;
        if (inspectionFrequency != null) this.healthSheet.updateInspectionFrequency(inspectionFrequency);
        if (propertyCode != null) this.propertyCode = propertyCode.toUpperCase();

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

    public void addConsignmentCode(PartCategory key, String value) {
        if (key == null || value == null) return;
        this.consignmentCodes.put(key, value);
    }
    public void removeConsignmentCode(PartCategory key){
        if (key == null) return;
        this.consignmentCodes.remove(key);
    }
    public void setConsignmentCodes(Map<PartCategory, String> consignmentCodes){
        if (consignmentCodes == null) return;
        this.consignmentCodes.clear();
       consignmentCodes.forEach((e,s) -> this.consignmentCodes.put(e,s.toUpperCase()));
    }

    public void updateInspection(InspectionCategory category){
        this.healthSheet.updateInspectionDate(category);
    }

    public void setHealthSheet(EquipmentHealthSheet healthSheet) {
        if (healthSheet == null) {
            if (this.healthSheet != null) {
                this.healthSheet.setEquipment(null);
            }
        } else {
            healthSheet.setEquipment(this);
        }
        this.healthSheet = healthSheet;
    }

}
