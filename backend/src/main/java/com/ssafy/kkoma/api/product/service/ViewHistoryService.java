package com.ssafy.kkoma.api.product.service;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.ViewHistory;
import com.ssafy.kkoma.domain.product.repository.ViewHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewHistoryService {

    private final ViewHistoryRepository viewHistoryRepository;
    private final MemberService memberService;
    private final ProductService productService;

    @Transactional
    public ViewHistory createViewHistory(Long memberId, Long productId) {
        Member member = memberService.findMemberByMemberId(memberId);
        Product product = productService.findProductByProductId(productId);

        ViewHistory viewHistory = viewHistoryRepository.findViewHistoryByMemberIdAndProductId(memberId, productId);

        if (viewHistory == null) {
            return viewHistoryRepository.save(ViewHistory.builder()
                    .member(member)
                    .product(product)
                    .viewedAt(LocalDateTime.now())
                    .build());
        }else {
            viewHistory.setViewedAt(LocalDateTime.now());
            return viewHistory;
        }
    }

    @Transactional
    public BasePageResponse<ViewHistory, ProductSummary> getMyViewHistories(Long memberId, Pageable pageable) {
        Page<ViewHistory> viewHistories = viewHistoryRepository.findByMemberIdOrderByViewedAtDesc(memberId, pageable);
        List<ProductSummary> content = viewHistories.getContent().stream()
                .map(viewHistory -> ProductSummary.fromEntity(viewHistory.getProduct()))
                .toList();
        return new BasePageResponse<>(content, viewHistories);
    }

}
