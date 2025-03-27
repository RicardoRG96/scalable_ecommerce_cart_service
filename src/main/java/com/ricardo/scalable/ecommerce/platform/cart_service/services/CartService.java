package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.cart_service.entities.Cart;

public interface CartService {

    Optional<Cart> findById(Long id);

    Optional<Cart> findByUserId(Long userId);

    List<Cart> findAll();

    Optional<Cart> save(CartDto cart);

    Optional<Cart> update(CartDto cart, Long id);

    void delete(Long id);

}
