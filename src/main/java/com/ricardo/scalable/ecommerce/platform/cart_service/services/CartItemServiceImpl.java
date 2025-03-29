package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ricardo.scalable.ecommerce.platform.cart_service.entities.Cart;
import com.ricardo.scalable.ecommerce.platform.cart_service.entities.CartItem;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.CartItemRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.CartRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartItemDto;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private WebClient.Builder client;

    @Value("${product-service.base-url}")
    private String productServiceBaseUrl;

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public Optional<List<CartItem>> findByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    @Override
    public Optional<List<CartItem>> findByProductSkuId(Long productSkuId) {
        return cartItemRepository.findByProductSkuId(productSkuId);
    }

    @Override
    public List<CartItem> findAll() {
        return (List<CartItem>) cartItemRepository.findAll();
    }

    @Override
    public Optional<CartItem> save(CartItemDto cartItem) {
        try {
            Optional<Cart> cartOptional = cartRepository.findById(cartItem.getCartId());
            Map<String, String> params = new HashMap<>();
            params.put("id", cartItem.getProductSkuId().toString());

            Optional<ProductSku> productSkuOptional = client.build()
                    .get()
                    .uri(productServiceBaseUrl + "/product-sku/{id}", params)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ProductSku.class)
                    .blockOptional();

            if (productSkuOptional.isPresent() && cartOptional.isPresent()) {
                CartItem createdCartItem = new CartItem();
                createdCartItem.setCart(cartOptional.orElseThrow());
                createdCartItem.setProductSku(productSkuOptional.orElseThrow());
                createdCartItem.setQuantity(cartItem.getQuantity());
                return Optional.of(cartItemRepository.save(createdCartItem));
            }
            return Optional.empty();
        } catch (WebClientResponseException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<CartItem> update(CartItemDto cartItem, Long id) {
        try {
            Optional<CartItem> cartItemOptional = cartItemRepository.findById(id);
            Optional<Cart> cartOptional = cartRepository.findById(cartItem.getCartId());
            Map<String, String> params = new HashMap<>();
            params.put("id", cartItem.getProductSkuId().toString());

            Optional<ProductSku> productSkuOptional = client.build()
                    .get()
                    .uri(productServiceBaseUrl + "/product-sku/{id}", params)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ProductSku.class)
                    .blockOptional();

            if (
                cartItemOptional.isPresent() && 
                productSkuOptional.isPresent() && 
                cartOptional.isPresent()
            ) {
                CartItem cartItemToUpdate = cartItemOptional.orElseThrow();
                cartItemToUpdate.setCart(cartOptional.orElseThrow());
                cartItemToUpdate.setProductSku(productSkuOptional.orElseThrow());
                cartItemToUpdate.setQuantity(cartItem.getQuantity());
                return Optional.of(cartItemRepository.save(cartItemToUpdate));
            }
            return Optional.empty();
        } catch (WebClientResponseException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }

}
