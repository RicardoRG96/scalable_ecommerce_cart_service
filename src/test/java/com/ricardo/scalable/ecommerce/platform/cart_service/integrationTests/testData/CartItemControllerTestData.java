package com.ricardo.scalable.ecommerce.platform.cart_service.integrationTests.testData;

import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartItemDto;

public class CartItemControllerTestData {

    public static CartItemDto createCartItemDto() {
        CartItemDto cartItem = new CartItemDto();
        cartItem.setCartId(2L);
        cartItem.setProductSkuId(6L);
        cartItem.setQuantity(2);

        return cartItem;
    }

    public static CartItemDto createCartItemDtoToUpdate() {
        CartItemDto cartItem = new CartItemDto();
        cartItem.setCartId(1L);
        cartItem.setProductSkuId(1L);
        cartItem.setQuantity(3);

        return cartItem;
    }

}
