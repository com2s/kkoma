package com.ssafy.kkoma.factory;

import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.repository.CategoryRepository;
import com.ssafy.kkoma.global.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryFactory {

    @Autowired
    CategoryRepository categoryRepository;

    private static String[] categoryNames = {
            "젖병",
            "젖꼭지",
            "유아용컵",
            "분유통",
            "이유식용기",
            "모유보관용기",
            "모유보관팩",
            "유아용수저",
            "유아용젓가락",
            "이유식조리도구",
            "모유보관팩",
            "모유보관용기",
            "유아용물통",
            "유아용스푼",
            "유아용포크",
            "유아용식판",
    };

    public Category createCategory() {
        Category category = Category.builder()
                .name(RandomStringGenerator.randomUUID(10))
                .build();
        return categoryRepository.save(category);
    }

    public Category createCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .build();
        return categoryRepository.save(category);
    }


    public List<Category> createCategories(int n) {
        n = Math.min(n, categoryNames.length);
        n = Math.max(n, 1);

        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Category category = Category.builder()
                    .name(categoryNames[i])
                    .build();
            categories.add(categoryRepository.save(category));
        }
        return categories;
    }

}
