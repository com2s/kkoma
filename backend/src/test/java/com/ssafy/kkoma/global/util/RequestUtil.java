package com.ssafy.kkoma.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.kkoma.domain.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Component
@Slf4j
public class RequestUtil {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public MockHttpServletRequestBuilder request(RequestMethod method, String urlTemplate, Object... uriVariables) {
        if (method == RequestMethod.GET) {
            return getRequest(urlTemplate, uriVariables);
        } else if (method == RequestMethod.POST) {
            return postRequest(urlTemplate, uriVariables);
        } else if (method == RequestMethod.PUT) {
            return putRequest(urlTemplate, uriVariables);
        } else if (method == RequestMethod.DELETE) {
            return deleteRequest(urlTemplate, uriVariables);
        }

        return null;
    }

    public MockHttpServletRequestBuilder requestAuth(Member authenticatedMember, RequestMethod method, String urlTemplate, Object... uriVariables) {
        if (method == RequestMethod.GET) {
            return getRequest(urlTemplate, authenticatedMember, uriVariables);
        } else if (method == RequestMethod.POST) {
            return postRequest(urlTemplate, authenticatedMember, uriVariables);
        } else if (method == RequestMethod.PUT) {
            return putRequest(urlTemplate, authenticatedMember, uriVariables);
        } else if (method == RequestMethod.DELETE) {
            return deleteRequest(urlTemplate, authenticatedMember, uriVariables);
        }

        return null;
    }

    public MockHttpServletRequestBuilder getRequest(String urlTemplate, Object... uriVariables) {
        return get(urlTemplate, uriVariables);
    }

    public MockHttpServletRequestBuilder postRequest(String urlTemplate, Object... uriVariables) {
        return post(urlTemplate, uriVariables);
    }

    public MockHttpServletRequestBuilder putRequest(String urlTemplate, Object... uriVariables) {
        return put(urlTemplate, (Object) uriVariables);
    }

    public MockHttpServletRequestBuilder deleteRequest(String urlTemplate, Object... uriVariables) {
        return delete(urlTemplate, (Object) uriVariables);
    }

    public MockHttpServletRequestBuilder getRequest(String urlTemplate, Member authenticatedMember, Object... uriVariables) {
        String accessToken = jwtUtil.createAccessToken(authenticatedMember.getId(), authenticatedMember.getRole(), jwtUtil.createAccessTokenExpireTime());
        return getRequest(urlTemplate, uriVariables).header("Authorization", "Bearer " + accessToken);
    }

    public MockHttpServletRequestBuilder postRequest(String urlTemplate, Member authenticatedMember, Object... uriVariables) {
        String accessToken = jwtUtil.createAccessToken(authenticatedMember.getId(), authenticatedMember.getRole(), jwtUtil.createAccessTokenExpireTime());
        return postRequest(urlTemplate, uriVariables).header("Authorization", "Bearer " + accessToken);
    }

    public MockHttpServletRequestBuilder putRequest(String urlTemplate, Member authenticatedMember, Object... uriVariables) {
        String accessToken = jwtUtil.createAccessToken(authenticatedMember.getId(), authenticatedMember.getRole(), jwtUtil.createAccessTokenExpireTime());
        return putRequest(urlTemplate, uriVariables).header("Authorization", "Bearer " + accessToken);
    }

    public MockHttpServletRequestBuilder deleteRequest(String urlTemplate, Member authenticatedMember, Object... uriVariables) {
        String accessToken = jwtUtil.createAccessToken(authenticatedMember.getId(), authenticatedMember.getRole(), jwtUtil.createAccessTokenExpireTime());
        return deleteRequest(urlTemplate, uriVariables).header("Authorization", "Bearer " + accessToken);
    }

    public <T> RequestBuilder getRequestWithJson(String urlTemplate, Member member, Class<T> requestType, T request, Object... uriVariables) throws JsonProcessingException {
        checkClass(requestType, request);
        return getRequest(urlTemplate, member)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public <T> RequestBuilder postRequestWithJson(String urlTemplate, Member member, Class<T> requestType, T request, Object... uriVariables) throws JsonProcessingException {
        checkClass(requestType, request);
        return postRequest(urlTemplate, member)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public <T> RequestBuilder putRequestWithJson(String urlTemplate, Member member, Class<T> requestType, T request, Object... uriVariables) throws JsonProcessingException {
        checkClass(requestType, request);
        return putRequest(urlTemplate, member)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    public <T> RequestBuilder deleteRequestWithJson(String urlTemplate, Member member, Class<T> requestType, T request, Object... uriVariables) throws JsonProcessingException {
        checkClass(requestType, request);
        return deleteRequest(urlTemplate, member)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }


    public <T> ResultMatcher jsonContent(Class<T> clazz, T object) throws JsonProcessingException {
        checkClass(clazz, object);
        ApiUtils.ApiResult<T> expectedResponse = ApiUtils.success(object);
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedResponse);
        log.info(expectedJsonResponse);
        return content().json(expectedJsonResponse);
    }

    public <T> ResultMatcher jsonListContent(Class<T> clazz, List<T> object) throws JsonProcessingException {
        checkClass(clazz, object.get(0));
        ApiUtils.ApiResult<List<T>> expectedResponse = ApiUtils.success(object);
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedResponse);
        log.info(expectedJsonResponse);
        return content().json(expectedJsonResponse);
    }

    private <T> void checkClass(Class<T> clazz, T object) {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException("Argument must be an instance of " + clazz.getSimpleName());
        }
    }

}
