package com.ssafy.kkoma.api.login.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.global.jwt.dto.JwtTokenDto;
import lombok.*;

import java.util.Date;

// todo request, response로 분할
public class OauthLoginDto {

    @Getter @Setter
    public static class Request {

        private String memberType;

    }

    @Getter @Setter
    @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Response {

        private String grantType;
        private String accessToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date accessTokenExpireTime;
        private String refreshToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date refreshTokenExpireTime;

        private boolean memberInfoCompleted;
        private boolean kidInfoCompleted;

        public static Response fromEntity(JwtTokenDto jwtTokenDto, Member member) {

            return Response.builder()
                    .grantType(jwtTokenDto.getGrantType())
                    .accessToken(jwtTokenDto.getAccessToken())
                    .accessTokenExpireTime(jwtTokenDto.getAccessTokenExpireTime())
                    .refreshToken(jwtTokenDto.getRefreshToken())
                    .refreshTokenExpireTime(jwtTokenDto.getRefreshTokenExpireTime())
                    .memberInfoCompleted(member.isMemberInfoCompleted())
                    .kidInfoCompleted(member.isKidInfoCompleted())
                    .build();

        }

    }

}
