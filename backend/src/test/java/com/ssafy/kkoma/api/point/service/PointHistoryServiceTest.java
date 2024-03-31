package com.ssafy.kkoma.api.point.service;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.point.constant.PointChangeType;
import com.ssafy.kkoma.domain.point.repository.PointHistoryRepository;
import com.ssafy.kkoma.factory.MemberFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
class PointHistoryServiceTest {

    @Autowired
    PointHistoryService pointHistoryService;

    @Autowired
    PointHistoryRepository pointHistoryRepository;

    @Autowired
    MemberFactory memberFactory;

    @Test
    @Transactional
    void 포인트_변동_및_변동_내역_가져오기() {
        int NUM = 20;

        // given
        Member member = memberFactory.createMember();

        // when
        for (int i = 1; i <= NUM; i++) {
            pointHistoryService.changePoint(member, PointChangeType.CHARGE, i * 100);
        }

        long totalElements = pointHistoryService.getPointHistory(
            member.getId(), PageRequest.of(0, NUM)
        ).getTotalElements();

        // then
        int rowNum = pointHistoryRepository.findByPoint(member.getPoint()).size();
        log.info("생성된 point history는 {}개, 실제 db에 존재하는 row의 개수는 {}개 입니다.", totalElements, rowNum);
        Assertions.assertThat(totalElements).isNotEqualTo(0);
        Assertions.assertThat(totalElements).isEqualTo(rowNum);
    }

}