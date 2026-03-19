package br.com.inspectflow.domain.checklist.models;

import br.com.inspectflow.domain.inspection.enums.InspectionCategoryItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistItem {
    private String componentName;
    private String title;
    private String description;
    private InspectionCategoryItem category;
    private boolean impedimentItem;
}