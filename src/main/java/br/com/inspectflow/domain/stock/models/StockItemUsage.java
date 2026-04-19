package br.com.inspectflow.domain.stock.models;

import br.com.inspectflow.domain.order.models.WorkOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_item_usages")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockItemUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_item_id", nullable = false)
    private StockItem stockItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    @Column(nullable = false)
    private Integer quantityUsed;

    @Column(name = "used_at",nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime usedAt;

}
