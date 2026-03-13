package br.com.inspectflow.domain.order.models;

import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.order.enums.OrderPriority;
import br.com.inspectflow.domain.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "work_orders")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private String equipmentName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private OrderPriority orderPriority;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private String assignee;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "work_order_parts", joinColumns = @JoinColumn(name = "work_order_id"))
    @Builder.Default
    private Set<MaintenancePart> parts = new HashSet<>();

    @Column(nullable = false)
    private String performedWork;

    private LocalDate completionDate;


    @PostLoad
    @PrePersist
    public void syncEquipmentName() {
        if (this.equipment != null) {
            this.equipmentName = this.equipment.getName();
        }
    }

    public void addPart(MaintenancePart part) {
        if (part == null) return;
        this.parts.add(part);
    }

    public void removePart(MaintenancePart part) {
        this.parts.remove(part);
    }


    public void completeOrder() {
        this.orderStatus = OrderStatus.COMPLETED;
        this.completionDate = LocalDate.now();
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCELLED;
    }


}
