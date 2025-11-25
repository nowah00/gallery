package com.ssg.gallery.account.controller;

import com.ssg.gallery.account.dto.AccountJoinRequests;
import com.ssg.gallery.account.dto.AccountLoginRequests;
import com.ssg.gallery.account.helper.AccountHelper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AccountController {

    private final AccountHelper accountHelper;

    // 회원가입
    @PostMapping("/api/account/join")
    public ResponseEntity<?> join(@RequestBody AccountJoinRequests joinReq) {

        // 사용자가 이름, ID, Password 입력하지 않았다면 실패를 반환하고,
        if (joinReq.getName() == null || joinReq.getLoginId() == null || joinReq.getLoginPw() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 모두 입력했다면 join 메서드를 통해서 DB에 등록하는거지.
        accountHelper.join(joinReq);

        // 성공!
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/api/account/login")
    public ResponseEntity<?> login(HttpServletRequest req, HttpServletResponse res, @RequestBody AccountLoginRequests loginReq) { // ⑥

        // 사용자가 ID, Password 입력하지 않았다면 실패를 반환하고,
        if (!StringUtils.hasLength(loginReq.getLoginId()) || !StringUtils.hasLength(loginReq.getLoginPw())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 입력했다면 입력한 ID, Password 를 바탕으로 DB에서 조회를 해.
        String output = accountHelper.login(loginReq, req, res);

        // 조회했는데 없다면 실패!
        if (output == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 있다면 ID와 함께 성공!을 반환 (accountHelper.login 여기에서 세션에 memberId를 쿠키에 등록해놓는다.)
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    // 아이디 체크
    @GetMapping("/api/account/check")
    public ResponseEntity<?> check(HttpServletRequest request) {

        // 세션에 있는 memberId 가 존재의 여부를 판단하는거야.
        return new ResponseEntity<>(accountHelper.isLoggedIn(request), HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/api/account/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        // 세션에 있는 사용자의 ID를 삭제!
        accountHelper.logout(request, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
