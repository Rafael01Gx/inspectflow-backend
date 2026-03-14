package br.com.inspectflow.domain.inspection.repositories;

import br.com.inspectflow.domain.inspection.models.Inspection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface InspectionRepository extends MongoRepository<Inspection, UUID> {
}
