package com.ssg.gallery.member.repository;

import com.ssg.gallery.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    // 멤버의 아이디와 패스워드로 해당 멤버를 조회하겠다는 메서드
    // Optional 쓰는 이유 : 회원이 없을 경우 Null 처리하기 위함
    Optional<Member> findByLoginIdAndLoginPw(String loginId, String loginPw);
}
