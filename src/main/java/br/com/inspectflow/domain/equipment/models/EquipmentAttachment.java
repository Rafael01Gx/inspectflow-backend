package br.com.inspectflow.domain.equipment.models;

import br.com.inspectflow.domain.common.shared.AbstractAttachment;
import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "equipment_attachments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"equipment_id", "type"})
        })
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class EquipmentAttachment extends AbstractAttachment<AttachmentType> {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private AttachmentType type;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;


}