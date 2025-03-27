package com.ricardo.scalable.ecommerce.platform.cart_service.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.cart_service.entities.Cart;

public interface CartRepository extends CrudRepository<Cart, Long> {

    Optional<Cart> findByUserId();

}
