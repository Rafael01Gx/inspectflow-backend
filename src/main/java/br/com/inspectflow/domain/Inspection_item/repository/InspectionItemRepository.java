package br.com.inspectflow.domain.Inspection_item.repository;

import br.com.inspectflow.domain.Inspection_item.models.InspectionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InspectionItemRepository extends JpaRepository<InspectionItem, Long>
{


}
