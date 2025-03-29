package com.ricardo.scalable.ecommerce.platform.cart_service.integrationTests.testData;

import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartDto;

public class CartControllerTestData {

    public static CartDto createCartDto() {
        CartDto cart = new CartDto();
        cart.setUserId(3L);

        return cart;
    }

    public static CartDto createCartDtoToUpdate() {
        CartDto cart = new CartDto();
        cart.setUserId(2L);

        return cart;
    }

}
