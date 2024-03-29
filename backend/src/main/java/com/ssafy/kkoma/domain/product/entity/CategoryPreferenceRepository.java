package com.ssafy.kkoma.domain.product.entity;

import com.ssafy.kkoma.api.recommendation.CategoryPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryPreferenceRepository extends JpaRepository<CategoryPreference, Long> {

    CategoryPreference findByMemberIdAndCategoryId(Long memberId, Integer CategoryId);
}
