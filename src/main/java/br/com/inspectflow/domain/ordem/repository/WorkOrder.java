package br.com.inspectflow.domain.ordem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkOrder extends JpaRepository<WorkOrder, UUID> {
}
