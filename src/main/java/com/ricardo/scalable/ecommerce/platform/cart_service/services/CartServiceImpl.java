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
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.CartRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartDto;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private WebClient.Builder client;

    @Value("${user-service.base-url}")
    private String userServiceBaseUrl;

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Optional<Cart> findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public List<Cart> findAll() {
        return (List<Cart>) cartRepository.findAll();
    }

    @Override
    public Optional<Cart> save(CartDto cart) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", cart.getUserId().toString());

            Optional<User> userOptional = client.build()
                    .get()
                    .uri(userServiceBaseUrl + "/{id}", params)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(User.class)
                    .blockOptional();

            if (userOptional.isPresent()) {
                Cart createdCart = new Cart();
                createdCart.setUser(userOptional.orElseThrow());
                return Optional.of(cartRepository.save(createdCart));
            }
            return Optional.empty();
        } catch (WebClientResponseException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Cart> update(CartDto cart, Long id) {
        try {
            Optional<Cart> carOptional = cartRepository.findById(id);
            Map<String, String> params = new HashMap<>();
            params.put("id", cart.getUserId().toString());
            Optional<User> userOptional = client.build()
                .get()
                .uri(userServiceBaseUrl + "/{id}", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class)
                .blockOptional();

            if (carOptional.isPresent() && userOptional.isPresent()) {
                Cart cartToUpdate = carOptional.orElseThrow();
                cartToUpdate.setUser(userOptional.orElseThrow());
                return Optional.of(cartRepository.save(cartToUpdate));
            }
            return Optional.empty();
        } catch (WebClientResponseException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        cartRepository.deleteById(id);
    }

}
