package com.ssafy.kkoma.api.point.service;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.point.dto.PointSummaryResponse;
import com.ssafy.kkoma.api.point.repository.PointRepository;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.repository.MemberRepository;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointServiceTest {

    @Autowired
    private PointService pointService;

    @Autowired
    PointRepository pointRepository;

    @Autowired
    MemberService memberService; // todo mocking

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 존재하는_포인트_아이디로_포인트_찾기() {
        // given
        Point point = new Point();
        Point savedPoint = pointRepository.save(point);

        // when
        Point foundPoint = pointService.findPointByPointId(savedPoint.getId());

        // then
        assertEquals(savedPoint.getId(), foundPoint.getId());
    }

    @Test
    void 존재하지_않는_포인트_아이디로_포인트_찾기() {
        // given
        Long NONEXISTENT_POINT_ID = 100000000L;

        // when&then
        assertThrows(EntityNotFoundException.class, () -> pointService.findPointByPointId(100000000L));
    }

    @Test
    @Transactional
    void 포인트_요약_얻기() {
        // given
        Member member = Member.builder()
                .name("kim")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build();

        Member savedMember = memberRepository.save(member);
        Point point = memberService.createPoint(savedMember.getId());

        int BALANCE = 100;
        point.addBalance(BALANCE);

        // when
        PointSummaryResponse pointSummary = pointService.getPointSummary(savedMember.getId());

        // then
        assertEquals(BALANCE, pointSummary.getBalance());
    }

}
