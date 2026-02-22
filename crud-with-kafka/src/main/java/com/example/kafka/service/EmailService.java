package com.example.kafka.service;

import com.example.kafka.config.KafkaConfig;
import com.example.kafka.event.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @KafkaListener(topics = KafkaConfig.ORDER_TOPIC, groupId = "email-group")
    public void consumeOrderEvent(OrderEvent event) {
        System.out.println("\n📧 EMAIL SERVICE - Received event: " + event.getEventType());
        
        switch (event.getEventType()) {
            case "ORDER_CREATED":
                sendOrderConfirmationEmail(event);
                break;
            case "ORDER_UPDATED":
                sendOrderUpdateEmail(event);
                break;
            case "ORDER_DELETED":
                sendOrderCancellationEmail(event);
                break;
        }
    }
    
    private void sendOrderConfirmationEmail(OrderEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getCustomerEmail());
            message.setSubject("Order Confirmation - Order #" + event.getOrderId());
            message.setText(
                "Dear Customer,\n\n" +
                "Your order has been confirmed!\n\n" +
                "Order Details:\n" +
                "Order ID: " + event.getOrderId() + "\n" +
                "Product: " + event.getProductName() + "\n" +
                "Quantity: " + event.getQuantity() + "\n" +
                "Price: $" + event.getPrice() + "\n" +
                "Total: $" + (event.getPrice() * event.getQuantity()) + "\n\n" +
                "Thank you for your order!\n\n" +
                "Best regards,\n" +
                "Your Store Team"
            );
            
            mailSender.send(message);
            System.out.println("✅ Email sent successfully to: " + event.getCustomerEmail());
        } catch (Exception e) {
            System.out.println("❌ Failed to send email: " + e.getMessage());
        }
    }
    
    private void sendOrderUpdateEmail(OrderEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getCustomerEmail());
            message.setSubject("Order Updated - Order #" + event.getOrderId());
            message.setText(
                "Dear Customer,\n\n" +
                "Your order has been updated.\n\n" +
                "Order ID: " + event.getOrderId() + "\n" +
                "Product: " + event.getProductName() + "\n" +
                "Status: " + event.getStatus() + "\n\n" +
                "Best regards,\n" +
                "Your Store Team"
            );
            
            mailSender.send(message);
            System.out.println("✅ Update email sent to: " + event.getCustomerEmail());
        } catch (Exception e) {
            System.out.println("❌ Failed to send email: " + e.getMessage());
        }
    }
    
    private void sendOrderCancellationEmail(OrderEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getCustomerEmail());
            message.setSubject("Order Cancelled - Order #" + event.getOrderId());
            message.setText(
                "Dear Customer,\n\n" +
                "Your order has been cancelled.\n\n" +
                "Order ID: " + event.getOrderId() + "\n" +
                "Product: " + event.getProductName() + "\n\n" +
                "If you have any questions, please contact us.\n\n" +
                "Best regards,\n" +
                "Your Store Team"
            );
            
            mailSender.send(message);
            System.out.println("✅ Cancellation email sent to: " + event.getCustomerEmail());
        } catch (Exception e) {
            System.out.println("❌ Failed to send email: " + e.getMessage());
        }
    }
}
