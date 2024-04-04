package com.ssafy.kkoma.domain.location.entity;

import com.ssafy.kkoma.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long regionCode;
	private Double x;
	private Double y;

	@Column(length = 50)
	private String placeDetail;

}
