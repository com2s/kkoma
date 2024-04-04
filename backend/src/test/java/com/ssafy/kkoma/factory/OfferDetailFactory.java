package com.ssafy.kkoma.factory;

import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.offer.entity.OfferDetail;
import com.ssafy.kkoma.domain.offer.repository.OfferDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OfferDetailFactory {

    @Autowired
    OfferDetailRepository offerDetailRepository;

    public OfferDetail createOfferDetail(Offer offer) {

        OfferDetail offerDetail = OfferDetail.builder()
                .offerDate(LocalDate.now())
                .startTime(LocalTime.now())
                .endTime(LocalTime.now())
                .build();
        offerDetail.setOffer(offer);
        return offerDetailRepository.save(offerDetail);
    }

    public List<OfferDetail> createOfferDetails(Offer offer, int num) {

        List<OfferDetail> ret = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            ret.add(createOfferDetail(offer));
        }
        return ret;
    }

}
