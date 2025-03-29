package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import com.ricardo.scalable.ecommerce.platform.cart_service.entities.CartItem;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.CartItemRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.CartRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.ProductSkuRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartItemDto;

import static com.ricardo.scalable.ecommerce.platform.cart_service.services.testData.CartItemServiceImplTestData.*;
import static com.ricardo.scalable.ecommerce.platform.cart_service.services.testData.CartServiceImplTestData.createCart001;
import static com.ricardo.scalable.ecommerce.platform.cart_service.services.testData.utils.ProductSkuTestData.createProductSku001;

@SpringBootTest
public class CartItemServiceImplTest {

    @MockitoBean
    private CartItemRepository cartItemRepository;

    @MockitoBean
    private CartRepository cartRepository;

    @MockitoBean
    private ProductSkuRepository productSkuRepository;

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

    @Test
    void testFindByCartId() {
        when(cartItemRepository.findByCartId(1L)).thenReturn(createListOfCartItemsByCartId1());

        Optional<List<CartItem>> cartItems = cartItemService.findByCartId(1L);

        assertAll(
            () -> assertTrue(cartItems.isPresent(), "Cart items should be present"),
            () -> assertEquals(2, cartItems.orElseThrow().size(), "Cart items size should be 5"),
            () -> assertEquals(1L, cartItems.orElseThrow().get(0).getCart().getId(), "Cart ID should be 1"),
            () -> assertEquals(1L, cartItems.orElseThrow().get(0).getProductSku().getId(), "Product SKU ID should be 1"),
            () -> assertEquals(2, cartItems.orElseThrow().get(0).getQuantity(), "Cart item quantity should be 2"),
            () -> assertEquals(1L, cartItems.orElseThrow().get(1).getCart().getId(), "Cart ID should be 2"),
            () -> assertEquals(2L, cartItems.orElseThrow().get(1).getProductSku().getId(), "Product SKU ID should be 2"),
            () -> assertEquals(3, cartItems.orElseThrow().get(1).getQuantity(), "Cart item quantity should be 3")
        );
    }

    @Test
    void testFindByProductSkuId() {
        when(cartItemRepository.findByProductSkuId(2L)).thenReturn(createListOfCartItemsByProductSkuId());

        Optional<List<CartItem>> cartItems = cartItemService.findByProductSkuId(2L);

        assertAll(
            () -> assertTrue(cartItems.isPresent(), "Cart items should be present"),
            () -> assertEquals(2, cartItems.orElseThrow().size(), "Cart items size should be 2"),
            () -> assertEquals(1L, cartItems.orElseThrow().get(0).getCart().getId(), "Cart ID should be 1"),
            () -> assertEquals(2L, cartItems.orElseThrow().get(0).getProductSku().getId(), "Product SKU ID should be 2"),
            () -> assertEquals(3, cartItems.orElseThrow().get(0).getQuantity(), "Cart item quantity should be 3"),
            () -> assertEquals(2L, cartItems.orElseThrow().get(1).getCart().getId(), "Cart ID should be 2"),
            () -> assertEquals(2L, cartItems.orElseThrow().get(1).getProductSku().getId(), "Product SKU ID should be 2"),
            () -> assertEquals(1, cartItems.orElseThrow().get(1).getQuantity(), "Cart item quantity should be 1")
        );
    }

    @Test
    void testFindAll() {
        when(cartItemRepository.findAll()).thenReturn(createListOfCartItems());

        List<CartItem> cartItems = cartItemService.findAll();

        assertAll(
            () -> assertEquals(5, cartItems.size(), "There should be 5 cart items")
        );
    }

    @Test
    void testSave() {
        when(cartRepository.findById(1L)).thenReturn(createCart001());
        when(productSkuRepository.findById(1L)).thenReturn(createProductSku001());
        when(cartItemRepository.save(any())).thenReturn(createCartItemCreationResponse());
        
        CartItemDto cartItemDto = createCartItemDto();
        Optional<CartItem> cartItem = cartItemService.save(cartItemDto);

        assertAll(
            () -> assertTrue(cartItem.isPresent(), "Cart item should be present"),
            () -> assertEquals(6L, cartItem.orElseThrow().getId(), "Cart item ID should be 6"),
            () -> assertEquals(6L, cartItem.orElseThrow().getProductSku().getId(), "Product SKU ID should be 6"),
            () -> assertEquals(4, cartItem.orElseThrow().getQuantity(), "Cart item quantity should be 2")
        );
    }

    @Test
    void testUpdate() {
        when(cartItemRepository.findById(1L)).thenReturn(createCartItem001());
        when(cartRepository.findById(1L)).thenReturn(createCart001());
        when(productSkuRepository.findById(1L)).thenReturn(createProductSku001());
        when(cartItemRepository.save(any())).thenReturn(createCartItemUpdateResponse());

        CartItemDto cartItemDto = createCartItemDto();
        Optional<CartItem> cartItem = cartItemService.update(cartItemDto, 1L);

        assertAll(
            () -> assertTrue(cartItem.isPresent(), "Cart item should be present"),
            () -> assertEquals(1L, cartItem.orElseThrow().getId(), "Cart item ID should be 1"),
            () -> assertEquals(1L, cartItem.orElseThrow().getProductSku().getId(), "Product SKU ID should be 1"),
            () -> assertEquals(4, cartItem.orElseThrow().getQuantity(), "Cart item quantity should be 2")
        );
    }

    @Test
    void testDelete() {
        when(cartItemRepository.findById(1L)).thenReturn(createCartItem001());
        doNothing().when(cartItemRepository).deleteById(1L);

        cartItemService.delete(1L);

        verify(cartItemRepository, times(1)).deleteById(1L);
    }

}
