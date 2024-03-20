package com.ssafy.kkoma.api.offer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.kkoma.api.deal.service.DealService;
import com.ssafy.kkoma.api.member.dto.response.MemberProfileResponse;
import com.ssafy.kkoma.api.offer.dto.response.OfferResponse;
import com.ssafy.kkoma.api.offer.dto.response.OfferTimeResponse;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.deal.request.DealTimeRequest;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.domain.offer.constant.OfferType;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.offer.entity.OfferDetail;
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
    private final DealService dealService;

    public Offer findOfferByOfferId(Long offerId){
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OFFER_NOT_EXISTS));
    }

    public Long createOffer(Long memberId, Long productId){
        Member member = memberService.findMemberByMemberId(memberId);
        Product product = productService.findProductById(productId);

        return offerRepository.save(Offer.builder().member(member)
                .product(product)
                .status(OfferType.SENT)
                .build())
                .getId();
    }

    public List<OfferResponse> getOffers(Long productId){
        List<OfferResponse> offerResponseList = new ArrayList<>();

        List<Offer> offerList = offerRepository.findAllOffersByProductId(productId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OFFER_NOT_EXISTS));

        for (Offer offer : offerList){
            System.out.println("###" + offer.getMember().getId());
            offerResponseList.add(OfferResponse.builder()
                .id(offer.getId())
                .memberProfile(MemberProfileResponse.fromEntity(offer.getMember()))
                .offerTimes(offer.getOfferDetails().stream().map(OfferTimeResponse::fromEntity).toList())
                .build());
        }

        return offerResponseList;
    }

    public Offer acceptOffer(Long offerId, DealTimeRequest dealTimeRequest){
        Offer offer = findOfferByOfferId(offerId);

        offer.setStatus(OfferType.ACCEPTED);
        // offer.getProduct().setStatus(ProductType.MID);
        offer.getProduct().updateStatus(ProductType.MID);
        dealService.createDeal(offer, dealTimeRequest); // TODO: deal entity 말고 변환해줘야 되는 것 같은데

        return offer;
    }

}
