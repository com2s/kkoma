package com.ssafy.kkoma.domain.product.entity;

import com.ssafy.kkoma.domain.common.entity.BaseTimeEntity;
import com.ssafy.kkoma.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishList extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column
	@Builder.Default
	private Boolean isValid = true;

	public void setMemberAndProduct(Member member, Product product) {
		this.member = member;
		this.product = product;
		member.getWishes().add(this);
		product.getWishes().add(this);
	}

	public void setValid(boolean b) {
		this.isValid = b;
	}
}
