package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.CartItem;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartItemDto;

public interface CartItemService {

    Optional<CartItem> findById(Long id);

    Optional<List<CartItem>> findByCartId(Long cartId);

    Optional<List<CartItem>> findByProductSkuId(Long productSkuId);

    List<CartItem> findAll();

    Optional<CartItem> save(CartItemDto cartItem);

    Optional<CartItem> update(CartItemDto cartItem, Long id);

    void delete(Long id);

}
