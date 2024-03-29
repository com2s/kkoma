package com.ssafy.kkoma.api.recommendation;

import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Category;
import com.ssafy.kkoma.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoryPreference implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        private Long id;

//        @EmbeddedId
//        private CategoryPreferenceId id;

        @ManyToOne//(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
//        @MapsId("memberId")
        private Member member;

        @ManyToOne // (fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
//        @MapsId("categoryId")
        private Category category;

        private float preference;

        public CategoryPreference() {

        }

        @Override
        public String toString() {
                return "CategoryPreference{" +
                        "id=" + id +
                        ", memberId=" + member.getId() +
                        ", preference=" + preference +
                        '}';
        }
}
