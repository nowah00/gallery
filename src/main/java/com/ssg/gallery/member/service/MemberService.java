package com.ssg.gallery.member.service;

import com.ssg.gallery.member.entity.Member;

public interface MemberService {

    // 회원데이터를 저장하는 메서드, 회원의 이름과 로그인 아이디 패스워드를 매개변수로 받는다
    void save(String name, String loginId, String loginPw);

    // 회원 데이터를 아이디와 패스워드로 조회하는 메서드이다
    Member find(String loginId, String loginPw);

}
