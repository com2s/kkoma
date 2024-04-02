package com.ssafy.kkoma.domain.product.entity;

import com.ssafy.kkoma.api.recommendation.CategoryPreference;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.factory.CategoryFactory;
import com.ssafy.kkoma.factory.CategoryPreferenceFactory;
import com.ssafy.kkoma.factory.MemberFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class CategoryPreferenceRepositoryTest {

    @Autowired
    CategoryPreferenceRepository categoryPreferenceRepository;

    @Autowired
    MemberFactory memberFactory;

    @Autowired
    CategoryFactory categoryFactory;

    @Autowired
    CategoryPreferenceFactory categoryPreferenceFactory;

    @Test
    @Transactional
    public void 카테고리_선호도_조회하기() {
        Member member = memberFactory.createMember();
        Category category = categoryFactory.createCategory();
        CategoryPreference categoryPreference = categoryPreferenceFactory.createCategoryPreference(member, category);
        log.info(categoryPreference.toString());

        CategoryPreference savedCategoryPreference = categoryPreferenceRepository.save(categoryPreference);
        log.info(savedCategoryPreference.toString());
        CategoryPreference foundCategoryPreference = categoryPreferenceRepository.findByMemberIdAndCategoryId(member.getId(), category.getId());
        log.info(foundCategoryPreference.toString());

        Assertions.assertEquals(savedCategoryPreference, foundCategoryPreference);
    }

}
