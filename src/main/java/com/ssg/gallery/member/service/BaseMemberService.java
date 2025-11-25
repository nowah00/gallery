package com.ssg.gallery.member.service;

import com.ssg.gallery.member.entity.Member;
import com.ssg.gallery.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseMemberService implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void save(String name, String loginId, String loginPw) {
        memberRepository.save(new Member(name, loginId, loginPw));
    }

    @Override
    public Member find(String loginId, String loginPw) {
        Optional<Member> member = memberRepository.findByLoginIdAndLoginPw(loginId, loginPw);
        return member.orElse(null); // 만약에 있으면 멤버를 넘겨주고 없다면 null 반환
    }
}
