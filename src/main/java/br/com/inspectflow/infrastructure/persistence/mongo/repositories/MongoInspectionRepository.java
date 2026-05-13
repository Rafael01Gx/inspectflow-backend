package br.com.inspectflow.infrastructure.persistence.mongo.repositories;

import br.com.inspectflow.domain.inspection.models.Inspection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MongoInspectionRepository extends MongoRepository<Inspection, UUID> {


    List<Inspection> findAll();

    @Query(value = "{ 'date' : { $exists: true, $gte: ?0, $lte: ?1 }, 'status' : { $nin: ['COMPLETED', 'CANCELLED'] } }", count = true)
    Long countByDateBetweenAndStatusNotIn(LocalDateTime startDate, LocalDateTime endDate);


    @Query(value = "{ 'status' : 'COMPLETED', 'date' : { $exists: true, $lte: ?0 } }", count = true)
    Long countCompletedAndOnTimeInspections(LocalDateTime now);

    @Query(value = "{ 'date' : { $exists: true, $lte: ?0 } }", count = true)
    Long countAllInspectionsUpTo(LocalDateTime now);
}
