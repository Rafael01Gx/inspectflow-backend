package br.com.inspectflow.domain.order.models;

import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.order.enums.OrderPriority;
import br.com.inspectflow.domain.order.enums.OrderStatus;
import br.com.inspectflow.domain.user.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private OrderPriority orderPriority;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "work_order_parts", joinColumns = @JoinColumn(name = "work_order_id"))
    @Builder.Default
    private Set<MaintenancePart> parts = new HashSet<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    @Builder.Default
    private List<String> systemInfo = new ArrayList<>();

    @Column(nullable = false)
    private String performedWork;

    private LocalDate completionDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


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


    public void addSystemInfo(String info){
        this.systemInfo.add(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + info);
    }
}
