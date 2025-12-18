package service;

package com.techhaven.service;

import com.techhaven.dto.order.OrderRequest;
import com.techhaven.entity.*;
import com.techhaven.repository.OrderRepository;
import com.techhaven.repository.ProductRepository;
import com.techhaven.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional // Ensures Atomicity: If one step fails, everything rolls back
    public void placeOrder(String userEmail, OrderRequest request) {
        
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 1. Initialize Order
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // 2. Process Each Item
        for (var itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Stock Check
            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Reduce Stock
            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            // Calculate Price
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            // Create OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .build();
            
            orderItems.add(orderItem);
        }

        // 3. Finalize Order
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        
        orderRepository.save(order);
    }
}
