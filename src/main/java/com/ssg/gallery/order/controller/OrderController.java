package com.ssg.gallery.order.controller;

import com.ssg.gallery.account.helper.AccountHelper;
import com.ssg.gallery.order.dto.OrderRead;
import com.ssg.gallery.order.dto.OrderRequest;
import com.ssg.gallery.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class OrderController {

    private final AccountHelper accountHelper;
    private final OrderService orderService;

    // 로그인한 회원의 주문 목록 조회
    @GetMapping("/api/orders")
    public ResponseEntity<?> readAll(HttpServletRequest req) {

        // 세션에서 로그인한 회원의 아이디를 가져왔어.
        Integer memberId = accountHelper.getMemberId(req);

        // 회원의 주문 목록을 리스트로 만들어서
        List<OrderRead> orders = orderService.findAll(memberId);

        // 반환
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // 로그인한 회원의 주문 상세 조회
    @GetMapping("/api/orders/{id}")
    public ResponseEntity<?> read(HttpServletRequest req, @PathVariable Integer id) {

        // 세션에서 로그인한 회원의 아이디를 가져왔어.
        Integer memberId = accountHelper.getMemberId(req);

        // 회원이 요청한 주문 번호를 가지고 해당 주문 정보를 만들어.
        OrderRead order = orderService.find(id, memberId);

        // 없다면 실패!
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 있다면 해당 주문 정보와 성공!을 반환
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // 로그인한 회원의 주문 요청
    @PostMapping("/api/orders")
    public ResponseEntity<?> add(HttpServletRequest req, @RequestBody OrderRequest orderReq) {

        // 세션에서 로그인한 회원의 아이디를 가져왔어.
        Integer memberId = accountHelper.getMemberId(req);

        // 회원 ID, 회원이 입력한 주문 정보를 바탕으로 주문 데이터를 생성해.
        orderService.order(orderReq, memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}