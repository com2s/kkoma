package com.ssafy.kkoma.api.kid.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.kkoma.api.kid.dto.response.KidSummaryResponse;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.factory.KidFactory;
import com.ssafy.kkoma.factory.MemberFactory;
import com.ssafy.kkoma.global.util.CustomSpringBootTest;
import com.ssafy.kkoma.global.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@Slf4j
@CustomSpringBootTest
class KidControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RequestUtil requestUtil;

    @Autowired
    MemberFactory memberFactory;

    @Autowired
    KidFactory kidFactory;

    @Test
    void 로그인한_회원의_모든_아이에_대한_요약_정보를_얻기() throws Exception {

        Member savedMember = memberFactory.createMember();
        Kid savedKid = kidFactory.createKid(savedMember);

        KidSummaryResponse kidSummaryResponse = KidSummaryResponse.fromEntity(savedKid);

        mockMvc.perform(requestUtil.getRequest("/api/kids/summary", savedMember))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        requestUtil.jsonListContent(KidSummaryResponse.class, List.of(kidSummaryResponse))
                );

        log.info(kidSummaryResponse.toString());
    }

    @Test
    void 아이_요약_정보를_아이디로_얻기() throws Exception {

        Member savedMember = memberFactory.createMember();
        Kid savedKid = kidFactory.createKid(savedMember);

        KidSummaryResponse kidSummaryResponse = KidSummaryResponse.fromEntity(savedKid);

        mockMvc.perform(requestUtil.getRequest("/api/kids/summary/{kidId}", savedMember, savedKid.getId()))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        requestUtil.jsonContent(KidSummaryResponse.class, kidSummaryResponse)
                );

        log.info(kidSummaryResponse.toString());
    }

    @Test
    void updateKid() {
    }

    @Test
    void testUpdateKid() {
    }
}