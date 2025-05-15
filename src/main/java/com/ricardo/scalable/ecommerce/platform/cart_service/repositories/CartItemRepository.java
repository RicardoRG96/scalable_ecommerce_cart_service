package com.ricardo.scalable.ecommerce.platform.cart_service.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.CartItem;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {

    Optional<List<CartItem>> findByCartId(Long cartId);

    Optional<List<CartItem>> findByProductSkuId(Long productSkuId);

}
