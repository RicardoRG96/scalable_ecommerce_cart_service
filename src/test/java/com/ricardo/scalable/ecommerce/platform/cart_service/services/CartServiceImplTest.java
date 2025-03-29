package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
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

    @Test
    void testFindByUserId() {
        when(cartRepository.findByUserId(2L)).thenReturn(createCart002());

        Optional<Cart> cart = cartService.findByUserId(2L);

        assertAll(
            () -> assertTrue(cart.isPresent(), "Cart should be present"),
            () -> assertEquals(2L, cart.orElseThrow().getId(), "Cart ID should be 2"),
            () -> assertEquals("Mateo", cart.orElseThrow().getUser().getFirstName(), "User name should be Mateo")
        );
    }

    @Test
    void testFindAll() {
        when(cartRepository.findAll()).thenReturn(createListOfCarts());

        List<Cart> carts = cartService.findAll();

        assertEquals(4, carts.size(), "There should be 4 carts");
        assertEquals("Ricardo", carts.get(0).getUser().getFirstName(), "First cart user name should be Ricardo");
        assertEquals("Mateo", carts.get(1).getUser().getFirstName(), "Second cart user name should be Mateo");
        assertEquals("Carla", carts.get(2).getUser().getFirstName(), "Third cart user name should be Juan");
        assertEquals("Leo", carts.get(3).getUser().getFirstName(), "Fourth cart user name should be Pedro");
    }

}
