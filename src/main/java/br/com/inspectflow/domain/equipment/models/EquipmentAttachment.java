package br.com.inspectflow.domain.equipment.models;

import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "equipment_attachments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"equipment_id", "type"})
        })
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EquipmentAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private AttachmentType type;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String fileName;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String fileUrl;

    private String contentType;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;


}