package com.enterprise.jms.consumer.model;

import java.math.BigDecimal;

public record TransactionRequest(
        String transactionId,
        String accountId,
        BigDecimal amount,
        String currency
) {}
