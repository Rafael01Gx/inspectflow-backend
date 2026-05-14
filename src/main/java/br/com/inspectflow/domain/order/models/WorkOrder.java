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

    @Setter
    @Column(nullable = false)
    private String performedWork;

    private LocalDateTime completionDate;

    @Setter
    private String stockRequisition;

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

    public void update(String title, String description, OrderPriority orderPriority, LocalDate dueDate, List<MaintenancePart> parts, User user) {
        var updateMessage = "Ordem de serviço atualizada/modificada por: " + user.getName() + " - " + user.getRole();
        if (title != null) this.title = title;
        if (description != null) this.description = description;
        if (orderPriority != null) this.orderPriority = orderPriority;
        if (dueDate != null) this.dueDate = dueDate;
        if (parts != null) {
            this.removeAllParts();
            this.addAllParts(parts);
        }
        this.addSystemInfo(updateMessage);
    }

    public void addPart(MaintenancePart part) {
        if (part == null) return;
        this.parts.add(part);
    }

    public void removePart(MaintenancePart part) {
        this.parts.remove(part);
    }

    public void removeAllParts() {
        this.parts.clear();
    }


    public void completeOrder() {
        this.orderStatus = OrderStatus.COMPLETED;
        this.completionDate = LocalDateTime.now();
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCELLED;
        this.completionDate = LocalDateTime.now();
    }


    public void addSystemInfo(String info) {
        var infoMessage = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + info;
        if (this.systemInfo.contains(infoMessage)) return;
        this.systemInfo.add(infoMessage);
    }

    public void addAllParts(List<MaintenancePart> parts) {
        this.parts.addAll(parts);
    }
}
