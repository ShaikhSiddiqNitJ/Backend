package com.example.kafka.service;

import com.example.kafka.config.KafkaConfig;
import com.example.kafka.event.OrderEvent;
import com.example.kafka.model.Order;
import com.example.kafka.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    
    public Order createOrder(Order order) {
        System.out.println("💾 Saving order to DATABASE...");
        Order savedOrder = orderRepository.save(order);
        
        // Publish event to Kafka
        OrderEvent event = new OrderEvent(
            "ORDER_CREATED",
            savedOrder.getId(),
            savedOrder.getProductName(),
            savedOrder.getQuantity(),
            savedOrder.getPrice(),
            savedOrder.getCustomerEmail(),
            savedOrder.getStatus()
        );
        
        System.out.println("📤 Publishing ORDER_CREATED event to Kafka...");
        kafkaTemplate.send(KafkaConfig.ORDER_TOPIC, event);
        
        return savedOrder;
    }
    
    public List<Order> getAllOrders() {
        System.out.println("📦 Fetching all orders from DATABASE");
        return orderRepository.findAll();
    }
    
    public Order getOrderById(Long id) {
        System.out.println("📦 Fetching order " + id + " from DATABASE");
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    
    public Order updateOrder(Long id, Order orderDetails) {
        System.out.println("✏️ Updating order " + id + " in DATABASE");
        Order order = getOrderById(id);
        
        order.setProductName(orderDetails.getProductName());
        order.setQuantity(orderDetails.getQuantity());
        order.setPrice(orderDetails.getPrice());
        order.setCustomerEmail(orderDetails.getCustomerEmail());
        order.setStatus(orderDetails.getStatus());
        
        Order updatedOrder = orderRepository.save(order);
        
        // Publish update event
        OrderEvent event = new OrderEvent(
            "ORDER_UPDATED",
            updatedOrder.getId(),
            updatedOrder.getProductName(),
            updatedOrder.getQuantity(),
            updatedOrder.getPrice(),
            updatedOrder.getCustomerEmail(),
            updatedOrder.getStatus()
        );
        
        System.out.println("📤 Publishing ORDER_UPDATED event to Kafka...");
        kafkaTemplate.send(KafkaConfig.ORDER_TOPIC, event);
        
        return updatedOrder;
    }
    
    public void deleteOrder(Long id) {
        System.out.println("🗑️ Deleting order " + id + " from DATABASE");
        Order order = getOrderById(id);
        orderRepository.deleteById(id);
        
        // Publish delete event
        OrderEvent event = new OrderEvent(
            "ORDER_DELETED",
            order.getId(),
            order.getProductName(),
            order.getQuantity(),
            order.getPrice(),
            order.getCustomerEmail(),
            "DELETED"
        );
        
        System.out.println("📤 Publishing ORDER_DELETED event to Kafka...");
        kafkaTemplate.send(KafkaConfig.ORDER_TOPIC, event);
    }
}
