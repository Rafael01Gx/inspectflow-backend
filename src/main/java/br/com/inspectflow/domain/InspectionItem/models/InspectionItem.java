package br.com.inspectflow.domain.InspectionItem.models;

import br.com.inspectflow.domain.InspectionItem.enums.CategoryItem;
import br.com.inspectflow.domain.InspectionItem.enums.InspectionStatus;
import br.com.inspectflow.domain.checklist.models.Checklist;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inspection_items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class InspectionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InspectionStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryItem category;

    @Column(nullable = false)
    private boolean impedimentItem;

    private String observation;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id", nullable = false)
    @JsonBackReference
    private Checklist checklist;


}
