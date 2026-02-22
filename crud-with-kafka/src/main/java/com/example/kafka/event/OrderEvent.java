package com.example.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private String eventType;
    private Long orderId;
    private String productName;
    private Integer quantity;
    private Double price;
    private String customerEmail;
    private String status;
}
