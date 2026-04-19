package br.com.inspectflow.application.dashboard.dto;

public record StockLowQuantityDto(
    Long id,
    String name,
    Integer quantity,
    Integer minQuantity
) {}
