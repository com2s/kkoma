package com.ssafy.kkoma.api.recommendation;

import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.recommendation.service.RecommendationService;
import com.ssafy.kkoma.domain.kid.constant.GenderType;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.CategoryPreferenceRepository;
import com.ssafy.kkoma.factory.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Slf4j
@SpringBootTest
class RecommendationServiceTest {

    @Autowired
    MemberFactory memberFactory;

    @Autowired
    CategoryFactory categoryFactory;

    @Autowired
    ProductFactory productFactory;

    @Autowired
    KidFactory kidFactory;

    @Autowired
    CategoryPreferenceFactory categoryPreferenceFactory;

    @Autowired
    RecommendationService recommendationService;

    @Autowired
    CategoryPreferenceRepository categoryPreferenceRepository;

    @Test
    @Transactional
    void recommendCategory() throws SQLException {
//        final int MEMBER_NUMBER = 10;
//        final int CATEGORY_NUMBER = 8;
//        final int PRODUCT_NUMBER = 5;
//        List<Member> members = memberFactory.createMembers(MEMBER_NUMBER);
//        Random random = new Random();
//        for (int i = 0; i < members.size(); i++) {
//            int randomNumber = random.nextInt(2000) + 1;
//            GenderType genderType = randomNumber % 2 == 0 ? GenderType.MALE : GenderType.FEMALE;
//            Kid savedKid = kidFactory.createKid(members.get(i), genderType, LocalDate.now().minusDays(randomNumber));
//        }
//
//        List<Category> categories = categoryFactory.createCategories(CATEGORY_NUMBER);
//        for (int i = 0; i < MEMBER_NUMBER; i++) {
//            int randomMemberIdx = (int) (Math.random() * MEMBER_NUMBER);
//            for (int j = 0; j < PRODUCT_NUMBER; j++) {
//                int randomCategoryIdx = (int) (Math.random() * CATEGORY_NUMBER);
//                productFactory.createProduct(members.get(randomMemberIdx), categories.get(randomCategoryIdx), 10000, ProductType.SALE);
//            }
//        }
//        log.info("members size={}", members.size());
//        log.info("categories size={}", categories.size());
//
//        for (Member member : members) {
//            for (Category category : categories) {
//                categoryPreferenceFactory.createCategoryPreference(member, category);
//            }
//        }
//
//        List<CategoryPreference> categoryPreferences = categoryPreferenceRepository.findAll();
//        log.info("categoryPreferenceSize={}", categoryPreferences.size());
//
//        for (CategoryPreference categoryPreference : categoryPreferences) {
//            log.info("categoryPreference={}", categoryPreference);
//        }

        List<ProductSummary> productSummaries = recommendationService.recommendProduct(1L);

        log.info(productSummaries.toString());
    }

    @Test
    void recommendProduct() {
        // todo
    }

}