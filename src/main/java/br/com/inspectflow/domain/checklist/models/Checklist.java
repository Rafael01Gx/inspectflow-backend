package br.com.inspectflow.domain.checklist.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "checklists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Checklist {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String code;

    private String name;

    private UUID equipmentId;

    private String equipmentName;

    @Builder.Default
    private List<ChecklistItem> items = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public void addItem(ChecklistItem item) {
        if (item == null) return;
        items.add(item);
    }

    public void removeItem(ChecklistItem item) {

        items.remove(item);
    }
    public void removeAllItems(){
        items.clear();
    }


}
