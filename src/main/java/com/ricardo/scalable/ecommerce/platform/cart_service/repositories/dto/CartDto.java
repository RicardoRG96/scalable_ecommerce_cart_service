package com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto;

public class CartDto {

    private Long userId;

    public CartDto() {
    }

    public CartDto(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
