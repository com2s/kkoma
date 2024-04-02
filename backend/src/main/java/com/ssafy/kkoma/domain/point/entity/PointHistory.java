package com.ssafy.kkoma.domain.point.entity;

import com.ssafy.kkoma.domain.common.entity.BaseTimeEntity;
import com.ssafy.kkoma.domain.point.constant.PointChangeType;
import com.ssafy.kkoma.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "point_id")
	private Point point;

	private int amount;

	private int balanceAfterChange;

	@Enumerated(EnumType.STRING)
	private PointChangeType pointChangeType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
}
