package com.enterprise.jms.consumer.processing;

import com.enterprise.jms.consumer.model.TransactionRequest;
import com.enterprise.jms.consumer.model.TransactionResult;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TransactionProcessor {
    private final Map<String, TransactionResult> processed = new ConcurrentHashMap<>();
    private final Map<String, AtomicInteger> attempts = new ConcurrentHashMap<>();

    @JmsListener(destination = "banking.tx.requests")
    public void handle(TransactionRequest request) {
        AtomicInteger attemptCounter = attempts.computeIfAbsent(request.transactionId(), ignored -> new AtomicInteger(0));
        int attempt = attemptCounter.incrementAndGet();
        processed.computeIfAbsent(
                request.transactionId(),
                ignored -> new TransactionResult(
                        request.transactionId(),
                        request.accountId(),
                        request.amount(),
                        request.currency(),
                        decisionFor(request),
                        attempt,
                        OffsetDateTime.now()
                )
        );
    }

    public TransactionResult find(String transactionId) {
        return processed.get(transactionId);
    }

    private String decisionFor(TransactionRequest request) {
        if (request.amount() == null) {
            return "REJECTED";
        }
        if (request.amount().signum() < 0) {
            return "REJECTED";
        }
        if (request.amount().compareTo(BigDecimal.valueOf(10000)) > 0) {
            return "FLAGGED";
        }
        return "APPROVED";
    }
}
