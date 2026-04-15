package com.enterprise.jms.consumer.ops;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/jms/ops")
public class OpsController {
    private final String brokerUrl;

    public OpsController(@Value("${spring.activemq.broker-url}") String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        return Map.of("service", "jms-mq-consumer", "status", "UP", "brokerUrl", brokerUrl);
    }
}
