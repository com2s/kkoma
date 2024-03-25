package com.ssafy.kkoma.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.kkoma.domain.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Component
public class RequestUtil {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public MockHttpServletRequestBuilder getRequest(String urlTemplate) {
        return get(urlTemplate);
    }

    public MockHttpServletRequestBuilder postRequest(String urlTemplate) {
        return post(urlTemplate);
    }

    public MockHttpServletRequestBuilder putRequest(String urlTemplate) {
        return put(urlTemplate);
    }

    public MockHttpServletRequestBuilder deleteRequest(String urlTemplate) {
        return delete(urlTemplate);
    }

    public MockHttpServletRequestBuilder getRequest(String urlTemplate, Member member) {
        String accessToken = jwtUtil.createAccessToken(member.getId(), member.getRole(), jwtUtil.createAccessTokenExpireTime());
        return get(urlTemplate).header("Authorization", "Bearer " + accessToken);
    }

    public MockHttpServletRequestBuilder postRequest(String urlTemplate, Member member) {
        String accessToken = jwtUtil.createAccessToken(member.getId(), member.getRole(), jwtUtil.createAccessTokenExpireTime());
        return post(urlTemplate).header("Authorization", "Bearer " + accessToken);
    }

    public MockHttpServletRequestBuilder putRequest(String urlTemplate, Member member) {
        String accessToken = jwtUtil.createAccessToken(member.getId(), member.getRole(), jwtUtil.createAccessTokenExpireTime());
        return put(urlTemplate).header("Authorization", "Bearer " + accessToken);
    }

    public MockHttpServletRequestBuilder deleteRequest(String urlTemplate, Member member) {
        String accessToken = jwtUtil.createAccessToken(member.getId(), member.getRole(), jwtUtil.createAccessTokenExpireTime());
        return delete(urlTemplate).header("Authorization", "Bearer " + accessToken);
    }

    public <T> RequestBuilder getRequestWithJson(String urlTemplate, Member member, Class<T> requestType, T request) throws JsonProcessingException {
        checkClass(requestType, request);
        String accessToken = jwtUtil.createAccessToken(member.getId(), member.getRole(), jwtUtil.createAccessTokenExpireTime());
        return get(urlTemplate)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public <T> RequestBuilder postRequestWithJson(String urlTemplate, Member member, Class<T> requestType, T request) throws JsonProcessingException {
        checkClass(requestType, request);
        String accessToken = jwtUtil.createAccessToken(member.getId(), member.getRole(), jwtUtil.createAccessTokenExpireTime());
        return post(urlTemplate)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public <T> RequestBuilder putRequestWithJson(String urlTemplate, Member member, Class<T> requestType, T request) throws JsonProcessingException {
        checkClass(requestType, request);
        String accessToken = jwtUtil.createAccessToken(member.getId(), member.getRole(), jwtUtil.createAccessTokenExpireTime());
        return put(urlTemplate)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public <T> RequestBuilder deleteRequestWithJson(String urlTemplate, Member member, Class<T> requestType, T request) throws JsonProcessingException {
        checkClass(requestType, request);
        String accessToken = jwtUtil.createAccessToken(member.getId(), member.getRole(), jwtUtil.createAccessTokenExpireTime());
        return delete(urlTemplate)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }


    public <T> ResultMatcher jsonContent(Class<T> clazz, T object) throws JsonProcessingException {
        checkClass(clazz, object);
        ApiUtils.ApiResult<T> expectedResponse = ApiUtils.success(object);
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedResponse);
        return content().json(expectedJsonResponse);
    }

    private <T> void checkClass(Class<T> clazz, T object) {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException("Argument must be an instance of " + clazz.getSimpleName());
        }
    }

}
