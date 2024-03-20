package com.ssafy.kkoma.api.member.dto.response;

import com.ssafy.kkoma.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberProfileResponse {

	private Long id;
	private String nickname;
	private String profileImage;

	public static MemberProfileResponse fromEntity(Member member){
		return MemberProfileResponse.builder()
			.id(member.getId())
			.nickname(member.getNickname())
			.profileImage(member.getProfileImage())
			.build();
	}

}
