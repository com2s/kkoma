package com.ssafy.kkoma.api.offer.service;

import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.domain.offer.constant.OfferType;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.offer.repository.OfferRepository;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final MemberService memberService;
    private final ProductService productService;

    public Offer findOfferByOfferId(Long offerId){
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OFFER_NOT_EXISTS));
    }

    public Long createOffer(Long memberId, Long productId){
        Member member = memberService.findMemberByMemberId(memberId);
        Product product = productService.findProductById(productId);

        return offerRepository.save(Offer.builder().member(member)
                .product(product)
                .condition(OfferType.SENT)
                .build())
                .getId();
    }

}
