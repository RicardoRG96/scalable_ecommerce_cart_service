package com.ricardo.scalable.ecommerce.platform.cart_service.controllers;

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

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Cart;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartDto;
import com.ricardo.scalable.ecommerce.platform.cart_service.services.CartService;
import static com.ricardo.scalable.ecommerce.platform.libs_common.validation.RequestBodyValidation.*;

import jakarta.validation.Valid;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getById(@PathVariable Long id) {
        Optional<Cart> cartOptional = cartService.findById(id);

        if (cartOptional.isPresent()) {
            return ResponseEntity.ok(cartOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getByUserId(@PathVariable Long userId) {
        Optional<Cart> cartOptional = cartService.findByUserId(userId);

        if (cartOptional.isPresent()) {
            return ResponseEntity.ok(cartOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Cart>> getAll() {
        List<Cart> carts = cartService.findAll();
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/")
    public ResponseEntity<?> createCart(
        @Valid @RequestBody CartDto cart,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<Cart> createdCartOptional = cartService.save(cart);
        if (createdCartOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCartOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCart(
        @Valid @RequestBody CartDto cart,
        BindingResult result,
        @PathVariable Long id
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<Cart> updatedCartOptional = cartService.update(cart, id);
        if (updatedCartOptional.isPresent()) {
            return ResponseEntity.ok(updatedCartOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        Optional<Cart> cartOptional = cartService.findById(id);
        if (cartOptional.isPresent()) {
            cartService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
