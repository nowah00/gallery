package com.ssg.gallery.cart.service;

import com.ssg.gallery.cart.dto.CartRead;
import com.ssg.gallery.cart.entity.Cart;
import java.util.List;

public interface CartService {
    // 로그인한 본인(회원)의 장바구니 상품 목록 조회
    List<CartRead> findAll(Integer memberId);

    // 로그인한 본인(회원)의 장바구니 중 선택한 상품 정보 조회
    CartRead find(Integer memberId, Integer itemId);

    // 로그인한 본인(회원)의 장바구니 전상품 삭제
    void removeAll(Integer memberId);

    // 로그인한 본인(회원)의 장바구니 안 선택한 상품 삭제
    void remove(Integer memberId, Integer itemId);

    // 로그인한 본인(회원)의 장바구니 상품 추가
    void save(Cart cart);
}
