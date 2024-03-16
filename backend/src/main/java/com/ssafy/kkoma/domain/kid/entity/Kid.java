package com.ssafy.kkoma.domain.kid.entity;

import java.time.LocalDate;

import com.ssafy.kkoma.api.kid.dto.UpdateKidRequestDto;
import com.ssafy.kkoma.domain.common.entity.BaseTimeEntity;
import com.ssafy.kkoma.domain.kid.constant.GenderType;
import com.ssafy.kkoma.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Kid extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(length = 50)
	private String name;

	private LocalDate birthDate;

	@Enumerated(EnumType.STRING)
	private GenderType gender;

	private Integer clothSize;

	private Integer shoeSize;

	public void setMember(Member member) {
		this.member = member;
		member.getKids().add(this);
	}

	public void updateKidInfo(UpdateKidRequestDto updateKidRequestDto) {
		this.name = updateKidRequestDto.getName();
		this.birthDate = updateKidRequestDto.getBirthDate();
		this.gender = updateKidRequestDto.getGender();
	}
}
