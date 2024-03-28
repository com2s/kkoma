package com.ssafy.kkoma.factory;

import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.offer.repository.OfferRepository;
import com.ssafy.kkoma.domain.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferFactory {

    @Autowired
    OfferRepository offerRepository;

    public OfferFactory(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Offer createOffer(Product product, Member buyer) {
        Offer offer = Offer.builder().product(product).member(buyer).build();
        return offerRepository.save(offer);
    }

}
