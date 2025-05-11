package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.ProductSkuRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartItemDto;
import com.ricardo.scalable.ecommerce.platform.libs_common.exceptions.InsufficientStockException;
import com.ricardo.scalable.ecommerce.platform.libs_common.exceptions.ResourceNotFoundException;

import static com.ricardo.scalable.ecommerce.platform.cart_service.services.testData.CartItemServiceImplTestData.*;
import static com.ricardo.scalable.ecommerce.platform.cart_service.services.testData.utils.ProductSkuTestData.*;

@SpringBootTest
public class StockServiceImplTest {

    @MockitoBean
    private ProductSkuRepository productSkuRepository;

    @Autowired
    private StockService stockService;

    @Test
    void verifyStock_whenQuantityIsGreaterThanStock_shouldThrowInsufficientStockException() {
        CartItemDto cartItem = createCartItemDto();
        cartItem.setQuantity(21);

        when(productSkuRepository.findById(1L)).thenReturn(createProductSku001());

        assertThrows(InsufficientStockException.class, () -> stockService.verifyStock(cartItem));
    }

    @Test
    void verifyStock_whenProductSkuNotFound_shouldThrowResourceNotFoundException() {
        CartItemDto cartItem = createCartItemDto();
        cartItem.setProductSkuId(100L);

        when(productSkuRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> stockService.verifyStock(cartItem));
    }

}
