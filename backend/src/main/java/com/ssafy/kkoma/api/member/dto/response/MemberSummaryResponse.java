package com.ssafy.kkoma.api.member.dto.response;

import com.ssafy.kkoma.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSummaryResponse {

    private Long id;
    private String profileImage;
    private String nickname;
    private String preferredPlace;

    public static MemberSummaryResponse fromEntity(Member member){
        return MemberSummaryResponse.builder()
                .id(member.getId())
                .profileImage(member.getProfileImage())
                .nickname(member.getNickname())
                .build();
    }

}
