package com.ssafy.kkoma.api.member.dto;

import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MemberInfoResponseDto {

    private Long memberId;
    private String email;
    private String memberName;
    private String profile;
    private Role role;

    public static MemberInfoResponseDto of(Member member) {
        return MemberInfoResponseDto.builder()
                .memberId(member.getId())
                .memberName(member.getName())
                .email(member.getEmail())
                .profile(member.getProfileImage())
                .role(member.getRole())
                .build();
    }

}
