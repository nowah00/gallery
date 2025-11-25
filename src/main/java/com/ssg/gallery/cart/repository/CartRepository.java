package com.ssg.gallery.cart.repository;

import com.ssg.gallery.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> { // ①

    // 로그인한 본인(회원)의 장바구니 목록 조회
    List<Cart> findAllByMemberId(Integer memberId); // ②

    // 로그인한 본인(회원)의 장바구니 정보 조회
    Optional<Cart> findByMemberIdAndItemId(Integer memberId, Integer itemId); // ③

    // 로그인한 본인(회원)의 장바구니 전상품 삭제
    void deleteByMemberId(Integer memberId); // ④

    // 로그인한 본인(회원)의 장바구니 선택한 상품 삭제
    void deleteByMemberIdAndItemId(Integer memberId, Integer itemId); // ⑤
}