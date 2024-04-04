package com.ssafy.kkoma.api.member.dto.response;

import com.ssafy.kkoma.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class MemberSummaryResponse {

    private Long memberId;
    private String profileImage;
    private String nickname;
    @Setter
    private String preferredPlace;

    public static MemberSummaryResponse fromEntity(Member member){
        return MemberSummaryResponse.builder()
                .memberId(member.getId())
                .profileImage(member.getProfileImage())
                .nickname(member.getNickname())
                .build();
    }

}
