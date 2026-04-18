package br.com.inspectflow.application.order.dto;

import java.util.UUID;

public record CancelOrderRequest(
        UUID id,
        String justification
) {
}
