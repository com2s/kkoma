package com.ssafy.kkoma.global.util;

import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.global.jwt.constant.TokenType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtAuthenticationExtension implements BeforeEachCallback {

    @Value("${token.secret}")
    private String tokenSecret = "Q4NSl604sgyHJj1qwEkR3ycUeR4uUAt7WJraD7EN3O9DVM4yyYuHxMEbSF4XXyYJkal13eqgB0F7Bq4H";

    @Value("${token.access-token-expiration-time}")
    private String accessTokenExpirationMs = "900000";

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        String jwtToken = generateTestToken(1L, Role.USER, createAccessTokenExpireTime());

        context.getTestInstance().ifPresent(
                testInstance -> {
                    if (testInstance instanceof JwtAuthenticationProvider) {
                        ((JwtAuthenticationProvider) testInstance).setJwtToken(jwtToken);
                    }
                }
        );
    }

    public String generateTestToken(Long memberId, Role role, Date expirationTime) {
        String accessToken = Jwts.builder()
                .setSubject(TokenType.ACCESS.name())
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                .claim("memberId", memberId)
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("typ", "JWT")
                .compact();
        return accessToken;
    }

    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationMs));
    }

}
