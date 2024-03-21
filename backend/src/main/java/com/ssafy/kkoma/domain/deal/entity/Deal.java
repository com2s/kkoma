package com.ssafy.kkoma.domain.deal.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ssafy.kkoma.api.kid.dto.request.UpdateKidRequest;
import com.ssafy.kkoma.domain.common.entity.BaseTimeEntity;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Deal extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	private LocalDateTime selectedTime;

	@Builder.Default
	private Boolean isCompleted = Boolean.FALSE;

	@Builder.Default
	private String code = String.valueOf(UUID.randomUUID());

	private LocalDateTime cancelledAt;

	public void updateIsCompleted(Boolean status) {
		this.isCompleted = status;
	}
}
