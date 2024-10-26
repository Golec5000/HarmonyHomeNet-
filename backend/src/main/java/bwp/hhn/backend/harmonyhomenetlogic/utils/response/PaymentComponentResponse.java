package bwp.hhn.backend.harmonyhomenetlogic.utils.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaymentComponentResponse(
        Long id,
        String componentType,
        BigDecimal unitPrice,
        BigDecimal specialMultiplier,
        BigDecimal componentAmount,
        LocalDateTime createdAt

) {
}
