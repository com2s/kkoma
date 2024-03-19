package com.ssafy.kkoma.api.offer.service;

import com.ssafy.kkoma.api.offer.dto.OfferTimeRequest;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.offer.entity.OfferDetail;
import com.ssafy.kkoma.domain.offer.repository.OfferDetailRepository;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OfferDetailService {

    private final OfferDetailRepository offerDetailRepository;
    private final OfferService offerService;

    public void createOfferDetail(Long offerId, List<OfferTimeRequest> offerTimeRequestList){
        Offer offer = offerService.findOfferByOfferId(offerId);

        for (OfferTimeRequest offerTime :  offerTimeRequestList) {
            offerDetailRepository.save(OfferDetail.builder()
                    .offer(offer)
                    .offerDate(offerTime.getOfferDate())
                    .startTime(offerTime.getStartTime())
                    .endTime(offerTime.getEndTime())
                    .build());
        }
    }

    public List<OfferDetail> findAllOfferDetailsByOfferId(Long offerId){
        return offerDetailRepository.findAllOfferDetailsByOfferId(offerId)
                        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.OFFER_DETAIL_NOT_EXISTS));
    }

}
