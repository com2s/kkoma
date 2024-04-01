package com.ssafy.kkoma.api.product.controller;

import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.domain.area.repository.AreaRepository;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.repository.ProductRepository;
import com.ssafy.kkoma.factory.MemberFactory;
import com.ssafy.kkoma.factory.ProductFactory;
import com.ssafy.kkoma.global.util.CustomMockMvcSpringBootTest;
import com.ssafy.kkoma.global.util.RequestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@CustomMockMvcSpringBootTest
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RequestUtil requestUtil;

    @Autowired
    private ProductFactory productFactory;

    @Autowired
    private MemberFactory memberFactory;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Test
    @Transactional
    void getProducts() throws Exception {
        List<ProductSummary> productSummaryList = new ArrayList<>();
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            String dealPlace = areaRepository.findAreaById(product.getLocation().getRegionCode()).get().getFullArea();
            ProductSummary productSummary = ProductSummary.fromEntity(product);
            productSummary.setDealPlace(dealPlace);
            productSummaryList.add(productSummary);
        }

        Member member = memberFactory.createMember();
        Product product = productFactory.createProduct(member);

        ProductSummary productSummary = ProductSummary.fromEntity(product);
        productSummary.setDealPlace("서울특별시 종로구 청운동");

        productSummaryList.add(productSummary);

        mockMvc.perform(requestUtil.getRequest("/api/products", member))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        requestUtil.jsonListContent(ProductSummary.class, productSummaryList)
                );
    }

    @Test
    void getProduct() {
    }

    @Test
    void createProduct() {
    }



}
