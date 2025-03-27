package com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto;

public class CartItemDto {

    private Long cartId;

    private Long productSkuId;

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
