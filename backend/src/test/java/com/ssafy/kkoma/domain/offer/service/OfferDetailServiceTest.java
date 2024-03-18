package com.ssafy.kkoma.domain.offer.service;

import com.ssafy.kkoma.api.offer.service.OfferDetailService;
import com.ssafy.kkoma.api.offer.dto.OfferTimeRequest;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.offer.repository.OfferDetailRepository;
import com.ssafy.kkoma.domain.offer.repository.OfferRepository;;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OfferDetailServiceTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    OfferDetailRepository offerDetailRepository;

    @Autowired
    OfferDetailService offerDetailService;

    private static final String TITLE = "TITLE";

    @Test
    @Transactional
    public void 거래_시간_등록_성공() throws Exception{
        // given
        Product product = productRepository.save(Product.builder().title(TITLE).build());
        Offer offer = offerRepository.save(Offer.builder().product(product).build());

        List<OfferTimeRequest> offerTimeRequestList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            offerTimeRequestList.add(new OfferTimeRequest(LocalDate.now(), LocalTime.now(), LocalTime.now()));
        }

        // when
        offerDetailService.createOfferDetail(offer.getId(), offerTimeRequestList);

        // then
        assertEquals(2, offerDetailRepository.findAllOfferDetailsByOfferId(offer.getId()).get().size());

    }

}