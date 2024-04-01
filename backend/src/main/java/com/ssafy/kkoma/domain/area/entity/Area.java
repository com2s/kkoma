package com.ssafy.kkoma.domain.area.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Area {

	@Id
	private Long id;

	@Column(length = 10)
	private String subName1;

	@Column(length = 10)
	private String subName2;

	@Column(length = 10)
	private String subName3;

	@Column(length = 10)
	private String subName4;

}
