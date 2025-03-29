package com.ricardo.scalable.ecommerce.platform.cart_service.services.testData;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.cart_service.entities.Cart;
import com.ricardo.scalable.ecommerce.platform.cart_service.entities.CartItem;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;

import static com.ricardo.scalable.ecommerce.platform.cart_service.services.testData.CartServiceImplTestData.*;
import static com.ricardo.scalable.ecommerce.platform.cart_service.services.testData.utils.ProductSkuTestData.*;

public class CartItemServiceImplTestData {

    public static List<CartItem> createListOfCartItems() {
        CartItem cartItem1 = createCartItem001().orElseThrow();
        CartItem cartItem2 = createCartItem002().orElseThrow();
        CartItem cartItem3 = createCartItem003().orElseThrow();
        CartItem cartItem4 = createCartItem004().orElseThrow();
        CartItem cartItem5 = createCartItem005().orElseThrow();

        return List.of(cartItem1, cartItem2, cartItem3, cartItem4, cartItem5);
    }

    public static Optional<CartItem> createCartItem001() {
        CartItem cartItem = new CartItem();
        Cart cart = createCart001().orElseThrow();
        ProductSku productSku = createProductSku001().orElseThrow();

        cartItem.setId(1L);
        cartItem.setCart(cart);
        cartItem.setProductSku(productSku);
        cartItem.setQuantity(2);
        cartItem.setCreatedAt(Timestamp.from(Instant.now()));
        cartItem.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(cartItem);
    }

    public static Optional<CartItem> createCartItem002() {
        CartItem cartItem = new CartItem();
        Cart cart = createCart001().orElseThrow();
        ProductSku productSku = createProductSku002().orElseThrow();

        cartItem.setId(2L);
        cartItem.setCart(cart);
        cartItem.setProductSku(productSku);
        cartItem.setQuantity(3);
        cartItem.setCreatedAt(Timestamp.from(Instant.now()));
        cartItem.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(cartItem);
    }

    public static Optional<CartItem> createCartItem003() {
        CartItem cartItem = new CartItem();
        Cart cart = createCart002().orElseThrow();
        ProductSku productSku = createProductSku003().orElseThrow();

        cartItem.setId(3L);
        cartItem.setCart(cart);
        cartItem.setProductSku(productSku);
        cartItem.setQuantity(1);
        cartItem.setCreatedAt(Timestamp.from(Instant.now()));
        cartItem.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(cartItem);
    }

    public static Optional<CartItem> createCartItem004() {
        CartItem cartItem = new CartItem();
        Cart cart = createCart002().orElseThrow();
        ProductSku productSku = createProductSku004().orElseThrow();

        cartItem.setId(4L);
        cartItem.setCart(cart);
        cartItem.setProductSku(productSku);
        cartItem.setQuantity(5);
        cartItem.setCreatedAt(Timestamp.from(Instant.now()));
        cartItem.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(cartItem);
    }

    public static Optional<CartItem> createCartItem005() {
        CartItem cartItem = new CartItem();
        Cart cart = createCart003().orElseThrow();
        ProductSku productSku = createProductSku005().orElseThrow();

        cartItem.setId(5L);
        cartItem.setCart(cart);
        cartItem.setProductSku(productSku);
        cartItem.setQuantity(4);
        cartItem.setCreatedAt(Timestamp.from(Instant.now()));
        cartItem.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(cartItem);
    }

    public static Optional<List<CartItem>> createListOfCartItemsByCartId1() {
        CartItem cartItem1 = createCartItem001().orElseThrow();
        CartItem cartItem2 = createCartItem002().orElseThrow();

        return Optional.of(List.of(cartItem1, cartItem2));
    }

    public static Optional<List<CartItem>> createListOfCartItemsByCartId2() {
        CartItem cartItem3 = createCartItem003().orElseThrow();
        CartItem cartItem4 = createCartItem004().orElseThrow();

        return Optional.of(List.of(cartItem3, cartItem4));
    }

    public static Optional<List<CartItem>> createListOfCartItemsByCartId3() {
        CartItem cartItem5 = createCartItem005().orElseThrow();

        return Optional.of(List.of(cartItem5));
    }

    public static Optional<CartItem> createCartItemCreationResponse() {
        CartItem cartItem = new CartItem();
        Cart cart = createCart004().orElseThrow();
        ProductSku productSku = createProductSku006().orElseThrow();

        cartItem.setId(6L);
        cartItem.setCart(cart);
        cartItem.setProductSku(productSku);
        cartItem.setQuantity(2);
        cartItem.setCreatedAt(Timestamp.from(Instant.now()));
        cartItem.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(cartItem);
    }

    public static Optional<CartItem> createCartItemUpdateResponse() {
        CartItem cartItem = createCartItem001().orElseThrow();
        cartItem.setQuantity(4);

        return Optional.of(cartItem);
    }

}
