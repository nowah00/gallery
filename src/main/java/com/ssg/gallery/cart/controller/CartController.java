package com.ssg.gallery.cart.controller;

import com.ssg.gallery.account.helper.AccountHelper;
import com.ssg.gallery.cart.dto.CartRead;
import com.ssg.gallery.cart.dto.CartRequest;
import com.ssg.gallery.cart.service.CartService;
import com.ssg.gallery.item.dto.ItemRead;
import com.ssg.gallery.item.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ItemService itemService;
    private final AccountHelper accountHelper;

    // 로그인한 회원의 장바구니 상품 조회
    @GetMapping("/api/cart/items")
    public ResponseEntity<?> readAll(HttpServletRequest req) {

        // 세션에서 로그인한 회원의 아이디를 가져왔어.
        Integer memberId = accountHelper.getMemberId(req);

        // 로그인한 회원의 장바구니 목록을 가져와.
        List<CartRead> carts = cartService.findAll(memberId);

        // 가져온 장바구니 목록에서 상품아이디만 빼서 리스트로 변환하고,
        List<Integer> itemIds = carts.stream().map(CartRead::getItemId).toList();

        // 상품아이디 리스트로 상품 리스트를 반환해주는거지.
        List<ItemRead> items = itemService.findAll(itemIds);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // 장바구니에다 요청한 상품을 추가
    @PostMapping("/api/carts")
    public ResponseEntity<?> push(HttpServletRequest req, @RequestBody CartRequest cartReq) {

        // 세션에서 로그인한 회원의 아이디를 가져왔어.
        Integer memberId = accountHelper.getMemberId(req);

        // 로그인한 회원의 장바구니에 요청한 상품이 있는지 확인을 해.
        CartRead cart = cartService.find(memberId, cartReq.getItemId());

        // 로그인한 회원의 장바구니에 요청한 상품이 없다면, 해당 상품을 장바구니에 추가해주는거지.
        if (cart == null) {
            cartService.save(cartReq.toEntity(memberId));
        }

        // 하지만 있다면, 그냥 패쓰~
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 장바구니에서 요청한 상품을 삭제
    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity<?> remove(HttpServletRequest req, @PathVariable("itemId") Integer itemId) {

        // 세션에서 로그인한 회원의 아이디를 가져왔어.
        Integer memberId = accountHelper.getMemberId(req);

        // 삭제를 요청한 상품을 회원의 장바구니에서 제거해줘.
        cartService.remove(memberId, itemId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
