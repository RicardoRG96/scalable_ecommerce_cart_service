package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Cart;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.CartRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.UserRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartDto;

import static com.ricardo.scalable.ecommerce.platform.cart_service.services.testData.CartServiceImplTestData.*;
import static com.ricardo.scalable.ecommerce.platform.cart_service.services.testData.utils.UserTestData.*;

@SpringBootTest
public class CartServiceImplTest {

    @MockitoBean
    private CartRepository cartRepository;

    @MockitoBean
    private UserRepository userRepository;

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

    @Test
    void testSave() {
        when(userRepository.findById(1L)).thenReturn(createUser001());
        when(cartRepository.save(any())).thenReturn(createCartCreationResponse());

        CartDto cartDto = new CartDto(1L);
        Optional<Cart> cart = cartService.save(cartDto);

        assertAll(
            () -> assertTrue(cart.isPresent(), "Cart should be present"),
            () -> assertEquals(5L, cart.orElseThrow().getId(), "Cart ID should be 5"),
            () -> assertEquals("Ricardo", cart.orElseThrow().getUser().getFirstName(), "User name should be Ricardo")
        );
    }

    @Test
    void testUpdate() {
        when(cartRepository.findById(1L)).thenReturn(createCart001());
        when(userRepository.findById(3L)).thenReturn(createUser003());
        when(cartRepository.save(any())).thenReturn(createCartUpdateResponse());

        CartDto cartDto = new CartDto(3L);
        Optional<Cart> cart = cartService.update(cartDto, 1L);

        assertAll(
            () -> assertTrue(cart.isPresent(), "Cart should be present"),
            () -> assertEquals(1L, cart.orElseThrow().getId(), "Cart ID should be 1"),
            () -> assertEquals("Carla", cart.orElseThrow().getUser().getFirstName(), "User name should be Carla")
        );
    }

    @Test
    void testDelete() {
        when(cartRepository.findById(1L)).thenReturn(createCart001());
        doNothing().when(cartRepository).deleteById(1L);

        cartService.delete(1L);

        verify(cartRepository, times(1)).deleteById(1L);
    }

}
