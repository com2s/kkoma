package com.ssafy.kkoma.api.member.dto.response;

import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoResponse {

    private Long id;
    private String profileImage;
    private String email;
    private String nickname;
    private String name;
    private String phone;
    private Role role;

    public static MemberInfoResponse fromEntity(Member member) {
        return MemberInfoResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .role(member.getRole())
                .phone(member.getPhone())
                .build();
    }

}
