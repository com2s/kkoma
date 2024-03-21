package com.ssafy.kkoma.api.offer.service;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.kkoma.api.member.dto.response.MemberProfileResponse;
import com.ssafy.kkoma.api.offer.dto.response.OfferResponse;
import com.ssafy.kkoma.api.offer.dto.response.OfferTimeResponse;
import com.ssafy.kkoma.api.product.dto.ProductInfoResponse;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.domain.offer.constant.OfferType;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.offer.repository.OfferRepository;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.global.error.ErrorCode;
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

    public Offer findOfferByOfferId(Long offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OFFER_NOT_EXISTS));
    }

    public Long createOffer(Long memberId, Long productId) {
        Member member = memberService.findMemberByMemberId(memberId);
        Product product = productService.findProductById(productId);

        Offer offer = Offer.builder()
            .product(product)
            .status(OfferType.SENT)
            .build();

        offer.setMember(member);

        return offerRepository.save(offer).getId();
    }

    public List<OfferResponse> getOffers(Long productId) {
        List<OfferResponse> offerResponseList = new ArrayList<>();

        List<Offer> offerList = offerRepository.findAllOffersByProductId(productId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OFFER_NOT_EXISTS));

        for (Offer offer : offerList){
            offerResponseList.add(OfferResponse.builder()
                .id(offer.getId())
                .memberProfile(MemberProfileResponse.fromEntity(offer.getMember()))
                .offerTimes(offer.getOfferDetails().stream().map(OfferTimeResponse::fromEntity).toList())
                .build());
        }

        return offerResponseList;
    }

    public Offer updateOfferStatusFromSentToAccepted(Long offerId) {
        Offer offer = offerRepository.findById(offerId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OFFER_NOT_EXISTS));

        offer.setStatus(OfferType.ACCEPTED);

        return offer;
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
