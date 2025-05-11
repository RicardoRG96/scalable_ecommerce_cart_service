package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.ProductSkuRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartItemDto;
import com.ricardo.scalable.ecommerce.platform.libs_common.exceptions.InsufficientStockException;
import com.ricardo.scalable.ecommerce.platform.libs_common.exceptions.ResourceNotFoundException;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Override
    public void verifyStock(CartItemDto cartItemDto) {
        Long productSkuId = cartItemDto.getProductSkuId();
        int quantity = cartItemDto.getQuantity();

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
