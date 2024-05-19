package tech.gustavomedina.product.dto.client;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderProductStock(
        UUID orderId,
        String productCode,
        Integer quantity
    ) {
}
