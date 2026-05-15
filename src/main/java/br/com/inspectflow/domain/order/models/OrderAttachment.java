package br.com.inspectflow.domain.order.models;

import br.com.inspectflow.domain.common.shared.AbstractAttachment;
import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "work_order_attachments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"work_order_id", "type"})
        })
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class OrderAttachment extends AbstractAttachment<OrderAttachmentType> {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private OrderAttachmentType type;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder order;
}