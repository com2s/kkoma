package com.ssafy.kkoma.api.member.service;

import com.ssafy.kkoma.api.member.dto.response.MemberInfoResponse;
import com.ssafy.kkoma.api.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberInfoServiceTest {

    @Autowired
    MemberInfoService memberInfoService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원_정보_얻기() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();

        Member savedMember = memberRepository.save(member);

        // when
        MemberInfoResponse memberInfo = memberInfoService.getMemberInfo(savedMember.getId());

        // then
        assertEquals(savedMember.getId(), memberInfo.getId());
        assertEquals(savedMember.getName(), memberInfo.getName());
        assertEquals(savedMember.getRole(), memberInfo.getRole());
    }

    @Test
    void 존재하지않는_회원_정보_얻기() {
        // given
        Long NON_EXISTENT_MEMBER_ID = 100000000L;

        // when&then
        assertThrows(EntityNotFoundException.class, () -> memberInfoService.getMemberInfo(NON_EXISTENT_MEMBER_ID));
    }

    @Test
    void 회원_요약_정보_얻기() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();

        Member savedMember = memberRepository.save(member);

        // when
        MemberSummaryResponse memberSummary = memberInfoService.getMemberSummary(savedMember.getId());

        // then
        assertEquals(savedMember.getNickname(), memberSummary.getNickname());
        assertEquals(savedMember.getProfileImage(), memberSummary.getProfileImage());
    }

    @Test
    void 존재하지않는_회원_요약_정보_얻기() {
        // given
        Long NON_EXISTENT_MEMBER_ID = 100000000L;

        // when&then
        assertThrows(EntityNotFoundException.class, () -> memberInfoService.getMemberSummary(NON_EXISTENT_MEMBER_ID));
    }

}
