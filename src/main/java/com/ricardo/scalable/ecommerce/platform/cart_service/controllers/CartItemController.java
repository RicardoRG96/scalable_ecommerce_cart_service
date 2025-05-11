package com.ricardo.scalable.ecommerce.platform.cart_service.controllers;

import static com.ricardo.scalable.ecommerce.platform.libs_common.validation.RequestBodyValidation.validation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.scalable.ecommerce.platform.cart_service.entities.CartItem;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartItemDto;
import com.ricardo.scalable.ecommerce.platform.cart_service.services.CartItemService;
import com.ricardo.scalable.ecommerce.platform.cart_service.services.StockService;

import jakarta.validation.Valid;

@RestController
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private StockService stockService;

    @GetMapping("/cart-items/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        Optional<CartItem> cartItemOptional = cartItemService.findById(id);

        if (cartItemOptional.isPresent()) {
            return ResponseEntity.ok(cartItemOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cart-items/cart/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItemsByCartId(@PathVariable Long cartId) {
        Optional<List<CartItem>> cartItemsOptional = cartItemService.findByCartId(cartId);
        boolean isEmpty = cartItemsOptional.orElseThrow().isEmpty();

        if (cartItemsOptional.isPresent() && !isEmpty) {
            return ResponseEntity.ok(cartItemsOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cart-items/product-sku/{productSkuId}")
    public ResponseEntity<List<CartItem>> getCartItemsByProductSkuId(@PathVariable Long productSkuId) {
        Optional<List<CartItem>> cartItemsOptional = cartItemService.findByProductSkuId(productSkuId);
        boolean isEmpty = cartItemsOptional.orElseThrow().isEmpty();

        if (cartItemsOptional.isPresent() && !isEmpty) {
            return ResponseEntity.ok(cartItemsOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cart-items")
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        List<CartItem> cartItems = cartItemService.findAll();
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/cart-items")
    public ResponseEntity<?> createCartItem(
        @Valid @RequestBody CartItemDto cartItem,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        stockService.verifyStock(cartItem);
        Optional<CartItem> createdCartItemOptional = cartItemService.save(cartItem);

        if (createdCartItemOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCartItemOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/cart-items/{id}")
    public ResponseEntity<?> updateCartItem(
        @Valid @RequestBody CartItemDto cartItem,
        BindingResult result,
        @PathVariable Long id
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        stockService.verifyStock(cartItem);
        Optional<CartItem> updatedCartItemOptional = cartItemService.update(cartItem, id);

        if (updatedCartItemOptional.isPresent()) {
            return ResponseEntity.ok(updatedCartItemOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/cart-items/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
        Optional<CartItem> cartItemOptional = cartItemService.findById(id);
        if (cartItemOptional.isPresent()) {
            cartItemService.delete(id);
            return ResponseEntity.noContent().build();   
        }
        return ResponseEntity.notFound().build();
    }
}
