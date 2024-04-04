package com.ssafy.kkoma.domain.kid.entity;

import com.ssafy.kkoma.api.kid.dto.request.UpdateKidRequest;
import com.ssafy.kkoma.domain.common.entity.BaseTimeEntity;
import com.ssafy.kkoma.domain.kid.constant.GenderType;
import com.ssafy.kkoma.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@Builder
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

	public Kid() { }

	public void setMember(Member member) {
		this.member = member;
		member.getKids().add(this);
	}

	public void updateKidInfo(UpdateKidRequest updateKidRequest) {
		this.name = updateKidRequest.getName();
		this.birthDate = updateKidRequest.getBirthDate();
		this.gender = updateKidRequest.getGender();
	}
}
