package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.OrderItemRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.ProductSkuRepository;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.OrderItem;
import com.ricardo.scalable.ecommerce.platform.libs_common.exceptions.InsufficientStockException;
import com.ricardo.scalable.ecommerce.platform.libs_common.exceptions.ResourceNotFoundException;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void verifyStock(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        if (orderItems.isEmpty()) {
            throw new ResourceNotFoundException("No order items found for order ID: " + orderId);
        }

        for (OrderItem orderItem : orderItems) {
            Long productSkuId = orderItem.getProductSku().getId();
            int quantity = orderItem.getQuantity();

            productSkuRepository.findById(productSkuId)
                .ifPresentOrElse(productSku -> {
                    if (productSku.getStock() < quantity) {
                        throw new InsufficientStockException("Not enough stock for product SKU: " + productSkuId);
                    }
                }, () -> {
                    throw new ResourceNotFoundException("Product SKU not found: " + productSkuId);
                });
        }
    }

}
