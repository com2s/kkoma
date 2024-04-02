package com.ssafy.kkoma.api.offer.controller;

import com.ssafy.kkoma.factory.CategoryFactory;
import com.ssafy.kkoma.factory.MemberFactory;
import com.ssafy.kkoma.factory.ProductFactory;
import com.ssafy.kkoma.global.util.CustomMockMvcSpringBootTest;
import com.ssafy.kkoma.global.util.RequestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@CustomMockMvcSpringBootTest
class OfferControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RequestUtil requestUtil;

    @Autowired
    private ProductFactory productFactory;

    @Autowired
    private MemberFactory memberFactory;

    @Autowired
    private CategoryFactory categoryFactory;

    @Test
    void createOffer() {

//        Member seller = memberFactory.createMember();
//        Member buyer = memberFactory.createMember();
//        buyer.addPoint(10000);
//        Category category = categoryFactory.createCategory("유모차");
//        Product product = productFactory.createProduct(seller, category, 20000);

    }

    @Test
    void getOffers() {
    }

    @Test
    void changeOfferStatus() {
    }
}
