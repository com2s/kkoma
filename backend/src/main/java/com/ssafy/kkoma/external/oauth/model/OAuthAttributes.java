package com.ssafy.kkoma.external.oauth.model;

import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OAuthAttributes {

    private String name;
    private String email;
    private String profile;
    private MemberType memberType;

    public Member toMemberEntity(MemberType memberType, Role role) {
        return Member.builder()
                .name(name)
                .email(email)
                .memberType(memberType)
                .profileImage(profile)
                .role(role)
                .build();
    }

}
