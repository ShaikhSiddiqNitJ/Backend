package com.example.kafka.service;

import com.example.kafka.config.KafkaConfig;
import com.example.kafka.event.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {
    
    @KafkaListener(topics = KafkaConfig.ORDER_TOPIC, groupId = "analytics-group")
    public void consumeOrderEvent(OrderEvent event) {
        System.out.println("\n📊 ANALYTICS SERVICE - Received event: " + event.getEventType());
        
        trackOrderMetrics(event);
    }
    
    private void trackOrderMetrics(OrderEvent event) {
        System.out.println("✅ Tracking order metrics");
        System.out.println("   Event: " + event.getEventType());
        System.out.println("   Order ID: " + event.getOrderId());
        System.out.println("   Revenue: $" + (event.getPrice() * event.getQuantity()));
        System.out.println("   Product: " + event.getProductName());
    }
}
