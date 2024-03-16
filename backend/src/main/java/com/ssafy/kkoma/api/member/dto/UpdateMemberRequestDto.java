package com.ssafy.kkoma.api.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class UpdateMemberRequestDto {

    private String profileImage;
    private String nickname;
    private String name;
    private String phone;

}
