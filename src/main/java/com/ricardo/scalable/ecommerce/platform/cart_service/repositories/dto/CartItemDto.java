package com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartItemDto {

    @NotNull
    private Long cartId;

    @NotNull
    private Long productSkuId;

    @Min(1)
    @NotNull
    private int quantity;

    public CartItemDto() {
    }

    public CartItemDto(Long cartId, Long productSkuId, int quantity) {
        this.cartId = cartId;
        this.productSkuId = productSkuId;
        this.quantity = quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
