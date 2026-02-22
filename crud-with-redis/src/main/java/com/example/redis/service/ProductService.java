package com.example.redis.service;

import com.example.redis.model.Product;
import com.example.redis.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Cacheable(value = "products")
    public List<Product> getAllProducts() {
        System.out.println("📦 Fetching all products from DATABASE");
        return productRepository.findAll();
    }

    @Cacheable(value = "products", key = "#id")
    public Optional<Product> getProductById(Long id) {
        System.out.println("📦 Fetching product " + id + " from DATABASE");
        return productRepository.findById(id);
    }

    @CachePut(value = "products", key = "#result.id")
    public Product createProduct(Product product) {
        System.out.println("💾 Saving product to DATABASE");
        return productRepository.save(product);
    }

    @CachePut(value = "products", key = "#id")
    public Product updateProduct(Long id, Product productDetails) {
        System.out.println("✏️ Updating product " + id + " in DATABASE");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        
        return productRepository.save(product);
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        System.out.println("🗑️ Deleting product " + id + " from DATABASE");
        productRepository.deleteById(id);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void clearCache() {
        System.out.println("🧹 Clearing all cache");
    }
}
