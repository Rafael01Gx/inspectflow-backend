package br.com.inspectflow.infrastructure.persistence.mongo.repositories;

import br.com.inspectflow.domain.checklist.models.Checklist;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface MongoCheckListRepository extends MongoRepository<Checklist, String> {
    Optional<Checklist> findByCode(String code);
}
