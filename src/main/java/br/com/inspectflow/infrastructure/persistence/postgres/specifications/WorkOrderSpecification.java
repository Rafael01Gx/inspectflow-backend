package br.com.inspectflow.infrastructure.persistence.postgres.specifications;

import br.com.inspectflow.application.order.dto.SearchOrderFilterRequest;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.user.models.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class WorkOrderSpecification {

    public static Specification<WorkOrder> byFilter(SearchOrderFilterRequest filter) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // equipmentName
            if (filter.equipmentName() != null && !filter.equipmentName().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("equipmentName")),
                        "%" + filter.equipmentName().toLowerCase() + "%"
                ));
            }

            // status
            if (filter.orderStatus() != null) {
                predicates.add(cb.equal(root.get("orderStatus"), filter.orderStatus()));
            }

            // prioridade
            if (filter.orderPriority() != null) {
                predicates.add(cb.equal(root.get("orderPriority"), filter.orderPriority()));
            }

            // assignee
            if (filter.assignee() != null && !filter.assignee().isBlank()) {

                Join<WorkOrder, User> userJoin = root.join("assignee");

                predicates.add(cb.like(
                        cb.lower(userJoin.get("name")), // ⚠️ campo do User
                        "%" + filter.assignee().toLowerCase() + "%"
                ));
            }
            // data de conclusão
            if (filter.completionDate() != null) {
                predicates.add(cb.equal(
                        root.get("completionDate"),
                        filter.completionDate()
                ));
            }

            // criado a partir de
            if (filter.createdAt() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("createdAt"),
                        filter.createdAt()
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}