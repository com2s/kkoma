package com.ssafy.kkoma.api.offer.controller;

import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.offer.entity.OfferDetail;
import com.ssafy.kkoma.domain.point.entity.Point;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.factory.CategoryFactory;
import com.ssafy.kkoma.factory.MemberFactory;
import com.ssafy.kkoma.factory.ProductFactory;
import com.ssafy.kkoma.global.util.CustomSpringBootTest;
import com.ssafy.kkoma.global.util.RequestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@CustomSpringBootTest
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