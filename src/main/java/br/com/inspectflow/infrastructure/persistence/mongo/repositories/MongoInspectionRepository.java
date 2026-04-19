package br.com.inspectflow.infrastructure.persistence.mongo.repositories;

import br.com.inspectflow.domain.inspection.models.Inspection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MongoInspectionRepository extends MongoRepository<Inspection, UUID> {

    long count();
    List<Inspection> findAll();
    @Query("{ 'date' : { $gte: ?0, $lte: ?1 }, 'status' : { $nin: ['COMPLETED', 'CANCELLED'] } }")
    long countByDateBetweenAndStatusNotIn(LocalDateTime startDate, LocalDateTime endDate);


    @Query("{ 'status' : 'COMPLETED', 'date' : { $lte: ?0 } }")
    long countCompletedAndOnTimeInspections(LocalDateTime now);

    @Query("{ 'date' : { $lte: ?0 } }")
    long countAllInspectionsUpTo(LocalDateTime now);
}
