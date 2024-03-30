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

        @ManyToOne // (fetch = FetchType.LAZY) todo-siyoon check
        @JoinColumn(name = "member_id")
        private Member member;

        @ManyToOne // (fetch = FetchType.LAZY) todo-siyoon check
        @JoinColumn(name = "category_id")
        private Category category;

        private float preference;

        public void addPreference(float value) {
                if (this.preference + value > 1) {
                        preference = 1;
                }
                this.preference += value;
        }

        public void subPreference(float value) {
                if (this.preference < value) {
                        preference = 0;
                }
                this.preference += value;
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
