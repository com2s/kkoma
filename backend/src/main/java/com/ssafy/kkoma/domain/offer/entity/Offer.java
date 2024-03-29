package com.ssafy.kkoma.domain.offer.entity;

import com.ssafy.kkoma.domain.common.entity.BaseTimeEntity;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.offer.constant.OfferType;
import com.ssafy.kkoma.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Offer extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToMany(mappedBy = "offer")
	private List<OfferDetail> offerDetails;

	@Enumerated(EnumType.STRING)
	private OfferType status;

	private LocalDateTime repliedAt;

	private LocalDateTime cancelledAt;

	@Builder
	public Offer(Member member, Product product) {
		this.member = member;
		this.product = product;
		this.offerDetails = new ArrayList<>();
		this.status = OfferType.SENT;
		this.repliedAt = null;
		this.cancelledAt = null;
	}

	public void setMember(Member member) {
		this.member = member;
		member.getOffers().add(this);
	}

	public void updateStatus(OfferType status) {
		this.status = status;
	}

}
