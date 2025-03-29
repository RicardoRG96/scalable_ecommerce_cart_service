package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ricardo.scalable.ecommerce.platform.cart_service.entities.CartItem;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.CartItemRepository;
import static com.ricardo.scalable.ecommerce.platform.cart_service.services.testData.CartItemServiceImplTestData.*;

@SpringBootTest
public class CartItemServiceImplTest {

    @MockitoBean
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemService cartItemService;

    @Test
    void testFindById() {
        when(cartItemRepository.findById(1L)).thenReturn(createCartItem001());

        Optional<CartItem> cartItem = cartItemService.findById(1L);

        assertAll(
            () -> assertTrue(cartItem.isPresent(), "Cart item should be present"),
            () -> assertEquals(1L, cartItem.orElseThrow().getId(), "Cart item ID should be 1"),
            () -> assertEquals(1L, cartItem.orElseThrow().getProductSku().getId(), "Product SKU ID should be 1"),
            () -> assertEquals(2, cartItem.orElseThrow().getQuantity(), "Cart item quantity should be 2")
        );
    }

}
