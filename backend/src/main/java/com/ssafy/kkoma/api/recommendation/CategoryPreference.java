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
@Table(
        name = "category_preference",
        indexes = {
                @Index(name = "category_preference_member_id_idx", columnList = "member_id"),
                @Index(name = "category_preference_category_id_idx", columnList = "category_id"),
                @Index(name = "category_preference_preference_idx", columnList = "preference")
        }
)
public class CategoryPreference implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        private Member member;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        private Category category;

        private float preference;

        public void addPreference(float value) {
                if (this.preference + value > 1) {
                        preference = 1;
                } else {
                        this.preference += value;
                }
        }

        public void subPreference(float value) {
                if (this.preference < value) {
                        preference = 0;
                } else {
                        this.preference -= value;
                }
        }

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
