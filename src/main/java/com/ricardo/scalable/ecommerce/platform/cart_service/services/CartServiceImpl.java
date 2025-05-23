package com.ricardo.scalable.ecommerce.platform.cart_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Cart;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.CartRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.UserRepository;
import com.ricardo.scalable.ecommerce.platform.cart_service.repositories.dto.CartDto;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

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
        Optional<User> userOptional = userRepository.findById(cart.getUserId());

        if (userOptional.isPresent()) {
            Cart createdCart = new Cart();
            createdCart.setUser(userOptional.orElseThrow());
            return Optional.of(cartRepository.save(createdCart));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Cart> update(CartDto cart, Long id) {
        Optional<Cart> carOptional = cartRepository.findById(id);
        Optional<User> userOptional = userRepository.findById(cart.getUserId());

        if (carOptional.isPresent() && userOptional.isPresent()) {
            Cart cartToUpdate = carOptional.orElseThrow();
            cartToUpdate.setUser(userOptional.orElseThrow());
            return Optional.of(cartRepository.save(cartToUpdate));
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        cartRepository.deleteById(id);
    }

}
