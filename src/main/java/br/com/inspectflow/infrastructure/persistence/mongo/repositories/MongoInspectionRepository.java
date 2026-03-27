package br.com.inspectflow.infrastructure.persistence.mongo.repositories;

import br.com.inspectflow.domain.inspection.models.Inspection;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface MongoInspectionRepository extends MongoRepository<Inspection, UUID> {
}
