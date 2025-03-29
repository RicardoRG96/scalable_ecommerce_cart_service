package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ricardo.scalable.ecommerce.platform.cart_service.entities.Cart;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.CartRepository;
import static com.ricardo.scalable.ecommerce.platform.cart_service.services.testData.CartServiceImplTestData.*;

@SpringBootTest
public class CartServiceImplTest {

    @MockitoBean
    private CartRepository cartRepository;

    @MockitoBean
    private WebClient.Builder client;

    @Autowired
    private CartService cartService;

    @Test
    void testFindById() {
        when(cartRepository.findById(1L)).thenReturn(createCart001());

        Optional<Cart> cart = cartService.findById(1L);

        assertAll(
            () -> assertTrue(cart.isPresent(), "Cart should be present"),
            () -> assertEquals(1L, cart.orElseThrow().getId(), "Cart ID should be 1"),
            () -> assertEquals("Ricardo", cart.orElseThrow().getUser().getFirstName(), "User name should be Ricardo")
        );
    }

}
