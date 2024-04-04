package com.ssafy.kkoma.global.interceptor;

import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.AuthenticationException;
import com.ssafy.kkoma.global.jwt.constant.TokenType;
import com.ssafy.kkoma.global.jwt.service.TokenManager;
import com.ssafy.kkoma.global.util.AuthorizationHeaderUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // allow preflight
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        String authorizationHeader = request.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

        String token = authorizationHeader.split(" ")[1];
        tokenManager.validateToken(token);

        Claims tokenClaims = tokenManager.getTokenClaims(token);
        String tokenType = tokenClaims.getSubject();
        if(!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        return true;
    }

}
