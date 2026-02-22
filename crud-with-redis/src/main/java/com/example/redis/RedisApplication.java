package com.example.redis;

import com.example.redis.model.Product;
import com.example.redis.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class RedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            long currentCount = repository.count();
            if (currentCount < 100) {
                System.out.println("🚀 Inserting " + (100 - currentCount) + " more products...");
                for (int i = 1; i <= 100; i++) {
                    Product product = new Product();
                    product.setName("Product " + i);
                    product.setDescription("Description for product " + i);
                    product.setPrice(100.0 + (i * 10));
                    product.setQuantity(10 + i);
                    repository.save(product);
                }
                System.out.println("✅ Total products: " + repository.count());
            } else {
                System.out.println("ℹ️ Database already has " + repository.count() + " products");
            }
        };
    }
}
