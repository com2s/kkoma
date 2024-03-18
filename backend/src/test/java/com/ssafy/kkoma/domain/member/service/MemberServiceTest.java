package com.ssafy.kkoma.domain.member.service;

import com.ssafy.kkoma.api.member.dto.response.MemberInfoResponse;
import com.ssafy.kkoma.api.member.dto.request.UpdateMemberRequest;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.point.entity.Point;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원_정보_수정() {

        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();

        Member savedMember = memberRepository.save(member);

        UpdateMemberRequest updateMemberRequest = UpdateMemberRequest.builder()
                        .name("lee")
                        .build();

        MemberInfoResponse memberInfoResponse = memberService.updateMemberInfo(savedMember.getId(), updateMemberRequest);

        assertEquals("lee", memberInfoResponse.getName());
    }

    @Test
    void 포인트_생성() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();

        Member savedMember = memberRepository.save(member);

        // when
        Point point = memberService.createPoint(savedMember.getId());
        Member foundMember = memberService.findMemberByMemberId(savedMember.getId());

        // then
        assertEquals(foundMember.getPoint().getId(), point.getId());
    }

}
