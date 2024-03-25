package com.ssafy.kkoma.factory;

import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.repository.CategoryRepository;
import com.ssafy.kkoma.global.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory {

    @Autowired
    CategoryRepository categoryRepository;

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

}
