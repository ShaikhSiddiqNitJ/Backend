package com.example.kafka.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    
    public static final String ORDER_TOPIC = "order-events";
    public static final String EMAIL_TOPIC = "email-notifications";
    public static final String SMS_TOPIC = "sms-notifications";
}
