package com.ssafy.kkoma.api.product.controller;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.product.dto.ProductWishResponse;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.WishList;
import com.ssafy.kkoma.factory.MemberFactory;
import com.ssafy.kkoma.factory.ProductFactory;
import com.ssafy.kkoma.factory.WishListFactory;
import com.ssafy.kkoma.global.util.CustomMockMvcSpringBootTest;
import com.ssafy.kkoma.global.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CustomMockMvcSpringBootTest
class WishControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RequestUtil requestUtil;

    @Autowired
    MemberFactory memberFactory;

    @Autowired
    ProductFactory productFactory;

    @Autowired
    ProductService productService;

    @Autowired
    WishListFactory wishListFactory;

    @Test
    @Transactional
    void 찜하기() throws Exception {

        Member member = memberFactory.createMember();
        Product product = productFactory.createProduct(member);
        ProductWishResponse productWishResponse = ProductWishResponse.builder()
                .productId(product.getId())
                .wish(true)
                .build();

        mockMvc.perform(requestUtil.postRequest("/api/products/{productId}/wish", member, product.getId()))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        requestUtil.jsonContent(ProductWishResponse.class, productWishResponse)
                );
    }

    @Test
    @Transactional
    void 찜하기후에_찜취소하기() throws Exception {
        Member member = memberFactory.createMember();
        Product product = productFactory.createProduct(member);

        ProductWishResponse expectedResponse = ProductWishResponse.builder()
                .productId(product.getId())
                .wish(true)
                .build();

        Long productId = product.getId();
        mockMvc.perform(requestUtil.postRequest("/api/products/{productId}/wish", member, productId))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        requestUtil.jsonContent(ProductWishResponse.class, expectedResponse)
                );

        ProductWishResponse expectedResponse2 = ProductWishResponse.builder()
                .productId(product.getId())
                .wish(false)
                .build();

        mockMvc.perform(requestUtil.putRequest("/api/products/{productId}/unwish", member, productId))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        requestUtil.jsonContent(ProductWishResponse.class, expectedResponse2)
                );
    }


    @Test
    @Transactional
    public void 나의_찜_목록_조회하기() throws Exception{
        Member seller = memberFactory.createMember();
        Member buyer = memberFactory.createMember();

        List<ProductSummary> wishProductsList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Product product = productFactory.createProduct(seller);
            WishList wishList = wishListFactory.createWishList(buyer, product);
            wishProductsList.add(ProductSummary.fromEntity(wishList.getProduct()));
        }

        for (int i = 0; i < 5; i++) {
            Product product = productFactory.createProduct(seller);
            wishListFactory.createWishList(buyer, product);
        }

        BasePageResponse<WishList, ProductSummary> myWishProductResponse = new BasePageResponse<>(wishProductsList, 10, 0, 10, 15, 2, true, false, false);

        mockMvc.perform(requestUtil.getRequest("/api/products/wishes", buyer).param("page", "0").param("size", "10"))
            .andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                requestUtil.jsonContent(BasePageResponse.class, myWishProductResponse)
            );
    }

}
