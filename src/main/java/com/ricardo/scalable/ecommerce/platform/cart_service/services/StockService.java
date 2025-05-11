package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartItemDto;

public interface StockService {

    void verifyStock(CartItemDto cartItemDto);

}
