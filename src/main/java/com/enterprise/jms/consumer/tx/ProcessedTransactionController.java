package com.enterprise.jms.consumer.tx;

import com.enterprise.jms.consumer.model.TransactionResult;
import com.enterprise.jms.consumer.processing.TransactionProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jms/processed")
public class ProcessedTransactionController {
    private final TransactionProcessor processor;

    public ProcessedTransactionController(TransactionProcessor processor) {
        this.processor = processor;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResult> get(@PathVariable String transactionId) {
        TransactionResult result = processor.find(transactionId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
