package br.com.inspectflow.domain.inspection.models;


import br.com.inspectflow.domain.inspection.enums.InspectionCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inspection_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class InspectionHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID equipmentId;

    @Column(nullable = false)
    private UUID inspectionId;

    @Column(nullable = false)
    private UUID inspectorId;

    @Column(nullable = false)
    private String inspectorName;

    @Column(nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InspectionCategory category;

    @Column(nullable = false)
    private String status;
}
