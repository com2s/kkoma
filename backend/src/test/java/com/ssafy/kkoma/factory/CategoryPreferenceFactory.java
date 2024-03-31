package com.ssafy.kkoma.factory;

import com.ssafy.kkoma.api.recommendation.CategoryPreference;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.CategoryPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CategoryPreferenceFactory {

    @Autowired
    CategoryPreferenceRepository categoryPreferenceRepository;

    public CategoryPreference createCategoryPreference(Member member, Category category) {
        CategoryPreference categoryPreference = CategoryPreference.builder()
                .member(member)
                .category(category)
                .preference(getRandomPreference())
                .build();
        return categoryPreferenceRepository.save(categoryPreference);
    }

    public static float getRandomPreference() {
        Random random = new Random();
        return random.nextFloat();
    }
}
