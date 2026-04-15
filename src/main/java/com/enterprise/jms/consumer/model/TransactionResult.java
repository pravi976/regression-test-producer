package com.enterprise.jms.consumer.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionResult(
        String transactionId,
        String accountId,
        BigDecimal amount,
        String currency,
        String decision,
        int attempts,
        OffsetDateTime processedAt
) {}
