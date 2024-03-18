package com.ssafy.kkoma.global.resolver.memberinfo;

import com.ssafy.kkoma.domain.member.constant.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoDto {

    private Long memberId;
    private Role role;

}
