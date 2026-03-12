package br.com.inspectflow.domain.checklist.models;

import br.com.inspectflow.domain.InspectionItem.models.InspectionItem;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "checklists")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Checklist {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "checklist",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<InspectionItem> items  = new HashSet<>();

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void addItem(InspectionItem item) {
        if (item == null) return;
        items.add(item);
        item.setChecklist(this);
    }

    public void removeItem(InspectionItem item) {
        items.remove(item);
        item.setChecklist(null);
    }

}
