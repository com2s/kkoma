package com.ssafy.kkoma.api.product.controller;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.product.service.ViewHistoryService;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.ViewHistory;
import com.ssafy.kkoma.factory.MemberFactory;
import com.ssafy.kkoma.factory.ProductFactory;
import com.ssafy.kkoma.global.util.CustomMockMvcSpringBootTest;
import com.ssafy.kkoma.global.util.RequestUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@CustomMockMvcSpringBootTest
class ViewHistoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RequestUtil requestUtil;

    @Autowired
    MemberFactory memberFactory;

    @Autowired
    ProductFactory productFactory;

    @Autowired
    ViewHistoryService viewHistoryService;

    @Test
    @Transactional
    public void 내가_최근_열람한_글_조회() throws Exception{
        Member seller = memberFactory.createMember();
        Member member = memberFactory.createMember();

        Product product = productFactory.createProduct(seller);

        for (int i = 0; i < 10; i++) {
            viewHistoryService.createViewHistory(member.getId(), product.getId());
        }

        ViewHistory viewHistory = viewHistoryService.createViewHistory(member.getId(), product.getId());

        List<ProductSummary> productSummaryList = new ArrayList<>();
        productSummaryList.add(ProductSummary.fromEntity(viewHistory.getProduct()));

        BasePageResponse<ViewHistory, ProductSummary> myViewHistoriesResponse = new BasePageResponse<>(productSummaryList, 10, 0, 1, 1, 1, true, true, false);

        mockMvc.perform(requestUtil.getRequest("/api/products/viewHistories", member).param("page", "0").param("size", "10"))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        requestUtil.jsonContent(BasePageResponse.class, myViewHistoriesResponse)
                );

    }


}