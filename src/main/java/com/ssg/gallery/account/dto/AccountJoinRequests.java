package com.ssg.gallery.account.dto;

import lombok.Getter;
import lombok.Setter;

// 회원가입을 요청할 때 사용하는 DTO
@Getter
@Setter
public class AccountJoinRequests {
    private String name;
    private String loginId;
    private String loginPw;
}
