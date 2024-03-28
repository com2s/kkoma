package com.ssafy.kkoma.api.point.service;

import com.ssafy.kkoma.api.point.dto.PointSummaryResponse;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.point.constant.PointChangeType;
import com.ssafy.kkoma.domain.point.entity.PointHistory;
import com.ssafy.kkoma.domain.point.repository.PointRepository;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointService {
    
    private final PointRepository pointRepository;
    private final MemberService memberService;
    private final ProductService productService;
    private final PointHistoryService pointHistoryService;

    public Point findPointByPointId(Long pointId) {
        return pointRepository.findById(pointId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POINT_NOT_EXISTS));
    }

    public PointSummaryResponse getPointSummary(Long memberId) {
        return PointSummaryResponse.builder()
                .balance(memberService.getPointBalance(memberId))
                .build();
    }

    public void comparePointsToPrice(Long memberId, Long productId) {
        int balance = memberService.getPointBalance(memberId);
        int price = productService.findProductByProductId(productId).getPrice();

        if (balance < price) {
            throw new BusinessException(ErrorCode.POINT_NOT_ENOUGH);
        }
    }

    public void changePoint(Member member, PointChangeType pointChangeType, int amount) {
        Point point = member.getPoint();

        if (PointChangeType.GET.equals(pointChangeType)) point.addBalance(amount);
        else if (PointChangeType.USE.equals(pointChangeType)) point.subBalance(amount);

        PointHistory pointHistory = PointHistory.builder()
                .point(point)
                .amount(amount)
                .pointChangeType(pointChangeType)
                .balanceAfterChange(point.getBalance())
                .build();
        pointHistoryService.createPointHistory(pointHistory);
    }
}
