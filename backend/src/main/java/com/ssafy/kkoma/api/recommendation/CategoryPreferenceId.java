package com.ssafy.kkoma.api.recommendation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;

@Embeddable
@Getter
class CategoryPreferenceId implements Serializable {

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "category_id")
    private Integer categoryId;

}