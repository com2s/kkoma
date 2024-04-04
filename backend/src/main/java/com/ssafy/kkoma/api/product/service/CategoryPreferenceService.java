package com.ssafy.kkoma.api.product.service;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.recommendation.CategoryPreference;
import com.ssafy.kkoma.domain.product.constant.CategoryPreferenceType;
import com.ssafy.kkoma.domain.product.repository.CategoryPreferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryPreferenceService {

    private final CategoryPreferenceRepository categoryPreferenceRepository;
    private final MemberService memberService;
    private final CategoryService categoryService;

    // todo-siyoon 고도화
    public void renewCategoryPreference(Long memberId, Integer categoryId, CategoryPreferenceType type) {

        CategoryPreference categoryPreference = categoryPreferenceRepository.findByMemberIdAndCategoryId(memberId, categoryId);

        if (categoryPreference == null) {
            categoryPreference = CategoryPreference.builder()
                    .member(memberService.findMemberByMemberId(memberId))
                    .category(categoryService.findCategoryById(categoryId))
                    .preference(0)
                    .build();
        }

        if (type == CategoryPreferenceType.BUY) {
            categoryPreference.addPreference(0.1F);
        } else if (type == CategoryPreferenceType.SELL) {
            categoryPreference.addPreference(0.1F);
        } else if (type == CategoryPreferenceType.WISH) {
            categoryPreference.addPreference(0.1F);
        } else if (type == CategoryPreferenceType.UNWISH) {
            categoryPreference.subPreference(0.1F);
        } else if (type == CategoryPreferenceType.VIEW) {
            categoryPreference.addPreference(0.1F);
            // todo-siyoon 다른 선호도 감소 (배치 프로세싱으로 성능 개선 가능할 듯)
        }

        categoryPreferenceRepository.save(categoryPreference);
        log.info("renew preference");
    }

}
