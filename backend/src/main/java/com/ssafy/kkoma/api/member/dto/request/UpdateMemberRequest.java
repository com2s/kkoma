package com.ssafy.kkoma.api.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateMemberRequest {

    private String profileImage;
    private String nickname;
    private String name;
    private String phone;

}
