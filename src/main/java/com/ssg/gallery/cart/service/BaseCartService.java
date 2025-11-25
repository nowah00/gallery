package com.ssg.gallery.cart.service;

import com.ssg.gallery.cart.dto.CartRead;
import com.ssg.gallery.cart.entity.Cart;
import com.ssg.gallery.cart.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaseCartService implements CartService {

    private final CartRepository cartRepository;

    @Override
    public List<CartRead> findAll(Integer memberId) {
        return cartRepository.findAllByMemberId(memberId).stream().map(Cart::toRead).toList();
    }

    @Override
    public CartRead find(Integer memberId, Integer itemId) {
        Optional<Cart> cartOptional = cartRepository.findByMemberIdAndItemId(memberId, itemId);
        return cartOptional.map(Cart::toRead).orElse(null);
    }

    @Override
    @Transactional
    public void removeAll(Integer memberId) {
        cartRepository.deleteByMemberId(memberId);

    }

    @Override
    @Transactional
    public void remove(Integer memberId, Integer itemId) {
        cartRepository.deleteByMemberIdAndItemId(memberId, itemId);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}
