package br.com.inspectflow.domain.InspectionItem.repository;

import br.com.inspectflow.domain.InspectionItem.models.InspectionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InspectionItemRepository extends JpaRepository<InspectionItem, Long>
{


}
