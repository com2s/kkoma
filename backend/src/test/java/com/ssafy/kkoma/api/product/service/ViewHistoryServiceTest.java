package com.ssafy.kkoma.api.product.service;

import static org.junit.jupiter.api.Assertions.*;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.ViewHistory;
import com.ssafy.kkoma.domain.product.repository.ViewHistoryRepository;
import com.ssafy.kkoma.factory.MemberFactory;
import com.ssafy.kkoma.factory.ProductFactory;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class ViewHistoryServiceTest {

    @Autowired
    private ViewHistoryService viewHistoryService;

    @Autowired
    private ViewHistoryRepository viewHistoryRepository;

    @Autowired
    private MemberFactory memberFactory;

    @Autowired
    private ProductFactory productFactory;

    @Test
    @Transactional
    public void 글_조회_시_조회_기록이_생성된다() throws Exception{
        // given
        Member seller = memberFactory.createMember();
        Member member = memberFactory.createMember();

        Product product = productFactory.createProduct(seller);

        List<ViewHistory> beforeViewHistories = viewHistoryRepository.findAllViewHistoriesByMemberIdAndProductId(member.getId(), product.getId());

        // when
        viewHistoryService.createViewHistory(member.getId(), product.getId());

        // then
        List<ViewHistory> afterViewHistories = viewHistoryRepository.findAllViewHistoriesByMemberIdAndProductId(member.getId(), product.getId());
        assertEquals(beforeViewHistories.size() + 1, afterViewHistories.size());
    }

    @Test
    @Transactional
    public void 이미_조회한_글을_다시_조회해도_기록이_새로_생성되지_않는다() throws Exception{
        // given
        Member seller = memberFactory.createMember();
        Member member = memberFactory.createMember();

        Product product = productFactory.createProduct(seller);

        List<ViewHistory> beforeViewHistories = viewHistoryRepository.findAllViewHistoriesByMemberIdAndProductId(member.getId(), product.getId());

        // when
        for (int i = 0; i < 10; i++) {
            viewHistoryService.createViewHistory(member.getId(), product.getId());
        }

        // then
        List<ViewHistory> afterViewHistories = viewHistoryRepository.findAllViewHistoriesByMemberIdAndProductId(member.getId(), product.getId());
        assertEquals(beforeViewHistories.size() + 1, afterViewHistories.size());
    }

    @Test
    @Transactional
    public void 내가_최근_열람한_글_기록을_조회한다() throws Exception{
        // given
        Member seller = memberFactory.createMember();
        Member member = memberFactory.createMember();

        Product product = productFactory.createProduct(seller);

        BasePageResponse<ViewHistory, ProductSummary> beforeMyViewHistories = viewHistoryService.getMyViewHistories(member.getId(), PageRequest.of(0, 10));

        for (int i = 0; i < 10; i++) {
            viewHistoryService.createViewHistory(member.getId(), product.getId());
        }

        // when
        BasePageResponse<ViewHistory, ProductSummary> afterMyViewHistories = viewHistoryService.getMyViewHistories(member.getId(), PageRequest.of(0, 10));

        // then
        assertEquals(beforeMyViewHistories.getTotalElements() + 1, afterMyViewHistories.getTotalElements());
    }
}
