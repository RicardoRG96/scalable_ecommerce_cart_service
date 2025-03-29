package com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto;

import jakarta.validation.constraints.NotNull;

public class CartDto {

    @NotNull
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
