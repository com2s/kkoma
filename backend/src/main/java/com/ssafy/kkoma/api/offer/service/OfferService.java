package com.ssafy.kkoma.api.offer.service;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.kkoma.api.deal.dto.request.DecideOfferRequest;
import com.ssafy.kkoma.api.deal.service.DealService;
import com.ssafy.kkoma.api.offer.dto.response.OfferResponse;
import com.ssafy.kkoma.api.offer.dto.response.DecideOfferResponse;
import com.ssafy.kkoma.api.point.service.PointService;
import com.ssafy.kkoma.api.product.dto.ProductInfoResponse;
import com.ssafy.kkoma.api.point.service.PointHistoryService;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.domain.offer.constant.OfferType;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.offer.repository.OfferRepository;

import com.ssafy.kkoma.domain.point.constant.PointChangeType;
import com.ssafy.kkoma.domain.point.entity.PointHistory;

import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final MemberService memberService;
    private final ProductService productService;
    private final PointService pointService;
    private final PointHistoryService pointHistoryService;
    private final DealService dealService;

    public Offer findOfferByOfferId(Long offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OFFER_NOT_EXISTS));
    }

    public List<Offer> findAllOfferByProductId(Long productId) {
        return offerRepository.findAllOffersByProductId(productId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OFFER_NOT_EXISTS));
    }

    public Long createOffer(Long memberId, Long productId) {
        Member member = memberService.findMemberByMemberId(memberId);
        Product product = productService.findProductByProductId(productId);

        if (member.getPoint().getBalance() < product.getPrice()) {
            throw new BusinessException(ErrorCode.POINT_NOT_ENOUGH);
        }
        member.getPoint().subBalance(product.getPrice());

        pointHistoryService.createPointHistory(PointHistory.builder()
            .point(member.getPoint())
            .amount(product.getPrice())
            .pointChangeType(PointChangeType.USE)
            .balanceAfterChange(member.getPoint().getBalance())
            .build());

        Offer offer = Offer.builder()
            .product(product)
            .status(OfferType.SENT)
            .build();

        offer.setMember(member);

        return offerRepository.save(offer).getId();
    }

    public List<OfferResponse> getOffers(Long productId) {
        List<OfferResponse> offerResponseList = new ArrayList<>();
        List<Offer> offerList = findAllOfferByProductId(productId);

        for (Offer offer : offerList) {
            offerResponseList.add(OfferResponse.fromEntity(offer));
        }

        return offerResponseList;
    }

    public DecideOfferResponse acceptOffer(Long offerId, DecideOfferRequest decideOfferRequest) {
        Offer acceptedOffer = findOfferByOfferId(offerId); // 수락한 offer
        Product product = acceptedOffer.getProduct();
        if (!product.getStatus().equals(ProductType.SALE)) { // 수락한 offer가 이미 있다
            throw new EntityNotFoundException(ErrorCode.INVALID_ACCEPT);
        }

        Deal acceptedDeal = null; // offer을 수락해서 만들어진 deal
        List<Offer> offerList = findAllOfferByProductId(product.getId());
        for (Offer offer : offerList) {
            // 수락한 offer에 대해서 accept 처리
            if (offer.getId() == offerId) {
                offer.updateStatus(OfferType.ACCEPTED);
                offer.getProduct().updateStatus(ProductType.PROGRESS);
                acceptedDeal = dealService.createDeal(offer, decideOfferRequest);
            }
            // 나머지 offer에 대해서 deny 처리, 선입금한 포인트 반환
            else {
                offer.updateStatus(OfferType.REJECTED);
                Member rejectedBuyer = offer.getMember(); // 거절당한 구매희망자
                rejectedBuyer.getPoint().addBalance(product.getPrice());

                pointHistoryService.createPointHistory(PointHistory.builder()
                    .point(rejectedBuyer.getPoint())
                    .amount(product.getPrice())
                    .pointChangeType(PointChangeType.USE)
                    .balanceAfterChange(rejectedBuyer.getPoint().getBalance())
                    .build());
            }
        }

        return DecideOfferResponse.fromEntity(acceptedOffer, acceptedDeal);
    }

    public List<ProductInfoResponse> getNotProgressOfferingProducts(Long memberId) {
        List<Offer> offers = memberService.getMyOffers(memberId);

        List<ProductInfoResponse> productInfoResponses = new ArrayList<>();
        for (Offer offer : offers) {
            if (OfferType.SENT.equals(offer.getStatus()) || OfferType.CANCELLED.equals(offer.getStatus())) {
                productInfoResponses.add(productService.getProductInfoResponse(offer.getProduct().getId()));
            }
            else if (OfferType.ACCEPTED.equals(offer.getStatus()) && ProductType.SOLD.equals(offer.getProduct().getStatus())) {
                productInfoResponses.add(productService.getProductInfoResponse(offer.getProduct().getId()));
            }
        }

        return productInfoResponses;
    }

    public List<ProductInfoResponse> getProgressOfferingProducts(Long memberId) {
        List<Offer> offers = memberService.getMyOffers(memberId);

        List<ProductInfoResponse> productInfoResponses = new ArrayList<>();
        for (Offer offer : offers) {
            if (OfferType.ACCEPTED.equals(offer.getStatus()) && ProductType.PROGRESS.equals(offer.getProduct().getStatus())) {
                productInfoResponses.add(productService.getProductInfoResponse(offer.getProduct().getId()));
            }
        }

        return productInfoResponses;
    }

}
