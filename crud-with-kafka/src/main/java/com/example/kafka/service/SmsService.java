package com.example.kafka.service;

import com.example.kafka.config.KafkaConfig;
import com.example.kafka.event.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    
    @KafkaListener(topics = KafkaConfig.ORDER_TOPIC, groupId = "sms-group")
    public void consumeOrderEvent(OrderEvent event) {
        System.out.println("\n📱 SMS SERVICE - Received event: " + event.getEventType());
        
        if ("ORDER_CREATED".equals(event.getEventType())) {
            sendOrderSms(event);
        }
    }
    
    private void sendOrderSms(OrderEvent event) {
        System.out.println("✅ Sending SMS notification");
        System.out.println("   Message: Your order #" + event.getOrderId() + " has been placed successfully!");
        System.out.println("   Product: " + event.getProductName());
    }
}
